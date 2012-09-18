package com.saynomoo.mig4android.test;

import android.R;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.opengl.GLSurfaceView;
import android.view.View;
import android.widget.Button;
import com.saynomoo.mig4android.MigActivity;
import com.saynomoo.mig4android.MigLayout;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PaddingTest extends MigActivity {
    @Override
    public MigLayout createLayout() {
        return new PaddingTestView();
    }
    class PaddingTestView extends MigLayout {
        public PaddingTestView() {
            super(PaddingTest.this);
            setLayoutConstraints("debug, ins 10");
            setPadding(10, 10, 10, 10);
            addView(new Button(getContext()) {{
                setText("First");
//                getBackground().setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.DARKEN);
                setBackgroundColor(0x55FF0000);
                setPadding(10, 5, 10, 5);
            }}, "grow, push");
            addView(new Button(getContext()) {{
                setText("Second");
                setPadding(10, 5, 10, 5);
//                getBackground().setColorFilter(Color.parseColor("#00ff00"), PorterDuff.Mode.DARKEN);
                setBackgroundColor(0x5500FF00);
            }}, "grow, push");
        }
    }
}
