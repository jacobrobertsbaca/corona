package com.spreadtracker.ui.activity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;
import com.spreadtracker.App;
import com.spreadtracker.R;
import com.spreadtracker.contactstracing.Calculator;
import com.spreadtracker.contactstracing.Database;
import com.spreadtracker.ui.activity.NavigationActivity;
import com.spreadtracker.ui.fragment.tutorial.TutorialFragment;
import com.spreadtracker.ui.util.ToastError;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends NavigationActivity implements OnMapReadyCallback {

    private CoordinatorLayout mLayout;

    /**
     * The {@link GoogleMap} object presenting this fragment's map
     */
    private GoogleMap mMap;
    /**
     * The actual View that represents the {@link #mMap} object.
     */
    private MapView mMapView;

    public CoordinatorLayout getLayout() { return mLayout; }

    private FusedLocationProviderClient mLocationClient;

    private static Database database;

    private static Calculator calculator;

    public static Database getDatabase() {
        return database;
    }

    public static Calculator getCalculator() {
        return calculator;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMap(savedInstanceState);
        mLayout = findViewById(R.id.activity_main_layout);

        createDatabase();

        showTutorial();
    }

    @Override
    @NonNull
    protected NavHostFragment getNavHost() {
        return (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_navHostFragment);
    }

    /**
     * Shows the tutorial page, if it should be shown right now
     */
    private void showTutorial () {
        FragmentManager manager = getSupportFragmentManager();
        TutorialFragment fragment = (TutorialFragment) manager.findFragmentById(R.id.activity_main_tutorialFragment);
        View fragmentView = fragment.getView();

        boolean showTutorial = true;

        if (!showTutorial) {
            fragmentView.setVisibility(View.GONE);
            manager
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    /**
     * Initializes the Google Maps API and the map view in this fragment,
     * and schedules an asynchronous callback, {@link #onMapReady(GoogleMap)}, to occur when the map has finished loading.
     * @param savedInstanceState If non-null, represents the saved instance state when this fragment is reconstructed.
     */
    private void initializeMap (@Nullable Bundle savedInstanceState) {
        mMapView = findViewById(R.id.activity_main_mapView);

        // Initialize maps API
        try {
            MapsInitializer.initialize(App.getInstance().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
    }

    /**
     * Called when the Google Map view, {@link #mMapView}, of this fragment has finished loading
     * and is actually displaying the Google Map.
     * @param googleMap A {@link GoogleMap} object that represents the map that just finished loading.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Disable the re-align north compass as it obstructs the info button
        mMap.getUiSettings().setCompassEnabled(false);

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        zoomToCurrentLocation();
        buildHeatmap();
    }

    private void buildHeatmap () {
        // Temporary heat map visualization
        getDataSet(data -> {
            int[] colors = {
                    Color.argb(0, 0, 255, 0),
                    Color.argb(255, 255, 0, 0)
            };

            float[] startPoints = {0.0f, 1.0f};

            HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                    .weightedData(data)
                    .gradient(new Gradient(colors, startPoints))
                    .radius(30)
                    .build();
            TileOverlay overlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
        });
    }

    private void zoomToCurrentLocation () {
        if (mLocationClient == null) mLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
            }
        });
    }

    private void getDataSet (OnSuccessListener<List<WeightedLatLng>> onSuccess) {
        // This should get data from the model and process it to a format that makes it look when loaded onto the map
        // For now I'll just generate some random data within a "circle" around the user's location
        mLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                final double locationRadius = 0.1; // This is temporary...
                final int iterations = 1000;

                double minLat = location.getLatitude() - locationRadius;
                double maxLat = location.getLatitude() + locationRadius;
                double minLon = location.getLongitude() - locationRadius;
                double maxLon = location.getLongitude() + locationRadius;

                List<WeightedLatLng> points = new ArrayList<>();
                while (points.size() < iterations) {
                    double lat = minLat + Math.random() * (maxLat - minLat);
                    double lon = minLon + Math.random() * (maxLon - minLon);
                    double distance = Math.sqrt(Math.pow(lat - location.getLatitude(), 2)
                            + Math.pow(lon-location.getLongitude(), 2));
                    if (distance > locationRadius) continue;
                    double weight = Math.pow(Math.random(), 15) / 5;
                    LatLng latLng = new LatLng(lat, lon);
                    points.add(new WeightedLatLng(latLng, weight));
                }

                if (onSuccess != null) onSuccess.onSuccess(points);
            } else ToastError.error(this, "Could not get current location!", Toast.LENGTH_LONG);
        });
    }

    private void createDatabase() {
        File databaseFile = new File(App.getInstance().getApplicationContext().getFilesDir(), "tracker.sqlite");
        database = new Database(databaseFile);
        calculator = new Calculator(database);
//        List<String> names = database.getPersonNames();
//        List<Long> dates = database.getEventDates();
//
//        long now = 51L * 3600 * 1000 * 24 * 365;
//        boolean eventBeforeToday = (dates.get(0) < now);
    }

    /*
     * Overridden activity lifecycle methods
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null) mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null) mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null) mMapView.onLowMemory();
    }
}
