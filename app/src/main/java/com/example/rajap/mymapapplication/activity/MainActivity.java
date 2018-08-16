package com.example.rajap.mymapapplication.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import com.example.rajap.mymapapplication.R;
import com.example.rajap.mymapapplication.fragment.Fragment1;

public class MainActivity extends AppCompatActivity {

    private Fragment1 fragment1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// adding fragment to the activity
        setUpFragment();

    }
    public void setUpFragment() {



            fragment1 = new Fragment1();
            FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.frameLayoutForFragment1, fragment1, "firstFragment");
            fragmentTransaction1.commit();


    }



}
