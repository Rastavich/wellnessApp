package com.bignerdranch.android.wellnessapp;


import android.app.Fragment;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

public class SensorActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sensor);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
        }
    }
}


