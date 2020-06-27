package com.example.mapvers3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mapvers3.ui.home.ContentAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    LatLng mDefaultLocation = new LatLng(45, 24);
    private int DEFAULT_ZOOM = 1;
    private int MAX_ZOOM = 5;
    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;
    private boolean chekPosition = false;
    GeoDataClient mGeoDataClient;
    PlaceDetectionClient mPlaceDetectionClient;
    FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;
    Integer id=-1;
    GoogleMap googlemap;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googlemap = googleMap;

            getTasks();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(super.getContext(), null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(super.getContext(), null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(super.getContext());

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getTasks();
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.nav_map);
        mapFragment.getMapAsync(this);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        Bundle bundle = this.getArguments();
        try {
            id = bundle.getInt("id");
            chekPosition = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        getLocationPermission();
    }


    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<ContentPage>> {

            @Override
            protected List<ContentPage> doInBackground(Void... voids) {
                List<ContentPage> listofcontent = DataBaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .contentDao()
                        .getAll();
                return listofcontent;
            }

            @Override
            protected void onPostExecute(List<ContentPage> contentPages) {
                super.onPostExecute(contentPages);
                ContentPage targetPlace=new ContentPage();
                LatLng target;
                MarkerOptions markerO;
                try {
                    for (ContentPage place : contentPages
                    ) {
                        if (place.getId() != targetPlace.getId()) {
                            target = new LatLng(place.getLat(), place.getLongitudine());
                            markerO = new MarkerOptions().position(target).title(place.getNameInfo());
                            googlemap.addMarker(markerO);
                        }

                    }
                    googlemap.setMaxZoomPreference(MAX_ZOOM);
                    targetPlace = FindById(contentPages);
                    target = new LatLng(targetPlace.getLat(), targetPlace.getLongitudine());
                    markerO = new MarkerOptions().position(target).title(targetPlace.getNameInfo());
                    markerO.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    googlemap.addMarker(markerO);
                    googlemap.moveCamera(CameraUpdateFactory.newLatLng(target));



                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }


    private ContentPage FindById(List<ContentPage> pages) {
        for (ContentPage a : pages) {
            if (a.getId() == id) {
                return a;
            }
        }
        return new ContentPage();
    }


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }

        updateLocationUI();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMinZoomPreference(11);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
            return;
        }
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        googleMap.setMyLocationEnabled(true);
        updateLocationUI();
        getDeviceLocation();

    }

    private void updateLocationUI() {

        if (googlemap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                googlemap.setMyLocationEnabled(true);
               googlemap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                googlemap.setMyLocationEnabled(false);
               googlemap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(super.getActivity(), new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation =  (Location) task.getResult();
                            googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {

                           googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            googlemap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


}

