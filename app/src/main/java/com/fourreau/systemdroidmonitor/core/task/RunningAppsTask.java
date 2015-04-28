//package com.fourreau.systemdroidmonitor.core.task;
//
//import android.app.ActivityManager;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.graphics.drawable.Drawable;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.widget.Toast;
//
//import com.fourreau.systemdroidmonitor.core.adapter.ListViewAppsAdapter;
//import com.fourreau.systemdroidmonitor.core.model.ListViewItemApp;
//import com.fourreau.systemdroidmonitor.ui.activity.BaseActivity;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import timber.log.Timber;
//
///**
// * Created by Pierre on 28/04/2015.
// */
//public class RunningAppsTask extends AsyncTask<String, Void, List<ListViewItemApp>> {
//
//    private Context mContext;
//    List<ListViewItemApp> mItems = new ArrayList<ListViewItemApp>();
//
//    public RunningAppsTask(Context context){
//        this.mContext = context;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected List<ListViewItemApp> doInBackground(String... params) {
//
//
//        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
//
//        String[] activePackages;
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
//            activePackages = getActivePackages(am);
//        } else {
//            activePackages = getActivePackagesCompat(am);
//        }
//        if (activePackages != null) {
//            for (String activePackage : activePackages) {
//                try {
//                    Drawable icon = mContext.getPackageManager().getApplicationIcon(activePackage);
//                    mItems.add(new ListViewItemApp(icon, activePackage, activePackage));
//                }
//                catch ( PackageManager.NameNotFoundException e ) {
//                    //e.printStackTrace();
//                }
//                Timber.d("");
//                if (activePackage.equals("com.google.android.calendar")) {
//                    //Calendar app is launched, do something
//                }
//            }
//        }
//
//        return mItems;
//    }
//
//    @Override
//    protected void onPostExecute(List<ListViewItemApp> apps) {
//
//        // initialize and set the list adapter
//        mContext.get.setListAdapter(new ListViewAppsAdapter(mContext, mItems));
//
//        Toast.makeText(mContext, "Number of running applications : " + apps.size(), Toast.LENGTH_LONG).show();
//    }
//
//    String[] getActivePackagesCompat(ActivityManager mActivityManager) {
//        final List<ActivityManager.RunningTaskInfo> taskInfo = mActivityManager.getRunningTasks(1);
//        final ComponentName componentName = taskInfo.get(0).topActivity;
//        final String[] activePackages = new String[1];
//        activePackages[0] = componentName.getPackageName();
//        return activePackages;
//    }
//
//    String[] getActivePackages(ActivityManager mActivityManager) {
//        final Set<String> activePackages = new HashSet<String>();
//        final List<ActivityManager.RunningAppProcessInfo> processInfos = mActivityManager.getRunningAppProcesses();
//        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
//            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                activePackages.addAll(Arrays.asList(processInfo.pkgList));
//            }
//        }
//        return activePackages.toArray(new String[activePackages.size()]);
//    }
//
//}