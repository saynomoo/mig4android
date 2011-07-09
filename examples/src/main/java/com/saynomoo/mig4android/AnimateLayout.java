package com.saynomoo.mig4android;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnimateLayout extends MigActivity{
    @Override
    public MigLayout createLayout() {
        return new AnimateView();
    }

    class AnimateView extends MigLayout {
        public AnimateView() {
            super(AnimateLayout.this);
            setColumnConstraints("[]15[]25[]");
            setRowConstraints("[]15");
            final TextView upMessage = addLabel(this, "Message From Up", "wrap");
            upMessage.setVisibility(View.GONE);

            final TextView leftMessage = addLabel(this, "Message From Left", "");
            addView(new Button(getContext()){{
              setText("Left");
              setOnClickListener(new OnClickListener() {
                  public void onClick(View v) {
                  }
              });
            }}, "");
            addView(new Button(getContext()){{
              setText("Up");
            }}, "");
        }
    }

}

