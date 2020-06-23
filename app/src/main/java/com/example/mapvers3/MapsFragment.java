package com.example.mapvers3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mapvers3.ui.home.ContentAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsFragment extends Fragment {
private boolean chekPosition = false;
    Integer id;
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
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            getTasks();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.nav_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        Bundle bundle = this.getArguments();
        try {
             id =  bundle.getInt("id");
            chekPosition = true;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void getTasks()
    {
        class GetTasks extends AsyncTask<Void,Void, List<ContentPage>> {

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
                System.out.println(contentPages.get(0).getLat()+"  "+contentPages.get(0).getLongitudine());

                LatLng sydney = new LatLng(contentPages.get(0).getLat(), contentPages.get(0).getLongitudine());
                googlemap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                googlemap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }


    private ContentPage FindById(List<ContentPage> pages){
        for (ContentPage a:pages) {
            if(a.getId() ==  id){
                return a;
            }
        }
        return new ContentPage();
    }
}