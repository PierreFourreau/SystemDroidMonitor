package com.fourreau.systemdroidmonitor.ui.fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fourreau.systemdroidmonitor.R;
import com.fourreau.systemdroidmonitor.ui.BaseActivity;
import com.fourreau.systemdroidmonitor.ui.graph.PieGraph;
import com.fourreau.systemdroidmonitor.ui.graph.PieSlice;
import com.fourreau.systemdroidmonitor.util.UiUtils;

import java.io.File;

import timber.log.Timber;

/**
 * Created by Pierre on 25/04/2015.
 */
public class MemoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_memory, container, false);

        //get elements
        TextView textViewRamTotal = (TextView) view.findViewById(R.id.textview_memory_total_ram);
        TextView textViewRamAvailable = (TextView) view.findViewById(R.id.textview_memory_available_ram);
        TextView textViewRamLow = (TextView) view.findViewById(R.id.textview_memory_low_ram);
        TextView textViewRamThreshold = (TextView) view.findViewById(R.id.textview_memory_threshold_ram);

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);

        if (Build.VERSION.SDK_INT >= 16) {
            // percentage can be calculated for API 16+
            Long percentageAvailaible = (long) ((float) mi.availMem / mi.totalMem * 100);
            Timber.d("percent available : " + percentageAvailaible + "%");


            PieGraph pg = (PieGraph) view.findViewById(R.id.graph);
            PieSlice slice = new PieSlice();
            slice.setTitle(getString(R.string.memory_used_ram));
            slice.setColor(Color.parseColor("#AA66CC"));
            slice.setValue(mi.totalMem - mi.availMem);
            pg.addSlice(slice);
            slice = new PieSlice();
            slice.setTitle(getString(R.string.memory_available_ram));
            slice.setColor(Color.parseColor("#FFBB33"));
            slice.setValue(mi.availMem);
            pg.addSlice(slice);
        }

        //System
        Timber.d("Total memory" + UiUtils.humanReadableByteCount(mi.totalMem, true));
        Timber.d("Available memory " + UiUtils.humanReadableByteCount(mi.availMem, true));
        Timber.d("Low memory " + mi.lowMemory);
        Timber.d("Threshold memory " + UiUtils.humanReadableByteCount(mi.threshold, true));

        //Storage
        Timber.d("External memory : " + externalMemoryAvailable());
        Timber.d("Available internal memory size : " + getAvailableInternalMemorySize());
        Timber.d("Total internal memory size : " + getTotalInternalMemorySize());
        Timber.d("Available external memory size : " + getAvailableExternalMemorySize());
        Timber.d("Total external memory size : " + getTotalExternalMemorySize());

        //set elements
        textViewRamTotal.setText(UiUtils.humanReadableByteCount(mi.totalMem, true));
        textViewRamAvailable.setText(UiUtils.humanReadableByteCount(mi.availMem, true));
        textViewRamLow.setText(Boolean.toString(mi.lowMemory));
        textViewRamThreshold.setText(UiUtils.humanReadableByteCount(mi.threshold, true));

        return view;
    }

    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return formatSize(availableBlocks * blockSize);
    }

    public static String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return formatSize(totalBlocks * blockSize);
    }

    public static String getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return formatSize(availableBlocks * blockSize);
        } else {
            return "Not found";
        }
    }

    public static String getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return formatSize(totalBlocks * blockSize);
        } else {
            return "Not found";
        }
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseActivity) activity).onSectionAttached(5);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
