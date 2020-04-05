package com.spreadtracker.ui.fragment.home;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.spreadtracker.App;
import com.spreadtracker.R;
import com.spreadtracker.ui.activity.main.MainActivity;
import com.spreadtracker.ui.adapter.OverlayPagerAdapter;
import com.spreadtracker.ui.fragment.ViewModelFragment;
import com.spreadtracker.ui.pager.OverlayViewPager;

/**
 * Represents the home fragment--the "home screen" page of the app.
 * Contains the heatmap and the draggable percentage overlay.
 */
public class HomeFragment extends ViewModelFragment<MainActivity, HomeFragmentViewModel>
    implements OnMapReadyCallback {

    /**
     * The {@link GoogleMap} object presenting this fragment's map
     */
    private GoogleMap mMap;
    /**
     * The actual View that represents the {@link #mMap} object.
     */
    private MapView mMapView;

    @Override
    protected void inOnCreateView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initializeMap(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeViewPager();
    }

    /**
     * Initializes the Google Maps API and the map view in this fragment,
     * and schedules an asynchronous callback, {@link #onMapReady(GoogleMap)}, to occur when the map has finished loading.
     * @param savedInstanceState If non-null, represents the saved instance state when this fragment is reconstructed.
     */
    private void initializeMap (@Nullable Bundle savedInstanceState) {
        mMapView = root.findViewById(R.id.fragment_home_mapView);

        // Initialize maps API
        try {
            MapsInitializer.initialize(App.instance.getApplicationContext());
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

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /**
     * Creates the pager adapter needed to create the draggable
     */
    private void initializeViewPager () {
        final int colorDark = ContextCompat.getColor(activity, R.color.grayDark);
        final int colorLight = ContextCompat.getColor(activity, R.color.white);

        OverlayViewPager viewPager = root.findViewById(R.id.fragment_home_viewPager);
        viewPager.setAdapter(new OverlayPagerAdapter(getChildFragmentManager()));
        viewPager.setCurrentItem(1); // Set's to second page so that the overlay is hidden by default
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    // As we drag the overlay down, the icons in the upper left and right hand corners of the screen
                    // should become white
                    HomeFragment.this.activity.interpolateButtonColor(colorLight, colorDark, positionOffset);
                }
            }
            @Override public void onPageSelected(int position) { }
            @Override public void onPageScrollStateChanged(int state) {}
        });
    }

    /*
     * For use with ViewModelFragment
     */
    @NonNull
    @Override
    protected Class<HomeFragmentViewModel> getViewModelClass() { return HomeFragmentViewModel.class; }

    @Override
    protected int getLayout() { return R.layout.fragment_home; }

    /*
     * Overridden fragment lifecycle methods
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
