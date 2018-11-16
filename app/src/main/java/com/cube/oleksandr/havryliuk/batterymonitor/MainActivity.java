package com.cube.oleksandr.havryliuk.batterymonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Intent.ACTION_POWER_CONNECTED;
import static android.content.Intent.ACTION_POWER_DISCONNECTED;

public class MainActivity extends AppCompatActivity {

    private TextView chargingStateTextView;
    private TextView chargingLevelTextView;
    private ImageView batteryImageView;
    private Intent batteryIntent;
    private ChargerReceiver connectedReceiver;
    private ChargerReceiver disConnectedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initReceivers();

        getBatteryState(isDeviceCharging());
    }


    public void initViews() {
        chargingStateTextView = findViewById(R.id.charging_state_textview);
        chargingLevelTextView = findViewById(R.id.battery_level_textview);
        batteryImageView = findViewById(R.id.battery_imageview);
    }

    public void initReceivers() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryIntent = registerReceiver(null, ifilter);

        connectedReceiver = new ChargerReceiver();
        registerReceiver(connectedReceiver,
                new IntentFilter(ACTION_POWER_CONNECTED));

        disConnectedReceiver = new ChargerReceiver();
        registerReceiver(disConnectedReceiver,
                new IntentFilter(ACTION_POWER_DISCONNECTED));
    }

    public void getBatteryState(boolean status) {

        if (status) {
            chargingStateTextView.setText("Battery charging ...");
        } else {
            chargingStateTextView.setText("Battery not charging");
        }

        int batteryLevel = batteryLevel();
        chargingLevelTextView.setText(String.valueOf(batteryLevel + "%"));

        if (batteryLevel < 30) {
            if (status) {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_charging_20_24px);
            } else {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_20_24px);
            }
            return;
        }
        if (batteryLevel < 50) {
            if (status) {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_charging_30_24px);
            } else {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_30_24px);
            }
            return;
        }
        if (batteryLevel < 60) {
            if (status) {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_charging_50_24px);
            } else {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_50_24px);
            }
            return;
        }
        if (batteryLevel < 80) {
            if (status) {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_charging_60_24px);
            } else {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_60_24px);
            }
            return;
        }
        if (batteryLevel < 90) {
            if (status) {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_charging_80_24px);
            } else {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_80_24px);
            }
            return;
        }
        if (batteryLevel < 100) {
            if (status) {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_charging_90_24px);
            } else {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_90_24px);
            }
            return;
        }
        if (batteryLevel == 100) {
            if (status) {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_charging_full_24px);
            } else {
                batteryImageView.setImageResource(R.drawable.ic_baseline_battery_full_24px);
            }
            return;
        }
    }

    public boolean isDeviceCharging() {
        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean chargingStatus = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        return chargingStatus;
    }

    public int batteryLevel() {
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        return level;
    }

    public class ChargerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())) {
                getBatteryState(true);
            }
            if (Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {
                getBatteryState(false);
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(connectedReceiver);
        unregisterReceiver(disConnectedReceiver);
    }
}