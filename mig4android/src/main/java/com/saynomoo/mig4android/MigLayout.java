package com.saynomoo.mig4android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import net.miginfocom.layout.*;

import java.lang.reflect.Field;
import java.util.*;

/*
 * License (BSD):
 * ==============
 *
 * Copyright (c) 2004, Mikael Grev, MiG InfoCom AB. (miglayout (at) miginfocom (dot) com)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * Neither the name of the MiG InfoCom AB nor the names of its contributors may be
 * used to endorse or promote products derived from this software without specific
 * prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 *
 */

/**
Originally based on net.miginfocom.swl.MigLayout
 */
public class MigLayout extends ViewGroup implements WrapperFactory {
    private transient ViewGroupWrapper parentWrapper = viewGroupWrapper(this);
    private transient Map<ComponentWrapper, CC> ccMap = new HashMap<ComponentWrapper, CC>(0);

    private LC lc = new LC();
    private AC colSpecs = new AC(), rowSpecs = new AC();

    private transient Grid grid = null;

    private transient int lastModCount = PlatformDefaults.getModCount();
    private transient int lastHash = -1;
    private transient Paint outlinePaint = createOutlinePaint();
    private transient Paint cellPaint = createCellPaint();

    private transient ArrayList<LayoutCallback> callbackList = null;

    public MigLayout(Context context) {
        super(context);
    }
    public MigLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        String columnConstraints = param(attrs, "column_constraints", null);
        if(columnConstraints!=null) setColumnConstraints(columnConstraints);
        String rowConstraints = param(attrs, "row_constraints", null);
        if(rowConstraints!=null) setRowConstraints(rowConstraints);
        String layoutConstraints = param(attrs, "layout_constraints", null);
        if(layoutConstraints!=null) setLayoutConstraints(layoutConstraints);
    }
    public void addView(View view, String constraints){
        addView(view, new LayoutParams(constraints));
    }
    public void addView(View view, CC constraints){
        addView(view, new LayoutParams(constraints));
    }
    public ViewGroupWrapper getParentWrapper() {
        return parentWrapper;
    }
    public LC getLayoutConstraints(){
        return lc;
    }
    public void setLayoutConstraints(String constraints) {
        setLayoutConstraints(ConstraintParser.parseLayoutConstraint(ConstraintParser.prepare(constraints)));
    }
    public void setLayoutConstraints(LC constraints) {
        lc = constraints;
        grid = null;
    }
    public AC getColumnConstraints() {
        return colSpecs;
    }
    public void setColumnConstraints(String constraints) {
        setColumnConstraints(ConstraintParser.parseColumnConstraints(ConstraintParser.prepare(constraints)));
    }
    public void setColumnConstraints(AC constraints) {
        colSpecs = constraints;
        grid = null;
    }
    public Object getRowConstraints() {
        return rowSpecs;
    }
    public void setRowConstraints(String constraints) {
        setRowConstraints(ConstraintParser.parseRowConstraints(ConstraintParser.prepare(constraints)));
    }
    public void setRowConstraints(AC constraints) {
        rowSpecs = constraints;
        grid = null;
    }

    public void addLayoutCallback(LayoutCallback callback) {
        if (callback == null)
            throw new NullPointerException();

        if (callbackList == null)
            callbackList = new ArrayList<LayoutCallback>(1);

        callbackList.add(callback);
    }

    public void removeLayoutCallback(LayoutCallback callback) {
        if (callbackList != null)
            callbackList.remove(callback);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(grid!=null && isDebug()){
            try {
                final Field field = Grid.class.getDeclaredField("debugRects");
                field.setAccessible(true);
                ArrayList<int[]> debugRects = (ArrayList<int[]>)field.get(grid);
                if(debugRects!=null){
                    drawLayoutCell(canvas);
                    drawLayoutOutline(canvas);
                    for (int[] rect : debugRects) {
                        drawChildCell(canvas, rect);
                    }
                    for (ViewWrapper wrapper : parentWrapper.getComponents()) {
                        drawComponentOutline(canvas, wrapper.getComponent());
                    }
                }
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
            }
        }
    }

    private void drawLayoutCell(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth()-1, getHeight()-1, cellPaint);
    }

    private void drawLayoutOutline(Canvas canvas) {
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), getWidth()-getPaddingRight()-1, getHeight()-getPaddingBottom()-1, outlinePaint);
    }

    private void drawChildCell(Canvas canvas, int[] rect) {
        canvas.drawRect(rect[0], rect[1], rect[2]+rect[0]-1, rect[3]+rect[1]-1, cellPaint);
    }

    private void drawComponentOutline(Canvas canvas, View view) {
        canvas.drawRect(view.getLeft(), view.getTop(), view.getRight()-1, view.getBottom()-1, outlinePaint);
    }

    private boolean isDebug() {
        return getDebugMillis() > 0;
    }

    private int getDebugMillis() {
        int globalDebugMillis = LayoutUtil.getGlobalDebugMillis();
        return globalDebugMillis > 0 ? globalDebugMillis : lc.getDebugMillis();
    }

    private void checkCache() {
        Map<ComponentWrapper, CC> newConstraints = parentWrapper.constraintMap();
        if(!ccMap.equals(newConstraints)){
            ccMap = newConstraints;
            grid = null;
        }
        int mc = PlatformDefaults.getModCount();
        if (lastModCount != mc) {
            lastModCount = mc;
            grid = null;
        }
        int hash = parentWrapper.getLayoutHashCode();
        for (ComponentWrapper componentWrapper : ccMap.keySet()){
            hash += componentWrapper.getLayoutHashCode();
        }
        if(isDebug()){
            hash += 777129;
        }
        if (hash != lastHash) {
            lastHash = hash;
            grid = null;
        }
        if (grid == null){
            grid = new Grid(parentWrapper, lc, rowSpecs, colSpecs, ccMap, callbackList);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        checkCache();
        int[] box = contentCoords();

        final boolean layoutAgain = grid.layout(box, lc.getAlignX(), lc.getAlignY(), isDebug(), true);

        if (layoutAgain) {
            grid = null;
            checkCache();
            grid.layout(box, lc.getAlignX(), lc.getAlignY(), isDebug(), false);
        }
    }

    private int[] contentCoords() {
        final int contentLeft = getPaddingLeft();
        final int contentTop = getPaddingTop();
        final int contentWidth = getWidth() - contentLeft - getPaddingRight();
        final int contentHeight = getHeight() - contentTop - getPaddingBottom();
        return new int[]{contentLeft, contentTop, contentWidth, contentHeight};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        onLayout(false, 0, 0, 0, 0);
        final int width = dimensionBySpec(widthMeasureSpec, grid != null ? grid.getWidth() : null);
        final int height = dimensionBySpec(heightMeasureSpec, grid != null ? grid.getHeight() : null);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        final ViewGroup.LayoutParams lp = child.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT; //TODO Is there more accurate approximation from CC (maxw / pos?)
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
    }

    private int dimensionBySpec(int measureSpec, int[] migSizes) {
        final int mode = MeasureSpec.getMode(measureSpec);
        if(mode==MeasureSpec.AT_MOST || mode==MeasureSpec.UNSPECIFIED){
            return LayoutUtil.getSizeSafe(migSizes, LayoutUtil.PREF);
        }else{
            return MeasureSpec.getSize(measureSpec);
        }
    }

    private Paint createOutlinePaint() {
        Paint paint = new Paint();
        paint.setDither(true);
        paint.setARGB(255, 0, 0, 200);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setPathEffect(new DashPathEffect(new float[]{2, 4}, 0));
        paint.setStrokeWidth(1);
        return paint;
    }
    private Paint createCellPaint() {
        Paint paint = createOutlinePaint();
        paint.setARGB(255, 255, 0, 0);
        paint.setPathEffect(new DashPathEffect(new float[]{2, 3}, 0));
        return paint;
    }

    private static String param(AttributeSet attrs, String name, String defaultValue) {
        String xmlParams = attrs.getAttributeValue(null, name);
        return xmlParams != null ? xmlParams : defaultValue;
    }

    public ViewWrapper viewWrapper(View view) {
        return new ViewWrapper(view, this);
    }

    public ViewGroupWrapper viewGroupWrapper(ViewGroup group) {
        return new ViewGroupWrapper(group, this);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        private CC constraints;
        private String constraintString;

        public LayoutParams(CC cc) {
            super(WRAP_CONTENT, WRAP_CONTENT);
            this.constraints = cc;
        }

        public LayoutParams(String constraints) {
            this(ConstraintParser.parseComponentConstraint(constraints));
            this.constraintString = constraints;
        }

        public CC getConstraints() {
            return constraints;
        }

        public String getConstraintString() {
            return constraintString;
        }
    }
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        String params = param(attrs, "component_constraints", "");
        return new LayoutParams(params);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams("");
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams("");
    }

    static {
        try {
            PlatformDefaults.setDefaultDPI(160);
        } catch (NoSuchMethodError e) {
            //For some reason IDEA designer throws this
        }
    }
}
