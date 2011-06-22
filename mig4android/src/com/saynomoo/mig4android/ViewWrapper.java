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

import android.view.View;
import android.view.ViewGroup;
import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.ContainerWrapper;

import java.lang.reflect.Method;

public class ViewWrapper implements ComponentWrapper {

    private final View c;

    public ViewWrapper(View c) {
        this.c = c;
    }

    public final int getBaseline(int width, int height) {
        return c.getBaseline();
    }

    public final View getComponent() {
        return c;
    }

    public final float getPixelUnitFactor(boolean isHor) {
        return 1f; //TODO
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
        return c.getLeft(); //TODO?
    }

    public final int getScreenLocationY() {
        return c.getTop(); //TODO?
    }

    public final int getMinimumHeight(int sz) {
        if (GET_SUGGESTED_MINIMUM_HEIGHT != null) {
            try {
                return (Integer) GET_SUGGESTED_MINIMUM_HEIGHT.invoke(c);
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public final int getMinimumWidth(int sz) {
        if (GET_SUGGESTED_MINIMUM_WIDTH != null) {
            try {
                return (Integer) GET_SUGGESTED_MINIMUM_WIDTH.invoke(c);
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public final int getPreferredHeight(int sz) {
        return c.getMeasuredHeight();
    }

    public final int getPreferredWidth(int sz) {
        return c.getMeasuredWidth();
    }

    public final int getMaximumHeight(int sz) {
        return Short.MAX_VALUE;
    }

    public final int getMaximumWidth(int sz) {
        return Short.MAX_VALUE;
    }

    public final ContainerWrapper getParent() {
        return new ViewGroupWrapper((ViewGroup) c.getParent());
    }

    public int getHorizontalScreenDPI() {
        return (int) c.getResources().getDisplayMetrics().xdpi;
    }

    public int getVerticalScreenDPI() {
        return (int) c.getResources().getDisplayMetrics().ydpi;
    }

    public final int getScreenWidth() {
        return c.getResources().getDisplayMetrics().widthPixels;
    }

    public final int getScreenHeight() {
        return c.getResources().getDisplayMetrics().heightPixels;
    }

    public final boolean hasBaseline() {
        return c.getBaseline()!=-1;
    }

    public final String getLinkId() {
        return String.valueOf(c.getId());
    }

    public final void setBounds(int x, int y, int width, int height) {
        int xNoPad = x-c.getPaddingLeft();
        int yNoPad = y-c.getPaddingTop();
        c.layout(xNoPad, yNoPad, xNoPad + width, yNoPad + height);
    }

    public boolean isVisible() {
        return c.getVisibility() != View.GONE;
    }

    public final int[] getVisualPadding() {
        return new int[]{c.getPaddingTop(), c.getPaddingLeft(), c.getPaddingBottom(), c.getPaddingRight()};
    }

    public int getLayoutHashCode() {
        int h = getWidth() + (getHeight() << 5);
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

    private static Method GET_SUGGESTED_MINIMUM_HEIGHT = null;
    private static Method GET_SUGGESTED_MINIMUM_WIDTH = null;

    static {
        try {
            GET_SUGGESTED_MINIMUM_HEIGHT = View.class.getDeclaredMethod("getSuggestedMinimumHeight");
            GET_SUGGESTED_MINIMUM_WIDTH = View.class.getDeclaredMethod("getSuggestedMinimumWidth");
        } catch (Exception e) {
        }
    }
}
