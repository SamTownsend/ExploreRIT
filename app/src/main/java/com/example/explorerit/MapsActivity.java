package com.example.explorerit;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /**
     * The GoogleMap object
     */
    private GoogleMap mMap;

    /**
     * The target location for the user to get to
     */
    private LatLng target;

    /**
     * Values for random coordinates on the RIT campus to be chosen between
     **/
    private static final double MIN_LAT = 43.082979;
    private static final double MAX_LAT = 43.086787;
    private static final double MIN_LONG = -77.681430;
    private static final double MAX_LONG = -77.667284;
    /**
     * The LatLng value designated as the "center" of the map
     */
    private static final LatLng CENTER = new LatLng(((MAX_LAT + MIN_LAT) / 2) - .001000
            , ((MAX_LONG + MIN_LONG) / 2) - .001000);
    /**
     * The boundary in which the camera will stay in
     */
    private static final LatLngBounds BOUNDARY = new LatLngBounds(new LatLng(MIN_LAT, MIN_LONG),
            new LatLng(MAX_LAT, MAX_LONG));

    /**
     * Didn't really mess with this much other then set to landscape
     * @param savedInstanceState It's required
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Seems to work as an init method
     * As a result, does all initialization
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(15);
        mMap.setMaxZoomPreference(19);
        mMap.setLatLngBoundsForCameraTarget(BOUNDARY);

        target = CENTER;
        mMap.addMarker(new MarkerOptions().position(target).title("Next Destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(target));
    }

    /**
     * Creates and returns a random location within a predetermined rectangle on campus
     * @return A randomized LatLng coordinate
     */
    public LatLng getRandomLocation(){
        return new LatLng(Math.random() * (MAX_LAT - MIN_LAT) + MIN_LAT,
                Math.random() * (MAX_LONG - MIN_LONG) + MIN_LONG);
    }

    /**
     * An event handler for the button
     * @param view It's required
     */
    public void onSucc(View view){
        //home = getRandomLocation();
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(target , 15.0f) );
        System.out.println("You will never get the succ");
    }
}
