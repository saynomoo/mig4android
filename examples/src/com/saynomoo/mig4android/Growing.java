package com.saynomoo.mig4android;

import android.os.Bundle;
import android.view.ViewGroup;

public class Growing extends MigActivity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(new GrowingView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
    }

    class GrowingView extends MigLayout {
        public GrowingView() {
            super(Growing.this);
            setColumnConstraints("[pref!][grow,fill]");
            setRowConstraints("[]15[]");
            addLabel(this, "Fixed", "");
            addLabel(this, "Gets all extra space", "wrap");
            addTextField(this, "");
            addTextField(this, "");
        }
    }

}
