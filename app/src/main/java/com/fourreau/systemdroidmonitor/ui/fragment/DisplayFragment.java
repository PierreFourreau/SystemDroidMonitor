package com.fourreau.systemdroidmonitor.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fourreau.systemdroidmonitor.R;
import com.fourreau.systemdroidmonitor.ui.BaseActivity;

import timber.log.Timber;

/**
 * Created by Pierre on 25/04/2015.
 */
    public class DisplayFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

            Timber.d("density: "+metrics.density);
            Timber.d("densityDpi: " + metrics.densityDpi);
            Timber.d("scaledDensity: " + metrics.scaledDensity);
            Timber.d("xdpi: " + metrics.xdpi);
            Timber.d("ydpi: " + metrics.ydpi);

            Timber.d("** Density reference:");
            Timber.d("DENSITY_DEFAULT: " + DisplayMetrics.DENSITY_DEFAULT);
            Timber.d("DENSITY_LOW: " + DisplayMetrics.DENSITY_LOW);
            Timber.d("DENSITY_MEDIUM: " + DisplayMetrics.DENSITY_MEDIUM);
            Timber.d("DENSITY_HIGH: " + DisplayMetrics.DENSITY_HIGH);

            Timber.d("** Screen:");
            Timber.d("heightPixels: " + metrics.heightPixels);
            Timber.d("widthPixels: "+metrics.widthPixels);

            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_display, container, false);
        }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseActivity) activity).onSectionAttached(3);
    }

        @Override public void onResume() {
            super.onResume();
        }
    }