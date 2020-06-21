package com.example.mapvers3.ui.home;

import android.annotation.SuppressLint;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapvers3.ContentPage;
import com.example.mapvers3.DataBaseClient;
import com.example.mapvers3.MapActivity;
import com.example.mapvers3.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class HomeFragment extends Fragment {

   private View root;

    private RecyclerView recyclerView;

    private HomeViewModel homeViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);

       final TextView textView = root.findViewById(R.id.text_home);
        Button button = root.findViewById(R.id.updatedatabase);
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
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.println(Log.ASSERT,"alert","Jobe DOne");
                getTasks();
            }
        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });




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
                Log.println(Log.ASSERT,"alert","test1");
                ContentAdapter adapter = new ContentAdapter(root.getContext(),contentPages);
                Log.println(Log.ASSERT,"alert99",contentPages.get(0).getInfo());
                recyclerView.setAdapter(adapter);

            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }
    private void setDBdata(){
        class SaveContent extends AsyncTask<Void, Void ,Void> {
            @SuppressLint("ResourceType")
            ImageView image = root.findViewById(R.drawable.ic_pin);
            byte[] imag1 = imageViewToByte("aula_0");
            ContentPage content = new ContentPage(1, "nameinfotest1", "contentinfo1", imag1, 12.1, 12.2);
            ContentPage content1 = new ContentPage(2, "nameinfotest1", "contentinfo2", imag1, 12.1, 12.2);
            ContentPage content2 = new ContentPage(3, "nameinfotest1", "contentinfo3", imag1, 12.1, 12.2);
            ContentPage content3 = new ContentPage(4, "nameinfotest1", "contentinfo4", imag1, 12.1, 12.2);

            @Override
            protected Void doInBackground(Void... voids) {
                DataBaseClient.getInstance(root.getContext().getApplicationContext()).getAppDatabase().contentDao().insert(content1);
                DataBaseClient.getInstance(root.getContext().getApplicationContext()).getAppDatabase().contentDao().insert(content2);
                DataBaseClient.getInstance(root.getContext().getApplicationContext()).getAppDatabase().contentDao().update(content3);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.println(Log.ASSERT,"alert","job done 3");
            }
        }
       SaveContent sc = new SaveContent();
        sc.execute();
    }
    public byte[] imageViewToByte(String name){
//

        Drawable drawable = getResources().getDrawable(getResources()
                .getIdentifier(name, "drawable",root.getContext().getPackageName()));
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return  byteArray;
    }

}