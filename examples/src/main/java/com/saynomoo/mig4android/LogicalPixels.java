package com.saynomoo.mig4android;

public class LogicalPixels extends MigActivity{
    @Override
    public MigLayout createLayout() {
        return new LogicalPixelsView();
    }
    class LogicalPixelsView extends MigLayout {
        public LogicalPixelsView() {
            super(LogicalPixels.this);
            addConstraintLabel(this, "height 100px");
            addConstraintLabel(this, "height 100");
        }
    }
}
