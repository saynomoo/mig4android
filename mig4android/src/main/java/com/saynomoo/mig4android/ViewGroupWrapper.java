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
import net.miginfocom.layout.CC;
import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.ContainerWrapper;

import java.util.HashMap;
import java.util.Map;

public final class ViewGroupWrapper extends ViewWrapper implements ContainerWrapper {
    public ViewGroupWrapper(ViewGroup c, WrapperFactory factory) {
        super(c, factory);
    }

    public ViewGroup viewGroup() {
        return (ViewGroup) super.getComponent();
    }

    public ViewWrapper[] getComponents() {
        ViewGroup viewGroup = viewGroup();
        int size = viewGroup.getChildCount();
        ViewWrapper[] cws = new ViewWrapper[size];
        for (int i = 0; i < size; i++)
            cws[i] = factory.viewWrapper(viewGroup.getChildAt(i));
        return cws;
    }
    public Map<ComponentWrapper, CC> constraintMap(){
        ViewGroup viewGroup = viewGroup();
        int size = viewGroup.getChildCount();
        Map<ComponentWrapper, CC> constraints = new HashMap<ComponentWrapper, CC>((int)(1.5*size));
        for (int i = 0; i < size; i++) {
            View child = viewGroup.getChildAt(i);
            constraints.put(factory.viewWrapper(child), ((MigLayout.LayoutParams)child.getLayoutParams()).getConstraints());
        }
        return constraints;
    }

    public int getComponentCount() {
        return viewGroup().getChildCount();
    }

    public Object getLayout() {
        return getComponent();
    }

    public final boolean isLeftToRight() {
        return true;
    }

    public final void paintDebugCell(int x, int y, int width, int height) {}

    public int getComponetType(boolean disregardScrollPane) {
        return TYPE_CONTAINER;
    }
}
