package com.fourreau.systemdroidmonitor.ui.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fourreau.systemdroidmonitor.R;
import com.fourreau.systemdroidmonitor.ui.BaseActivity;

import timber.log.Timber;

/**
 * Created by Pierre on 25/04/2015.
 */
public class SummaryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Timber.d("OS version: " + Build.VERSION.RELEASE);
        Timber.d("API level: " + Build.VERSION.SDK_INT);
        Timber.d("Bootloader version: " + Build.BOOTLOADER);
        Timber.d("Brand: " + Build.BRAND);
        Timber.d("Manufacturer : " + Build.MANUFACTURER);
        Timber.d("Model : " + Build.MODEL);
        Timber.d("Device: "+Build.DEVICE);
        Timber.d("Product: "+Build.PRODUCT);

//        Timber.d("Uptime : " + UiUtils.sdf.format(new Date(SystemClock.uptimeMillis())));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseActivity) activity).onSectionAttached(1);
    }

    @Override public void onResume() {
        super.onResume();
    }
}
