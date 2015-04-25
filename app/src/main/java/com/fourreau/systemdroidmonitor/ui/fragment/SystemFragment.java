package com.fourreau.systemdroidmonitor.ui.fragment;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fourreau.systemdroidmonitor.R;

import timber.log.Timber;

/**
 * Created by Pierre on 25/04/2015.
 */
public class SystemFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Wifi Service
        WifiManager manager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = manager.getConnectionInfo();
        String wifiString = wifiInfo.getMacAddress();

        Timber.d("WiFi MAC Address " + wifiString);

        // Hardware Serial Number
        String serialString = Build.SERIAL;
        Timber.d("Hardware Serial " + serialString);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system, container, false);
    }

    @Override public void onResume() {
        super.onResume();
    }
}
