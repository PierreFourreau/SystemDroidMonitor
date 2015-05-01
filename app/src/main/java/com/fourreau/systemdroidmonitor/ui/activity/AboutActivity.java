package com.fourreau.systemdroidmonitor.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.fourreau.systemdroidmonitor.R;
import com.fourreau.systemdroidmonitor.core.SystemdroidmonitorApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Get tracker.
        Tracker t = ((SystemdroidmonitorApplication) getApplication()).getTracker(SystemdroidmonitorApplication.TrackerName.APP_TRACKER);

        // Set screen name.
        t.setScreenName("AboutActivity");
        t.send(new HitBuilders.ScreenViewBuilder().build());

    }
}
