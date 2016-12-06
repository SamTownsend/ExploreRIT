package com.example.explorerit;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LatLng home;

    /*Values for random coordinates on the RIT campus to be chosen between*/
    private final double MIN_LAT = 43.082979;
    private final double MAX_LAT = 43.086787;
    private final double MIN_LONG = -77.681430;
    private final double MAX_LONG = -77.667284;

    private final LatLng center = new LatLng((MAX_LAT + MIN_LAT) / 2, (MAX_LONG + MIN_LONG) / 2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(15);
        //home = new LatLng(43.084644, -77.679988);
        home = center;
        mMap.addMarker(new MarkerOptions().position(home).title("Next Destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
    }

    /**
     * Creates and returns a random location within a predetermined rectangle on campus
     * @return A randomized LatLng coordinate
     */
    public LatLng getRandomLocation(){
        return new LatLng(Math.random() * (MAX_LAT - MIN_LAT) + MIN_LAT,
                Math.random() * (MAX_LONG - MIN_LONG) + MIN_LONG);
    }

    public void onSucc(View view){
        //home = getRandomLocation();
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(home , 14.0f) );
        System.out.println("You will never get the succ");
    }
}
