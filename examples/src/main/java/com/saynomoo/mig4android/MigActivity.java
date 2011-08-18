package com.saynomoo.mig4android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.saynomoo.mig4android.resize.ComponentResizer;
import net.miginfocom.layout.LayoutUtil;

public abstract class MigActivity extends Activity {

    protected MigLayout migLayout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        migLayout = createLayout();
        setContentView(migLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
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
        editText.setTextSize(new TextView(this).getTextSize());
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
    void addConstraintLabel(MigLayout migLayout, final String constraint) {
        migLayout.addView(new TextView(this) {{
            setText(constraint);
            setBackgroundResource(android.R.color.background_light);
        }}, constraint);
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
        final MenuItem edit = menu.add("Toggle edit");
        edit.setCheckable(true);
        edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            View.OnLongClickListener editListener = new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    ComponentResizer.openResizer(v);
                    return true;
                }
            };
            public boolean onMenuItemClick(MenuItem item) {
                edit.setChecked(!edit.isChecked());
                final View.OnLongClickListener listenerToSet = edit.isChecked() ? editListener : null;
                for (ViewWrapper w : migLayout.getParentWrapper().getComponents()) {
                    w.getComponent().setOnLongClickListener(listenerToSet);
                }
                return true;
            }
        });
        return true;
    }
}
