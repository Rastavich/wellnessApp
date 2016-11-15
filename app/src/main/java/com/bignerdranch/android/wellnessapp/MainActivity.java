package com.bignerdranch.android.wellnessapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;


import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.FacebookSdk;

import static com.facebook.AccessToken.getCurrentAccessToken;

public class MainActivity extends FragmentActivity {
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.fragment_option);
        myDb = new DatabaseHelper(this);

        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HeartRateFragment startFragment = new HeartRateFragment();

        fragmentTransaction.add(R.id.fragment_placeholder, startFragment);
        fragmentTransaction.commit();
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view) {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

    public void onSelectFragment(View view) {
        Fragment newFragment;

        if(view == findViewById(R.id.history_button)) {
            newFragment = new StartFragment;
        } else if (view == findViewById(R.id.heart_button)) {
            newFragment = new HeartRateFragment();
        } else if (view == findViewById(R.id.logout_button)) {
            logout(view);
        }

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_placeholder, newFragment).commit();
    }


}

