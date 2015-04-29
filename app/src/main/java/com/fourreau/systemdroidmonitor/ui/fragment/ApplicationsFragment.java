package com.fourreau.systemdroidmonitor.ui.fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.fourreau.systemdroidmonitor.core.adapter.ListViewAppsAdapter;
import com.fourreau.systemdroidmonitor.core.model.ListViewItemApp;
import com.fourreau.systemdroidmonitor.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import timber.log.Timber;

/**
 * Created by Pierre on 28/04/2015.
 */
public class ApplicationsFragment extends ListFragment {

    private List<ListViewItemApp> mItems = new ArrayList<ListViewItemApp>();;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityManager am = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);

        String[] activePackages;
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
        activePackages = getActivePackages(am);
//            } else {
//                activePackages = getActivePackagesCompat(am);
//            }
        if (activePackages != null) {
            for (String activePackage : activePackages) {
                try {
                    Drawable icon = getActivity().getPackageManager().getApplicationIcon(activePackage);
//                        getActivity().getPackageManager().getApplicationLabel()
                    ApplicationInfo appInfo = getActivity().getPackageManager().getApplicationInfo(activePackage, 0x00000080);
                    mItems.add(new ListViewItemApp(icon, getActivity().getPackageManager().getApplicationLabel(appInfo).toString(), activePackage));
                }
                catch ( PackageManager.NameNotFoundException e ) {
                    Timber.e("ApplicationsFragment:PackageManager.NameNotFoundException", e.toString());
                }
            }
        }
        setListAdapter(new ListViewAppsAdapter(getActivity(), mItems));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ListViewItemApp item = mItems.get(position);
        Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseActivity) activity).onSectionAttached(1);
    }

    String[] getActivePackagesCompat(ActivityManager mActivityManager) {
        final List<ActivityManager.RunningTaskInfo> taskInfo = mActivityManager.getRunningTasks(1);
        final ComponentName componentName = taskInfo.get(0).topActivity;
        final String[] activePackages = new String[1];
        activePackages[0] = componentName.getPackageName();
        return activePackages;
    }

    String[] getActivePackages(ActivityManager mActivityManager) {
        final Set<String> activePackages = new HashSet<String>();
        final List<ActivityManager.RunningAppProcessInfo> processInfos = mActivityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                activePackages.addAll(Arrays.asList(processInfo.pkgList));
            }
        }
        return activePackages.toArray(new String[activePackages.size()]);
    }
}