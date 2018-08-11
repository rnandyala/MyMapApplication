package com.example.rajap.mymapapplication.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.rajap.mymapapplication.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/*

do you want to know more about MapView use the below link!

//https://stackoverflow.com/questions/19353255/how-to-put-google-maps-v2-on-a-fragment-using-viewpager
// below code took from the above link
//https://github.com/dapriett/nativescript-google-maps-sdk/issues/86




*/

/*
implementing an interface that is OnMapReadyCallback

 */
public class Fragment1 extends Fragment implements OnMapReadyCallback {
// GoogleMap Object
    GoogleMap mGoogleMap; // mGoogleMap takes me to particular location I will writing code in onMapReady method of
    //GoogleMap mGoogleMap actually takes me to particular location all action methods are part of mGoogleMap
    /*
    a. addMarker
    b. moveToParticularLocation  or moveCamera


     */
    CoordinatorLayout coordinatorLayout;
    MapView mMapView; // added map as an element to the fragment.xml file
// mMapView has getMapAsync(where I am passing this keyword which triggers my OnMapReadyCallback!) ! tada




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewOfFragment1;

        if (googleServicesAvailable())


        /*
        googleServicesAvailable method is used to  check whether google play services
        available on the user device or not!

        */

        {
            // inflate fragment layout
            viewOfFragment1 = inflater.inflate(R.layout.fragment_fragment1, container, false);

// coordinatorLayout is the parent element in the fragment (fragment_fragment1) which should be specified as parent
            // coordinatorlayout is used for touch events

            // inorder to use for Snackbar look below link video for more explanation

            /*

            https://android-developers.googleblog.com/2015/05/android-design-support-library.html

             */

            coordinatorLayout = (CoordinatorLayout) viewOfFragment1.findViewById(R.id.fragment1_coordinate_layout);



// getting mapVIew element
            // MapView is used to display map
            // using data from google Map services ----
            mMapView = viewOfFragment1.findViewById(R.id.mapView);

            // additional line
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume(); // need to get the map to display immediately
            try {
                // What is the purpose of mapsInitializer
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }


            /*

            OnMapReadyCallback is the interface that this class implemented
            so I can just pass this
              internally think
this  here as I am implementing the interface now you can call the this call method
// to know more about the definition



In practical sense it gets the lat and longitude
places them on the mapview






             */
            mMapView.getMapAsync(this);


            /*

            snack bar with built in snackbar
             */

            Snackbar mySnackbar = Snackbar.make(coordinatorLayout, "map view is working", Snackbar.LENGTH_INDEFINITE)
                    .setAction("do it", v -> {
                        Snackbar.make(coordinatorLayout, "do not mess with maps", Snackbar.LENGTH_INDEFINITE).show();
                    });
            mySnackbar.show();




            return viewOfFragment1;

        } else {

            // If mapView is not present then use the below code

            viewOfFragment1 = inflater.inflate(R.layout.sample_coordinate_layout, container, false);

            Snackbar.make(coordinatorLayout, "map are not available", Snackbar.LENGTH_LONG).show();


            return viewOfFragment1;

        }
    }


    public boolean googleServicesAvailable() {


        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(getActivity());

// If user is having google play service then the below code gets executed!
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        }

        // if user can download using the googleplay use then the below code is executed
        else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(getActivity(), isAvailable, 0);

            dialog.show();
            //          return false;
        }

        // if google play services cannot be downloaded then
        //the below code is executed
        else {

            Snackbar mySnackbar = Snackbar.make(coordinatorLayout, "error", Snackbar.LENGTH_SHORT)
                    .setAction("do it", v -> {
                        Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_LONG).show();
                    });
            mySnackbar.show();
//        return false;
        }


//here return type is used to take care both the elseif and else
        // or I can specify return types separately for else if and else
        return false;


    }





    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    // below method is abstract method of an interface i.e, onMapReadyCallback (all abstract method must be implemented in
    // first concrete class)



    /*
    The below method gets called by the

      mMapView.getMapAsync(this(onMapCallback));
 inside getMapAsync think that there is a call for the
 onMapReady that can be like



     */














    @Override
    public void onMapReady(GoogleMap googleMap) {


        mGoogleMap = googleMap;

        //42.5288523,-71.301984

        // This is where I can pass a  lat and longitude and show markers on the map


        goToParticularLocation(42.5288523, -71.301984,15);



    }

    public void goToParticularLocation(double lat, double lng, float zoom){
        LatLng billerica = new LatLng(lat, lng);

        // adding details to the marker
        mGoogleMap.addMarker(new MarkerOptions().position(billerica).title("Marker in billerica"));

        // CameraUpdate is used to zoom in to the added location
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(billerica, zoom);


        mGoogleMap.moveCamera(cameraUpdate);

    }



}


