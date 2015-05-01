package com.fourreau.systemdroidmonitor.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fourreau.systemdroidmonitor.R;
import com.fourreau.systemdroidmonitor.core.SystemdroidmonitorApplication;
import com.fourreau.systemdroidmonitor.ui.activity.BaseActivity;
import com.fourreau.systemdroidmonitor.ui.graph.PieGraph;
import com.fourreau.systemdroidmonitor.ui.graph.PieSlice;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import timber.log.Timber;

/**
 * Created by Pierre on 25/04/2015.
 */
public class BatteryFragment extends Fragment {

    private  TextView textViewBatteryHealth, textViewBatteryLevel, textViewBatteryPlugged, textViewBatteryPresent, textViewBatteryTechnology, textViewBatteryTemperature, textViewBatteryVoltage;
    private PieGraph pg;
    private String batteryChargedLabel, batteryRemainingLabel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get tracker.
        Tracker t = ((SystemdroidmonitorApplication) getActivity().getApplication()).getTracker(SystemdroidmonitorApplication.TrackerName.APP_TRACKER);

        // Set screen name.
        t.setScreenName("BatteryFragment");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        getActivity().registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        View view = inflater.inflate(R.layout.fragment_battery, container, false);

        //get elements
        pg = (PieGraph) view.findViewById(R.id.graphBattery);
        textViewBatteryHealth = (TextView) view.findViewById(R.id.textview_battery_health);
        textViewBatteryLevel = (TextView) view.findViewById(R.id.textview_battery_level);
        textViewBatteryPlugged = (TextView) view.findViewById(R.id.textview_battery_plugged);
        textViewBatteryPresent = (TextView) view.findViewById(R.id.textview_battery_present);
        textViewBatteryTechnology = (TextView) view.findViewById(R.id.textview_battery_technology);
        textViewBatteryTemperature = (TextView) view.findViewById(R.id.textview_battery_temperature);
        textViewBatteryVoltage = (TextView) view.findViewById(R.id.textview_battery_voltage);

        batteryChargedLabel = getString(R.string.battery_charged);
        batteryRemainingLabel = getString(R.string.battery_remaining);
        return view;
    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int  health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            int  level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int  plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
            boolean  present = intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
            String  technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            float temperature = ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0) / 10);
            int  voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);

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

            textViewBatteryHealth.setText(healthBattery);
            textViewBatteryLevel.setText(level + " %");
            textViewBatteryPlugged.setText(pluggedType);
            textViewBatteryPresent.setText(""+present);
            textViewBatteryTechnology.setText(technology);
            textViewBatteryTemperature.setText(""+temperature+ "°");
            textViewBatteryVoltage.setText(""+voltage + " mV");

            PieSlice slice = new PieSlice();
            slice.setTitle(batteryChargedLabel);
            slice.setColor(Color.parseColor("#AA66CC"));
            slice.setValue(level);
            pg.addSlice(slice);
            //if battery charged, there is just one slice
            if(level != 100) {
                slice = new PieSlice();
                slice.setTitle(batteryRemainingLabel);
                slice.setColor(Color.parseColor("#FFBB33"));
                slice.setValue(100 - level);
                pg.addSlice(slice);
            }

            Timber.d(
                    "Health: "+healthBattery+"-"+
                    "Level: "+level+"%-"+
                    "Plugged: "+pluggedType+"-"+
                    "Present battery: "+present+"-"+
                    "Technology: "+technology+"-"+
                    "Temperature: "+temperature+"° -"+
                    "Voltage: "+voltage+" mV");
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
