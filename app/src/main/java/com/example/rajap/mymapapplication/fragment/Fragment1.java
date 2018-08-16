package com.example.rajap.mymapapplication.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.androidadvance.topsnackbar.TSnackbar;
import com.example.rajap.mymapapplication.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.Manifest;
import java.util.List;
import java.util.Locale;
import static android.support.v4.content.ContextCompat.checkSelfPermission;
/*

The purpose of this class is add map fragment and display locations on the mapview
a. MapView class is used to add map .. MapView has onMapReady method this is used inorder initialize GoogleMap
b. I can add acitons to the googleMap such as
i. add marker
ii. move camera!
getting current requires a lot of code and I am adding that in this Fragment
 */



/*
adding MapView into a fragment use the below link!
//https://stackoverflow.com/questions/19353255/how-to-put-google-maps-v2-on-a-fragment-using-viewpager
// below code took from the above link
//https://github.com/dapriett/nativescript-google-maps-sdk/issues/86
*/


/*
implementing an interface that is OnMapReadyCallback
 */


public class Fragment1 extends Fragment implements OnMapReadyCallback {

// global declaration for addresses!
    /*The purpose of this attribute is to get the address based on the latitude and longitude
    */
    List<Address> addresses ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// retains the instance across the configuration changes!!
        //currently I am not retaining the fragment as
  //setRetainInstance(true);

    }

    // GoogleMap Object
     // mGoogleMap takes me to particular location I will be writing code in onMapReady method of
    //GoogleMap mGoogleMap actually takes me to a particular location all action methods are part of mGoogleMap
    /*
    a. addMarker
    b. moveToParticularLocation  or moveCamera
     */
    GoogleMap mGoogleMap;
    CoordinatorLayout coordinatorLayout;  // this is the parent element in my entire xml file why because to display snackbar I am using this attribute
    MapView mMapView; // added mapView as an element to the fragment.xml file
// mMapView has getMapAsync(where I am passing this keyword which triggers my OnMapReadyCallback!) ! tada


    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    PendingResult<LocationSettingsResult> result;
    private Activity mActivity;

    PendingIntent pendingIntent;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;

    /*
    The purpose this method is to trigger allow or deny current location
    This gets triggered after request result and the requestCode from requestresult is passed to
    the onRequestPermissionResult

     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);



        if ((requestCode == 1001 && grantResults[0] == PackageManager.PERMISSION_GRANTED) && (requestCode == 1001 && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {

            /*
            Why fusedLocationClient is giving CTE?

This is the latest way of finding the currentlocation using the fusedlocation
lot of boiler plate code was present in
GoogleAPIClient and LocationManagerAPI
All those API are now deprecated
             */

            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLastLocation = task.getResult();
                                mLastLocation.getLongitude();
                                mLastLocation.getLatitude();
                                Toast.makeText(getActivity(), mLastLocation.getLatitude() + "," + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
goToParticularLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude(),15);

                            }
                        }
                    });


        } else {

            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(getActivity(), "Should accept permission to access near by places", Toast.LENGTH_SHORT).show();
            } else {
                //if user checks do not show again check box this is executed
                Toast.makeText(getActivity(), "user denied the permissions", Toast.LENGTH_SHORT).show();

            }
        }
    }


    public void getFusedLocationProviderClientAndAddedRequiredPermissionsToGetLatAndLongitude() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

// checkSelfPermission if permission is granted prior this executes
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLastLocation = task.getResult();
                                mLastLocation.getLongitude();
                                mLastLocation.getLatitude();

                                // TSnackbar is used to put the alert on top of the screen instead of bottom
                                TSnackbar.make(coordinatorLayout,mLastLocation.getLongitude()+", "+mLastLocation.getLatitude(),TSnackbar.LENGTH_LONG).show();
goToParticularLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(),15);
                            }
                        }
                    });


        } else {
            //requesting permission
            // If user is asked permission for the first this gets executed...

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
        }


    }
















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
            // what is happening on line mMapView.onCreate(savedInstanceState)
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume(); // need to get the map to display immediately
            try {
                // What is the purpose of mapsInitializer
                /*


                */
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }


            /*
passing instance of an interface to the mapView!
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

            viewOfFragment1 = inflater.inflate(R.layout.sample_layout_for_nomap, container, false);

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

        // I should always get lat and long in here in this method
        getFusedLocationProviderClientAndAddedRequiredPermissionsToGetLatAndLongitude();

    }

    public void goToParticularLocation(double lat, double lng, float zoom){
        LatLng billerica = new LatLng(lat, lng);




        // CameraUpdate is used to zoom in to the added location
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(billerica, zoom);
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
             addresses = geocoder.getFromLocation(lat, lng, 1);
        }
        catch (Exception e){

        }


        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getAddressLine(1);
        String countryName = addresses.get(0).getAddressLine(2);
        // adding details to the marker

        mGoogleMap.addMarker(new MarkerOptions().position(billerica).title(cityName+", "+stateName));
        mGoogleMap.animateCamera(cameraUpdate);

        // enables traffic around you
        // this gives me live information on trafficø
        mGoogleMap.setTrafficEnabled(true);

    }



}


