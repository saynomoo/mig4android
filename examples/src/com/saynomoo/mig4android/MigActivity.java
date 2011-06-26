package com.saynomoo.mig4android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import net.miginfocom.layout.LayoutUtil;

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
        TextView textView = addLabel(migLayout, label, "gaptop para, span, split 2");
        textView.setTextColor(getResources().getColor(R.color.label));
        View separatorLine = new View(this);
        separatorLine.setMinimumHeight(1);
        separatorLine.setBackgroundColor(getResources().getColor(R.color.separator));
        migLayout.addView(separatorLine, "gapleft rel, gaptop para, growx");
    }

    void addTextField(MigLayout migLayout, String text, String layoutConstraints) {
        EditText editText = new EditText(this);
        editText.setText(text);
        migLayout.addView(editText, layoutConstraints);
    }
    void addTextField(MigLayout migLayout, String layoutConstraints) {
        addTextField(migLayout, "", layoutConstraints);
    }

    TextView addLabel(MigLayout migLayout, final String text, String layoutConstraints) {
        TextView textView = new TextView(this);
        textView.setText(text);
        migLayout.addView(textView, layoutConstraints);
        return textView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuItem item = menu.add("Toggle debug");
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                final int current = LayoutUtil.getGlobalDebugMillis();
                final int millis = current <= 0 ? 1000 : 0;
                LayoutUtil.setGlobalDebugMillis(millis);
                migLayout.invalidate();
                migLayout.requestLayout();
                return true;
            }
        });
        return true;
    }
}
