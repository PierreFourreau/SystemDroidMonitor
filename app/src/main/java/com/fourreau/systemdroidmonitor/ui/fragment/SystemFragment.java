package com.fourreau.systemdroidmonitor.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fourreau.systemdroidmonitor.R;
import com.fourreau.systemdroidmonitor.core.SystemdroidmonitorApplication;
import com.fourreau.systemdroidmonitor.ui.activity.BaseActivity;
import com.fourreau.systemdroidmonitor.util.UiUtils;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.apache.http.conn.util.InetAddressUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Pierre on 25/04/2015.
 */
public class SystemFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_system, container, false);

        // Get tracker.
        Tracker t = ((SystemdroidmonitorApplication) getActivity().getApplication()).getTracker(SystemdroidmonitorApplication.TrackerName.APP_TRACKER);

        // Set screen name.
        t.setScreenName("SystemFragment");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        // Wifi Service
        WifiManager manager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = manager.getConnectionInfo();
        String wifiString = wifiInfo.getMacAddress();

        //get elements
        TextView textViewSystemWifiMac = (TextView) view.findViewById(R.id.textview_system_wifi_mac);
        TextView textViewSystemWifiIpv4 = (TextView) view.findViewById(R.id.textview_system_wifi_ipv4);
        TextView textViewSystemWifiIpv6 = (TextView) view.findViewById(R.id.textview_system_wifi_ipv6);
        TextView textViewSystemCpu = (TextView) view.findViewById(R.id.textview_system_wifi_cpu);

        //set elements
        textViewSystemWifiMac.setText(wifiInfo.getMacAddress());
        textViewSystemWifiIpv4.setText(getIPAddress(true));
        textViewSystemWifiIpv6.setText(getIPAddress(false));
        textViewSystemCpu.setText(readCpuInfo());

        Timber.d("cpu:"+readCpuInfo());

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseActivity) activity).onSectionAttached(2);
    }

    @Override public void onResume() {
        super.onResume();
    }

    /**
     * Get IP address from first non-localhost interface
     * @param ipv4  true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim<0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            return "";
        } // for now eat exceptions
        return "";
    }

    private String readCpuInfo()
    {
        ProcessBuilder cmd;
        String result="";

        try{
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[1024];
            while(in.read(re) != -1){
                result = result + new String(re);
            }
            in.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return result;
    }
}
