package com.saynomoo.mig4android.test;

import com.saynomoo.mig4android.MigActivity;
import com.saynomoo.mig4android.MigLayout;

public class AbsolutePosition extends MigActivity {
    @Override
    public MigLayout createLayout() {
        return new AbsolutePositionView();
    }

    class AbsolutePositionView extends MigLayout {
        public AbsolutePositionView() {
            super(AbsolutePosition.this);
            addLabel(this, "Gap above text: container.ypos", "pos 0 container.ypos");
        }
    }

}
