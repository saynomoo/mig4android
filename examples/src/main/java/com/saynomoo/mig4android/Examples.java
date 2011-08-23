package com.saynomoo.mig4android;

public class Examples extends ActivityListActivity{
    Class[] activities = new Class[]{QuickStart.class, Growing.class, BasicSizes.class, AbsolutePosition.class, LogicalPixels.class, AnimateLayout.class};
    @Override
    public Class[] getActivities() {
        return activities;
    }
}
