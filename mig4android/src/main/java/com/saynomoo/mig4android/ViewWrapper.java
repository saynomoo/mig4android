package com.saynomoo.mig4android;
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

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.ContainerWrapper;
import net.miginfocom.layout.PlatformDefaults;

public class ViewWrapper implements ComponentWrapper {

    private final View c;
    protected WrapperFactory factory;

    public ViewWrapper(View c, WrapperFactory factory) {
        this.c = c;
        this.factory = factory;
    }

    public final int getBaseline(int width, int height) {
        return c.getBaseline();
    }

    public final View getComponent() {
        return c;
    }

    public final float getPixelUnitFactor(boolean isHor) {
        switch (PlatformDefaults.getLogicalPixelBase()) {
            case PlatformDefaults.BASE_SCALE_FACTOR:
                Float s = isHor ? PlatformDefaults.getHorizontalScaleFactor() : PlatformDefaults.getVerticalScaleFactor();
                if (s != null)
                    return s;
                return (isHor ? getHorizontalScreenDPI() : getVerticalScreenDPI()) / (float) PlatformDefaults.getDefaultDPI();
            case PlatformDefaults.BASE_FONT_SIZE:
                //TODO
            default:
                return 1f;
        }
    }

    public final int getX() {
        return c.getLeft();
    }

    public final int getY() {
        return c.getTop();
    }

    public final int getWidth() {
        return c.getWidth();
    }

    public final int getHeight() {
        return c.getHeight();
    }

    public final int getScreenLocationX() {
        return getScreenLocation()[0];
    }

    public final int getScreenLocationY() {
        return getScreenLocation()[1];
    }

    public int[] getScreenLocation(){
        int[] loc = new int[2];
        c.getLocationOnScreen(loc);
        return loc;
    }

    private void measureWidth(int sz) {
        int maxHeight = sz ==-1 ? Short.MAX_VALUE : sz;
        c.measure(View.MeasureSpec.makeMeasureSpec(Short.MAX_VALUE, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST));
    }

    private void measureHeight(int sz) {
        int maxWidth = (sz!=-1 && sz < c.getMeasuredWidth()) ? sz : c.getMeasuredWidth();
        c.measure(View.MeasureSpec.makeMeasureSpec(maxWidth, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(Short.MAX_VALUE, View.MeasureSpec.AT_MOST));
    }

    public final int getMinimumHeight(int sz) {
        return c.getMeasuredHeight();
    }
    public final int getMinimumWidth(int sz) {
        return c.getMeasuredWidth();
    }

    public final int getPreferredHeight(int sz) {
        measureHeight(sz);
        return c.getMeasuredHeight();
    }

    public final int getPreferredWidth(int sz) {
        measureWidth(sz);
        return c.getMeasuredWidth();
    }

    public final int getMaximumHeight(int sz) {
        return Short.MAX_VALUE;
    }

    public final int getMaximumWidth(int sz) {
        return Short.MAX_VALUE;
    }

    public final ContainerWrapper getParent() {
        return ViewGroup.class.isInstance(c.getParent()) ? factory.viewGroupWrapper((ViewGroup) c.getParent()) : null;
    }

    public int getHorizontalScreenDPI() {
        return (int) getDisplayMetrics().xdpi;
    }

    public int getVerticalScreenDPI() {
        return (int) getDisplayMetrics().ydpi;
    }

    public DisplayMetrics getDisplayMetrics() {
        return c.getResources().getDisplayMetrics();
    }

    public final int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    public final int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    public final boolean hasBaseline() {
        return c.getBaseline()!=-1;
    }

    public final String getLinkId() {
        return String.valueOf(c.getId());
    }

    public final void setBounds(int x, int y, int width, int height) {
        c.getLayoutParams().width = width;
        c.getLayoutParams().height = height;
        measureAgainIfNeedsResize(width, height);
        c.layout(x, y, x + width, y + height);
    }

    private void measureAgainIfNeedsResize(int width, int height) {
        if(c.getMeasuredWidth()!=width || c.getMeasuredHeight()!=height){
            c.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        }
    }

    public boolean isVisible() {
        return c.getVisibility() != View.GONE;
    }

    public final int[] getVisualPadding() {
        return null;
    }

    public int getLayoutHashCode() {
        int h = getWidth() + (getHeight() << 5) + (c.getMeasuredWidth() << 7) + (c.getMeasuredHeight() << 9);
        if (isVisible()) {
            h += 1324511;
        }
        String id = getLinkId();
        if (id != null)
            h += id.hashCode();
        return h;
    }

    public void paintDebugOutline() {}

    public int getComponetType(boolean disregardScrollPane) {
        return 0;
    }

    public final int hashCode() {
        return c.hashCode();
    }

    public final boolean equals(Object o) {
        if (o == null || o instanceof ComponentWrapper == false)
            return false;

        return getComponent().equals(((ComponentWrapper) o).getComponent());
    }

}
