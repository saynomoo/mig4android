package com.saynomoo.mig4android;

public class Growing extends MigActivity{
    @Override
    public MigLayout createLayout() {
        return new GrowingView();
    }

    class GrowingView extends MigLayout {
        public GrowingView() {
            super(Growing.this);
            setColumnConstraints("[pref!][grow,fill]");
            setRowConstraints("[]15[]");
            addLabel(this, "Fixed", "");
            addLabel(this, "Gets all extra space", "wrap");
            addTextField(this, "     ", "");
            addTextField(this, "     ", "");
        }
    }

}
