package com.saynomoo.mig4android.test;

import com.saynomoo.mig4android.MigActivity;
import com.saynomoo.mig4android.MigLayout;

public class LogicalPixels extends MigActivity {
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
