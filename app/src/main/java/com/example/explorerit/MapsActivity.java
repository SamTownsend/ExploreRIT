package com.example.explorerit;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /**
     * The GoogleMap object
     */
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    /**
     * The target location for the user to get to with a marker in the same spot
     */

    private Location myLocation;
    private LatLng target;
    private Marker trgtMarker;
    private int score;

    /**
     * Keeps track of whether or not a new target should be generated
     */
    private boolean buttonClickable = true;
    private TextView display;

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
        display = (TextView) findViewById(R.id.scoreDisplay);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.setMinZoomPreference(15);
        mMap.setMaxZoomPreference(19);
        mMap.setLatLngBoundsForCameraTarget(BOUNDARY);
        target = CENTER;
        trgtMarker = mMap.addMarker(new MarkerOptions().position(target).title("Next Destination"));
        trgtMarker.setDraggable(false);
        trgtMarker.setVisible(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(target));
    }

    public void atLocation(){
        if (Math.abs(myLocation.getLatitude() - target.latitude) < .0004 &&
            Math.abs(myLocation.getLongitude() - target.longitude) < .0004){ //within ~150 feet of target
            score += 10;
            display.setText("Score: " + score);
            target = getRandomLocation();
            trgtMarker.setPosition(target);
        }
        else {
            System.out.println("You must get to the target first!");
        }
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
    public void onButtonClick(View view){
        if(buttonClickable) {
            target = getRandomLocation();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(target));
            trgtMarker.setPosition(target);
            trgtMarker.setVisible(true);
            display.setText("Score: " + score);
            score += 10;
            //buttonClickable = false;
        }
        else
            atLocation();
    }
}
