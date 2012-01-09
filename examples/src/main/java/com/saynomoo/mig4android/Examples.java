package com.saynomoo.mig4android;

import com.saynomoo.mig4android.test.Tests;

public class Examples extends ActivityListActivity{
    Class[] activities = new Class[]{QuickStart.class, Growing.class, BasicSizes.class, Tests.class, MigLayoutInListView.class};
    @Override
    public Class[] getActivities() {
        return activities;
    }
}
