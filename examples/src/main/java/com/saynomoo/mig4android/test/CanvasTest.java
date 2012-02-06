package com.saynomoo.mig4android.test;

import android.graphics.Canvas;
import android.opengl.GLSurfaceView;
import com.saynomoo.mig4android.MigActivity;
import com.saynomoo.mig4android.MigLayout;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class CanvasTest extends MigActivity {
    @Override
    public MigLayout createLayout() {
        return new CanvasTestView();
    }
    class CanvasTestView extends MigLayout {
        public CanvasTestView() {
            super(CanvasTest.this);
            addView(new GLSurfaceView(CanvasTest.this){{
              setRenderer(new Renderer() {
                  public void onSurfaceCreated(GL10 gl, EGLConfig config) {}
                  public void onSurfaceChanged(GL10 gl, int width, int height) {}
                  public void onDrawFrame(GL10 gl) {
                      gl.glClearColor(0, 0, 1.0f, 1.0f);
                      gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
                  }
              });
            }}, "wmax 100, hmax 100, wrap");
            addLabel(this, "The blue canvas above does not take up all the space, since it is restricted to \"wmax 100, hmax 100\"", "");
        }
    }
}
