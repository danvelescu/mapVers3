package com.example.mapvers3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public class CostumeInfoWindowMap implements GoogleMap.InfoWindowAdapter {
    private ViewGroup container;
    private Context context;
    List<ContentPage> lists;
    LatLng markerPosition;
    public CostumeInfoWindowMap(Context ctx , ViewGroup container , List<ContentPage> lists){
       this.context = ctx;
        this.container=container;
        this.lists = lists;
    }

    @Override
    public View getInfoWindow(Marker marker){
       markerPosition =  marker.getPosition();
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((FragmentActivity)context).getLayoutInflater()
                .inflate(R.layout.mapmarker,null);
        ImageView image = view.findViewById(R.id.imageIndow);
        TextView text = view.findViewById(R.id.InfoStringWindow);

        for (ContentPage i:lists) {
            if(i.getLat()==markerPosition.latitude&&i.getLongitudine()==markerPosition.longitude){
                byte[] imagee = i.getImage();
                Bitmap bitmab = BitmapFactory.decodeByteArray(imagee, 0, imagee.length);
                image.setImageBitmap(bitmab);
                text.setText(i.getNameInfo());
            }
        }


        return view;
    }


}
