package com.saynomoo.mig4android;

import android.os.Bundle;
import android.view.ViewGroup;

public class BasicSizes extends MigActivity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(new BasicSizesView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
    }

    class BasicSizesView extends MigLayout {
        public BasicSizesView() {
            super(BasicSizes.this);
            setColumnConstraints("[]15[75px]25[min]25[]");
            setRowConstraints("[]15");
            addLabel(this, "75px", "skip");
            addLabel(this, "Min", "");
            addLabel(this, "Pref", "wrap");

            addLabel(this, "new TextField(15)", "");
            addTextField(this, "");
            addTextField(this, "");
            addTextField(this, "");
        }
    }

}
