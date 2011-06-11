package com.saynomoo.mig4android;

import android.app.Activity;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MigExamples extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new MigLayout(this) {{
            setLayoutConstraints("wrap");
            setColumnConstraints("[right][fill,sizegroup]unrel[right][fill,sizegroup]");
            setRowConstraints("");
            addSeparator(this, "General");
            addLabel(this, "Company", "gap indent");
            addTextField(this, "span,growx");
            addLabel(this, "Contact", "gap indent");
            addTextField(this, "span,growx");

            addSeparator(this, "Propeller");
            addLabel(this, "PTI/kW", "gap indent");
            addTextField(this, "wmin 130");
            addLabel(this, "Power/kW", "gap indent");
            addTextField(this, "wmin 130");
            addLabel(this, "R/mm", "gap indent");
            addTextField(this, "wmin 130");
            addLabel(this, "D/mm", "gap indent");
            addTextField(this, "wmin 130");
        }});
    }

    private void addSeparator(MigLayout migLayout, String label) {
        TextView textView = addLabel(migLayout, label, "gapbottom 1, span, split 2, aligny center");
        textView.setTextColor(getResources().getColor(R.color.label));
        View separatorLine = new View(this);
        separatorLine.setMinimumHeight(1);
        separatorLine.setBackgroundColor(getResources().getColor(R.color.separator));
        migLayout.addView(separatorLine, "gapleft rel, growx");
    }

    private void addTextField(MigLayout migLayout, String layoutConstraints) {
        EditText editText = new EditText(this);
        migLayout.addView(editText, layoutConstraints);
    }

    private TextView addLabel(MigLayout migLayout, final String text, String layoutConstraints) {
        TextView textView = new TextView(this);
        textView.setText(text);
        migLayout.addView(textView, layoutConstraints);
        return textView;
    }
}
