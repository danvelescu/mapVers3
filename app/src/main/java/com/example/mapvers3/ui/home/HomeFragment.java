package com.example.mapvers3.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapvers3.ContentPage;
import com.example.mapvers3.DataBaseClient;
import com.example.mapvers3.MapActivity;
import com.example.mapvers3.MapsFragment;
import com.example.mapvers3.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


   private View root;
   private FragmentManager fragmentHomeManager;
    private RecyclerView recyclerView;

    private HomeViewModel homeViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        fragmentHomeManager = getFragmentManager();
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);



        Button startDBbuton = root.findViewById(R.id.startDB);
        recyclerView = root.findViewById(R.id.recycler);
        Log.println(Log.ASSERT,"alert","1");
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        Log.println(Log.ASSERT,"alert","2");
        startDBbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDBdata();
            }
        });
        getTasks();

        return root;
    }

    private void getTasks()
    {
        class GetTasks extends AsyncTask<Void,Void, List<ContentPage>> {

            @Override
            protected List<ContentPage> doInBackground(Void... voids) {
                List<ContentPage> listofcontent = DataBaseClient
                        .getInstance(root.getContext())
                        .getAppDatabase()
                        .contentDao()
                        .getAll();
                Log.println(Log.ASSERT,"alert1",listofcontent.get(2).getImage().toString());
                return listofcontent;
            }

            @Override
            protected void onPostExecute(List<ContentPage> contentPages) {
                super.onPostExecute(contentPages);
                ContentAdapter adapter = new ContentAdapter(root.getContext(),contentPages,fragmentHomeManager);
                recyclerView.setAdapter(adapter);
            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }
    private void setDBdata(){

        class SaveContent extends AsyncTask<Void, Void ,Void> {
            @SuppressLint("ResourceType")

            byte[] imag1 = imageViewToByte("aula_0");





            @Override
            protected Void doInBackground(Void... voids) {


               return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.println(Log.ASSERT,"alert","SQL Inserted");
            }
        }
       SaveContent sc = new SaveContent();
        sc.execute();
    }
    public byte[] imageViewToByte(String name){
        Drawable drawable = getResources().getDrawable(getResources()
                .getIdentifier(name, "drawable",root.getContext().getPackageName()));
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return  byteArray;
    }







}