package com.saynomoo.mig4android.resize;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.saynomoo.mig4android.MigLayout;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.IDEUtil;

public class ComponentResizer {
    public static void openResizer(final View view){
        final MigLayout.LayoutParams oldParams = (MigLayout.LayoutParams) view.getLayoutParams();
        final EditText input = new EditText(view.getContext());
        input.setText(IDEUtil.getConstraintString(oldParams.getConstraints(), false));
        input.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    final CC cc = ConstraintParser.parseComponentConstraint(s.toString());
                    view.setLayoutParams(new MigLayout.LayoutParams(cc));
                } catch (Exception e) {
                }
            }
        });
        new AlertDialog.Builder(view.getContext())
            .setTitle("Update Layout")
            .setView(input)
            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {}
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (view.getLayoutParams() != oldParams) {
                    view.setLayoutParams(oldParams);
                }
            }
        }).show();
    }

}
