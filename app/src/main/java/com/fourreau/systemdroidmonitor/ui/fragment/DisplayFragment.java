package com.fourreau.systemdroidmonitor.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fourreau.systemdroidmonitor.R;
import com.fourreau.systemdroidmonitor.ui.activity.BaseActivity;

import timber.log.Timber;

/**
 * Created by Pierre on 25/04/2015.
 */
public class DisplayFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_display, container, false);

        //get elements
        TextView textViewDisplayDensityDpi = (TextView) view.findViewById(R.id.textview_display_density_dpi);
        TextView textViewDisplayDensityScaled = (TextView) view.findViewById(R.id.textview_display_density_scaled);
        TextView textViewDisplayDensityXdpi = (TextView) view.findViewById(R.id.textview_display_xdpi);
        TextView textViewDisplayDensityYdpi = (TextView) view.findViewById(R.id.textview_display_ydpi);
        TextView textViewDisplayDensityDefault = (TextView) view.findViewById(R.id.textview_display_density_default);
        TextView textViewDisplayDensityLow = (TextView) view.findViewById(R.id.textview_display_density_low);
        TextView textViewDisplayDensityMedium = (TextView) view.findViewById(R.id.textview_display_density_medium);
        TextView textViewDisplayDensityHigh = (TextView) view.findViewById(R.id.textview_display_density_high);
        TextView textViewDisplayHeight = (TextView) view.findViewById(R.id.textview_display_height);
        TextView textViewDisplayWidth = (TextView) view.findViewById(R.id.textview_display_width);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //set elements
        textViewDisplayDensityDpi.setText(""+metrics.densityDpi+" dpi");
        textViewDisplayDensityScaled.setText(""+metrics.scaledDensity);
        textViewDisplayDensityXdpi.setText(""+metrics.xdpi+" dpi");
        textViewDisplayDensityYdpi.setText(""+metrics.ydpi+" dpi");
        textViewDisplayDensityDefault.setText(""+DisplayMetrics.DENSITY_DEFAULT);
        textViewDisplayDensityLow.setText(""+DisplayMetrics.DENSITY_LOW);
        textViewDisplayDensityMedium.setText(""+DisplayMetrics.DENSITY_MEDIUM);
        textViewDisplayDensityHigh.setText(""+DisplayMetrics.DENSITY_HIGH);
        textViewDisplayHeight.setText(""+metrics.heightPixels+" px");
        textViewDisplayWidth.setText(""+metrics.widthPixels+" px");


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

        return view;
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