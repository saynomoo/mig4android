package com.saynomoo.mig4android.test;

import android.widget.TextView;
import com.saynomoo.mig4android.MigActivity;
import com.saynomoo.mig4android.MigLayout;

public class WidthDeterminedFromSibling extends MigActivity {
    @Override
    public MigLayout createLayout() {
        return new WidthDeterminedFromSiblingView();
    }
    class WidthDeterminedFromSiblingView extends MigLayout {
        public WidthDeterminedFromSiblingView() {
            super(WidthDeterminedFromSibling.this);
            final TextView textView = addTextField(this, "", "grow, wrap");
            final TextView label = addLabel(this, "This component determines the width\nType text to the TextEdit", "wrap");
        }
    }
}
