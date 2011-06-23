package com.saynomoo.mig4android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public abstract class MigActivity extends Activity {

    protected MigLayout migLayout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        migLayout = createLayout();
        setContentView(migLayout);
    }

    public abstract MigLayout createLayout();

    void addSeparator(MigLayout migLayout, String label) {
        TextView textView = addLabel(migLayout, label, "gapbottom 1, span, split 2, aligny center");
        textView.setTextColor(getResources().getColor(R.color.label));
        View separatorLine = new View(this);
        separatorLine.setMinimumHeight(1);
        separatorLine.setBackgroundColor(getResources().getColor(R.color.separator));
        migLayout.addView(separatorLine, "gapleft rel, growx");
    }

    void addTextField(MigLayout migLayout, String layoutConstraints) {
        EditText editText = new EditText(this);
        migLayout.addView(editText, layoutConstraints);
    }

    TextView addLabel(MigLayout migLayout, final String text, String layoutConstraints) {
        TextView textView = new TextView(this);
        textView.setText(text);
        migLayout.addView(textView, layoutConstraints);
        return textView;
    }
}
