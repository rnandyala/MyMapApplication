package com.example.rajap.mymapapplication.activity;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rajap.mymapapplication.R;
import com.example.rajap.mymapapplication.fragment.Fragment1;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.Duration;

public class MainActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayoutMainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*

        The below method
        adds fragment to the main activity
         */

        setMapFragment();

    }
    public void setMapFragment() {
        Fragment1 fragment1 = new Fragment1();
        FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
        fragmentTransaction1.add(R.id.frameLayoutForFragment1, fragment1, "firstFragment");
        fragmentTransaction1.commit();
    }





}
