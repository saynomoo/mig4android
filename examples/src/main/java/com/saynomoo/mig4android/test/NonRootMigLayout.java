package com.saynomoo.mig4android.test;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.saynomoo.mig4android.MigActivity;
import com.saynomoo.mig4android.MigLayout;

public class NonRootMigLayout extends MigActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LinearLayout(this){{
            setOrientation(VERTICAL);
            addView(new EditText(getContext()));
            addView(createLayout());
        }});
    }

    @Override
    public MigLayout createLayout() {
        return new MigLayout(this){{
            addView(new TextView(getContext()){{
                setText("Layout is done relative to MigLayout");
            }});
        }};
    }
}
