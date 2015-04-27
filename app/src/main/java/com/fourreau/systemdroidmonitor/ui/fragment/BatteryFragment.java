package com.fourreau.systemdroidmonitor.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fourreau.systemdroidmonitor.R;
import com.fourreau.systemdroidmonitor.ui.activity.BaseActivity;

import timber.log.Timber;

/**
 * Created by Pierre on 25/04/2015.
 */
public class BatteryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_battery, container, false);
    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int  health= intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            int  icon_small= intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL,0);
            int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
            boolean  present= intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
            String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            float temperature = ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0) / 10);
            int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);

            String healthBattery;
            //health
            switch(health) {
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    healthBattery = "Good";
                break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    healthBattery = "Overheating";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    healthBattery = "Over voltage";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    healthBattery = "Unknown";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    healthBattery = "Unspecified failure";
                    break;
                default:
                    healthBattery = "Not found";
                    break;
            }

            //plugged
            String pluggedType;
            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_AC :
                    pluggedType = "AC charger";
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB :
                    pluggedType = "USB charger";
                    break;
                case 0:
                    pluggedType = "No, on battery power";
                    break;
                default:
                    pluggedType = "Not found";
                    break;
            }

            Timber.d(
                    "Health: "+healthBattery+"\n"+
                            "Icon Small:"+icon_small+"\n"+
                            "Level: "+level+"%\n"+
                            "Plugged: "+pluggedType+"\n"+
                            "Present battery: "+present+"\n"+
                            "Technology: "+technology+"\n"+
                            "Temperature: "+temperature+"° \n"+
                            "Voltage: "+voltage+" mV\n");
                    }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseActivity) activity).onSectionAttached(4);
    }

    @Override public void onResume() {
        super.onResume();
    }
}
