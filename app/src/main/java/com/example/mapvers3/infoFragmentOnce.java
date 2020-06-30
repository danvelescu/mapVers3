package com.example.mapvers3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class infoFragmentOnce extends Fragment {


    private View rootView;
  private  ImageView image;
 private   TextView textname;
 private   TextView textinfo;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmentfinal, container, false);
        textinfo =  rootView.findViewById(R.id.textfinal999);
        textname = rootView.findViewById(R.id.txtfinal1999);


        image = (ImageView) rootView.findViewById(R.id.imageforonce1);



        ContentPage contentid;
        Bundle bundle = this.getArguments();

            contentid = (ContentPage) bundle.getParcelable("content");
            System.out.println(contentid.getLat()+" successs");
            System.out.println(contentid.getInfo()+" successs");
           textname.setText(contentid.getNameInfo());
            textinfo.setText(contentid.getInfo());
            byte[] imagee = contentid.getImage();
            Bitmap bitmab = BitmapFactory.decodeByteArray(imagee, 0, imagee.length);
           image.setImageBitmap(bitmab);

        return rootView;
    }
}