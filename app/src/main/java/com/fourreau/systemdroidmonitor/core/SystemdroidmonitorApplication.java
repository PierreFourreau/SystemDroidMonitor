package com.fourreau.systemdroidmonitor.core;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.fourreau.systemdroidmonitor.BuildConfig;
import com.fourreau.systemdroidmonitor.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by Pierre on 25/04/2015.
 */
public class SystemdroidmonitorApplication extends Application {

    /**
     * Enum used to identify the tracker that needs to be used for tracking.
     *
     * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
     * storing them all in Application object helps ensure that they are created only once per
     * application instance.
     */
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public synchronized Tracker getTracker(TrackerName trackerId) {

        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(R.xml.app_tracker);
            mTrackers.put(trackerId, t);
        }
        return mTrackers.get(trackerId);
    }

    @Override public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        //init logger
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            // TODO e.g., Crashlytics.log(String.format(message, args));
            Crashlytics.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    Crashlytics.logException(t);
                } else if (priority == Log.WARN) {
                    Crashlytics.log(t.toString());
                }
            }
        }
    }
}
