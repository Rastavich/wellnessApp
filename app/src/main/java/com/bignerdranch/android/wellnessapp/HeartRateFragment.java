package com.bignerdranch.android.wellnessapp;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

import java.util.List;


public class HeartRateFragment extends Fragment implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mHeart;


    public HeartRateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);


        List<Sensor> sensorTypes = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for(int i=0; i<sensorTypes.size(); i++) {

        }


        if( mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT) != null) {
            //There is a heartbeat sensor
        } else {
            //No sensor, cannot find heartrate on this device
        }
        mHeart = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_heart_rate, container, false);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float lux = event.values[0];
        // Do something with this sensor value.
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mHeart, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }




}

