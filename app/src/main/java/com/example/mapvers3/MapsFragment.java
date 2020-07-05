package com.example.mapvers3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
        private MarkerOptions markerO;
    LatLng mDefaultLocation = new LatLng(45, 24);
    private int DEFAULT_ZOOM = 1;
    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;
    private boolean chekPosition = false;
    GeoDataClient mGeoDataClient;
    PlaceDetectionClient mPlaceDetectionClient;
    FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;
    private CostumeInfoWindowMap customInfoWindow;
    Integer id=-1;
    GoogleMap googlemap;
    private List<ContentPage> contentPages1;
    private ViewGroup container1;
    private RecyclerView recyclerView;
    private List<ImagePage> listForImage;
    private List<ContentPage>  arrayofcontent = new ArrayList<>();

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
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
            container1 = container;

        mGeoDataClient = Places.getGeoDataClient(super.getContext(), null);


        mPlaceDetectionClient = Places.getPlaceDetectionClient(super.getContext(), null);


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
                getImages();
                return listofcontent;
            }

            @Override
            protected void onPostExecute(List<ContentPage> contentPages) {
                arrayofcontent = contentPages;
                customInfoWindow = new CostumeInfoWindowMap(getContext(),container1,contentPages);
                super.onPostExecute(contentPages);
                googlemap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {

                        ContentPage targetCOntent = new ContentPage();
                        LatLng latLon = marker.getPosition();
                        for (ContentPage i: arrayofcontent) {
                            if(i.getLongitudine()==latLon.longitude&&i.getLat()==latLon.latitude){
                                targetCOntent = i;
                            }
                        }

                        infoFragmentOnce infoFragmentOnce = new infoFragmentOnce();
                        FragmentManager fragmentManager = getFragmentManager();


                        Bundle bundle = new Bundle();
                        bundle.putParcelable("content",targetCOntent);
                        infoFragmentOnce.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,infoFragmentOnce).commit();


//

                    }
                });
                ContentPage targetPlace=new ContentPage();
                LatLng target;

                try {
                    for (ContentPage place : contentPages
                    ) {
                        if (place.getId() != targetPlace.getId()) {
                            target = new LatLng(place.getLat(), place.getLongitudine());
                            markerO = new MarkerOptions().position(target).title(place.getNameInfo());
                            googlemap.addMarker(markerO);
                        }

                    }
                    googlemap.setInfoWindowAdapter(customInfoWindow);
                    targetPlace = FindById(contentPages);
                    target = new LatLng(targetPlace.getLat(), targetPlace.getLongitudine());
                    markerO = new MarkerOptions().position(target).title(targetPlace.getNameInfo());
                    markerO.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                    googlemap.addMarker(markerO);
                    googlemap.moveCamera(CameraUpdateFactory.newLatLng(target));
                    googlemap.setMinZoomPreference(15);




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
    private void getImages(){
        class GetTasksImage extends AsyncTask<Void,Void, List<ImagePage>> {

            @Override
            protected List<ImagePage> doInBackground(Void... voids) {
                List<ImagePage> listofcontent = DataBaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .contentDao()
                        .getAllimagies();

                return listofcontent;
            }

            @Override
            protected void onPostExecute(List<ImagePage>listofcontent) {
                super.onPostExecute(listofcontent);
                listForImage = listofcontent;
            }
        }
        GetTasksImage gt = new GetTasksImage();
        gt.execute();
    }


    private void getLocationPermission() {

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




        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(super.getActivity(), new OnCompleteListener() {




                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
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

    private void chekNear(){
        //arrayofcontent
        float[] results = new float[1];
        for (ContentPage i:arrayofcontent) {


            Location.distanceBetween(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), i.getLat(), i.getLongitudine(), results);

            if (results[0] < 100) {
                Toast.makeText(getContext(), "Esti Aproape de "+i.getNameInfo(), Toast.LENGTH_LONG).show();
            }
        }

    }

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 10000;

    @Override
    public void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(runnable, delay);
                chekNear();
            }
        },delay);
        super.onResume();
    }
}

