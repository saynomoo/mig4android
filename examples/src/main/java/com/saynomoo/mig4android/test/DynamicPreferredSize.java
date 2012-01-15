package com.saynomoo.mig4android.test;

import android.widget.TextView;
import com.saynomoo.mig4android.MigActivity;
import com.saynomoo.mig4android.MigLayout;

public class DynamicPreferredSize extends MigActivity {
    @Override
    public MigLayout createLayout() {
        return new DynamicPreferredSizeView();
    }
    class DynamicPreferredSizeView extends MigLayout {
        public DynamicPreferredSizeView() {
            super(DynamicPreferredSize.this);
            final TextView textView = addLabel(this, "Initial text", "");

            postDelayed(new Runnable() {
                public void run() {
                    textView.setText("Wider text added after initial layout");
                    requestLayout();
                }
            }, 1);
        }
    }
}
