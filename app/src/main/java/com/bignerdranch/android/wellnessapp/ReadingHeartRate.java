package com.bignerdranch.android.wellnessapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.sensorextension.Ssensor;
import com.samsung.android.sdk.sensorextension.SsensorEvent;
import com.samsung.android.sdk.sensorextension.SsensorEventListener;
import com.samsung.android.sdk.sensorextension.SsensorExtension;
import com.samsung.android.sdk.sensorextension.SsensorManager;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.sensorextension.Ssensor;
import com.samsung.android.sdk.sensorextension.SsensorEvent;
import com.samsung.android.sdk.sensorextension.SsensorEventListener;
import com.samsung.android.sdk.sensorextension.SsensorExtension;
import com.samsung.android.sdk.sensorextension.SsensorManager;

/*
    Code used from the Sensor Extension Programming guide
    made available once the SDK has been released to you.
*/

public class ReadingHeartRate extends Activity {

    Ssensor ir = null;
    Ssensor red = null;

    //private final static int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 101;
    ToggleButton btn_start = null;
    TextView tIR = null;
    TextView tRED = null;

    SSListenerIR mSSListenerIR = null;
    SSListenerRED mSSListenerRED = null;

    SsensorManager mSSensorManager = null;
    SsensorExtension mSsensorExtension = null;
    Activity mContext;

    protected void onCreate(Bundle savedInstanceState) {

        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_heart_rate);

        btn_start = (ToggleButton) findViewById(R.id.btn_start);
        tIR = (TextView) findViewById(R.id.tIR);
        tRED = (TextView) findViewById(R.id.tRED);

        mSSListenerIR = new SSListenerIR();
        mSSListenerRED = new SSListenerRED();
        if (btn_start != null) {
            btn_start.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    btn_start.setSelected(!btn_start.isSelected());

                    try {
                        if (!btn_start.isSelected()) {
                            // HRM OFF
                            btn_start.setText(btn_start.getTextOff());
                            mSSensorManager.unregisterListener(mSSListenerIR,
                                    ir);
                            mSSensorManager.unregisterListener(mSSListenerRED,
                                    red);
                        } else {
                            mSsensorExtension = new SsensorExtension();
                            try {
                                mSsensorExtension.initialize(mContext);
                                mSSensorManager = new SsensorManager(mContext,
                                        mSsensorExtension);
                                ir = mSSensorManager
                                        .getDefaultSensor(Ssensor.TYPE_HRM_LED_IR);
                                red = mSSensorManager
                                        .getDefaultSensor(Ssensor.TYPE_HRM_LED_RED);

                            } catch (SsdkUnsupportedException e) {
                                Toast.makeText(mContext, e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                                mContext.finish();
                            } catch (IllegalArgumentException e) {
                                Toast.makeText(mContext, e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                                mContext.finish();
                            } catch (SecurityException e) {
                                Toast.makeText(mContext, e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                                mContext.finish();
                            }
                            // HRM ON
                            btn_start.setText(btn_start.getTextOn());
                            if (mSSensorManager != null) {
                                mSSensorManager.registerListener(mSSListenerIR,
                                        ir, SensorManager.SENSOR_DELAY_NORMAL);
                                mSSensorManager.registerListener(
                                        mSSListenerRED, red,
                                        SensorManager.SENSOR_DELAY_NORMAL);
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        ErrorToast(e);
                    }
                }
            });
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (shouldShowRequestPermissionRationale(Manifest.permission.BODY_SENSORS)) {
                        // Explain to the user why we need to read the contacts
                    }

                    requestPermissions(
                            new String[] { Manifest.permission.BODY_SENSORS },
                            101);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant

                    return;
                }
            }
        }
    }

    public void ErrorToast(IllegalArgumentException e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        btn_start.setSelected(!btn_start.isSelected());
        btn_start.setText(btn_start.getTextOff());
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            if (ir != null) {
                mSSensorManager.unregisterListener(mSSListenerIR, ir);
                tIR.setText("");
            }
            if (red != null) {
                mSSensorManager.unregisterListener(mSSListenerRED, red);
                tRED.setText("");
            }
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            this.finish();
        } catch (IllegalStateException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            this.finish();
        }

    }

    private class SSListenerIR implements SsensorEventListener {

        @Override
        public void OnAccuracyChanged(Ssensor arg0, int arg1) {
            // TODO Auto-generated method stub
        }

        @Override
        public void OnSensorChanged(SsensorEvent event) {
            // TODO Auto-generated method stub

            Ssensor sIr = event.sensor;
            StringBuffer sb = new StringBuffer();
            sb.append("==== Sensor Information ====\n")
                    .append("IR RAW DATA(HRM) : " + event.values[0] + "\n");

            tIR.setText(sb.toString());
        }
    }

    private class SSListenerRED implements SsensorEventListener {

        @Override
        public void OnAccuracyChanged(Ssensor arg0, int arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public void OnSensorChanged(SsensorEvent event) {
            // TODO Auto-generated method stub

            // Handling SsensorEvent with SSensorEventListener.
            Ssensor sIr = event.sensor;
            StringBuffer sb = new StringBuffer();
            sb.append("==== Sensor Information ====\n")
                    .append("RED LED RAW DATA(HRM) : " + event.values[0] + "\n");
            tRED.setText(sb.toString());
        }
    }
}

