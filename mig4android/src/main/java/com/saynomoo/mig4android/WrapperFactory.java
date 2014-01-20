package com.saynomoo.mig4android;

import android.view.View;
import android.view.ViewGroup;

public interface WrapperFactory {
    public ViewWrapper viewWrapper(View view);
    public ViewGroupWrapper viewGroupWrapper(ViewGroup group);
}
