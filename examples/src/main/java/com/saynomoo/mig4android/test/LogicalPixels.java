package com.saynomoo.mig4android.test;

import com.saynomoo.mig4android.MigActivity;
import com.saynomoo.mig4android.MigLayout;
import com.saynomoo.mig4android.ViewGroupWrapper;
import net.miginfocom.layout.PlatformDefaults;

public class LogicalPixels extends MigActivity {
    @Override
    public MigLayout createLayout() {
        return new LogicalPixelsView();
    }
    class LogicalPixelsView extends MigLayout {
        public LogicalPixelsView() {
            super(LogicalPixels.this);
            setLayoutConstraints("wrap 2");
            addLabel(this, getResources().getDisplayMetrics().toString(), "span 2");
            final ViewGroupWrapper w = getParentWrapper();
            final int defaultDPI = PlatformDefaults.getDefaultDPI();
            final String label = "ViewWrapper: " + "{density=" + ((float)w.getHorizontalScreenDPI()) / defaultDPI + ", width=" + w.getScreenWidth() +
                    ", height=" + w.getScreenHeight() + ", scaledDensity=" + '?' +
                    ", xdpi=" + w.getHorizontalScreenDPI() + ", ydpi=" + w.getVerticalScreenDPI() + "}, default DPI: " + defaultDPI;
            addLabel(this, label, "span 2");
            addConstraintLabel(this, "height 100px");
            addConstraintLabel(this, "height 100");

            final String widthPx = "width " + w.getScreenWidth()/2 + "px";
            addLabel(this, widthPx, widthPx + ", wrap");
            final String widthDp = "width " + defaultDPI * (w.getScreenWidth()/2) / w.getHorizontalScreenDPI();
            addLabel(this, widthDp, widthDp + ", wrap");
        }
    }
}
