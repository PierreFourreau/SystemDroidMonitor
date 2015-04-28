package com.fourreau.systemdroidmonitor.ui.fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fourreau.systemdroidmonitor.R;
import com.fourreau.systemdroidmonitor.ui.activity.BaseActivity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import timber.log.Timber;

/**
 * Created by Pierre on 25/04/2015.
 */
public class SummaryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        //get elements
        TextView textViewSummaryOs = (TextView) view.findViewById(R.id.textview_summary_os);
        TextView textViewSummaryApi = (TextView) view.findViewById(R.id.textview_summary_api);
        TextView textViewSummaryBootloader = (TextView) view.findViewById(R.id.textview_summary_bootloader);
        TextView textViewSummaryBrand = (TextView) view.findViewById(R.id.textview_summary_brand);
        TextView textViewSummaryDevice = (TextView) view.findViewById(R.id.textview_summary_device);
        TextView textViewSummaryModel = (TextView) view.findViewById(R.id.textview_summary_model);
        TextView textViewSummaryManufacturer = (TextView) view.findViewById(R.id.textview_summary_manufacturer);
        TextView textViewSummaryProduct = (TextView) view.findViewById(R.id.textview_summary_product);
        TextView textViewSummarySerial = (TextView) view.findViewById(R.id.textview_summary_serial);

        //set elements
        textViewSummaryOs.setText(Build.VERSION.RELEASE);
        textViewSummaryApi.setText(""+Build.VERSION.SDK_INT);
        textViewSummaryBootloader.setText(Build.BOOTLOADER);
        textViewSummaryBrand.setText(Build.BRAND);
        textViewSummaryDevice.setText(Build.DEVICE);
        textViewSummaryModel.setText(Build.MODEL);
        textViewSummaryManufacturer.setText(Build.MANUFACTURER);
        textViewSummaryProduct.setText(Build.PRODUCT);
        textViewSummarySerial.setText(Build.SERIAL);

        Timber.d("OS version: " + Build.VERSION.RELEASE);
        Timber.d("API level: " + Build.VERSION.SDK_INT);
        Timber.d("Bootloader version: " + Build.BOOTLOADER);
        Timber.d("Brand: " + Build.BRAND);
        Timber.d("Manufacturer : " + Build.MANUFACTURER);
        Timber.d("Model : " + Build.MODEL);
        Timber.d("Device: "+Build.DEVICE);
        Timber.d("Product: "+Build.PRODUCT);
        Timber.d("Hardware serial: "+Build.SERIAL);

        return view;
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
