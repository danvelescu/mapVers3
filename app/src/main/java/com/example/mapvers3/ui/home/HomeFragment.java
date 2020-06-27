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
import android.widget.LinearLayout;
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

import com.example.mapvers3.AppDatabase;
import com.example.mapvers3.ContenDao;
import com.example.mapvers3.ContentPage;
import com.example.mapvers3.DataBaseClient;
import com.example.mapvers3.ImagePage;
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
    private RecyclerView recyclerView,recyclerView1;
    private List<ContentPage>contentPagess;
    private HomeViewModel homeViewModel;
    private List<ImagePage>imagePages = new ArrayList<>();


    @SuppressLint("WrongConstant")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        fragmentHomeManager = getFragmentManager();
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);



        Button startDBbuton = root.findViewById(R.id.startDB);
        recyclerView = root.findViewById(R.id.recycler);
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
        System.out.println("test111.1");
        class GetTasks extends AsyncTask<Void,Void, List<ContentPage>> {

            @Override
            protected List<ContentPage> doInBackground(Void... voids) {
                List<ContentPage> listofcontent = DataBaseClient
                        .getInstance(root.getContext())
                        .getAppDatabase()
                        .contentDao()
                        .getAll();
                System.out.println("test111.1"+listofcontent.get(0));
                return listofcontent;
            }

            @Override
            protected void onPostExecute(List<ContentPage> contentPages) {
                System.out.println("test111.2");
                super.onPostExecute(contentPages);
                contentPagess=contentPages;
               getImageTask();
            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }
    private void setDBdata(){

        class SaveContent extends AsyncTask<Void, Void ,Void> {
            @SuppressLint("ResourceType")

            byte[] imag1 = imageViewToByte("aula_0");






            List<ContentPage> listOfContents = new ArrayList<>();
            List<ImagePage> listOfimagies = new ArrayList<>();
            //int id, String nameInfo, String info, byte[] image, Double lat, Double longitudine
            @Override
            protected Void doInBackground(Void... voids) {

//               listOfimagies.add(new ImagePage(0,0,imag1));
//                listOfimagies.add(new ImagePage(1,0,imag1));
//                listOfimagies.add(new ImagePage(2,0,imag1));
//                listOfimagies.add(new ImagePage(3,1,imag1));
//                listOfimagies.add(new ImagePage(4,1,imag1));
//                listOfimagies.add(new ImagePage(5,1,imag1));
//                listOfimagies.add(new ImagePage(6,2,imag1));
//                listOfimagies.add(new ImagePage(7,2,imag1));
//                listOfimagies.add(new ImagePage(8,3,imag1));
//                listOfimagies.add(new ImagePage(9,4,imag1));
                listOfimagies.add(new ImagePage(15,1,imag1));
                listOfimagies.add(new ImagePage(16,1,imag1));
                listOfimagies.add(new ImagePage(17,1,imag1));
                listOfimagies.add(new ImagePage(18,1,imag1));
                listOfimagies.add(new ImagePage(19,1,imag1));


//              listOfContents.add(new ContentPage(0,"name0","info0",imag1,45.7526686,24.1127237));
//               listOfContents.add(new ContentPage(1,"name1","info1",imag1,46.7526686,26.1127237));
//              listOfContents.add(new ContentPage(2,"name2","info2",imag1,47.7526686,27.1127237));
//               listOfContents.add(new ContentPage(3,"name3","info3",imag1,48.7526686,28.1127237));
//                listOfContents.add(new ContentPage(4,getString(R.string.nameinfo1),getString(R.string.contentinfo1),imag1,48.7526686,28.1127237));




                DataBaseClient.getInstance(root.getContext()).getAppDatabase()
                        .contentDao().insertAll(listOfContents);
                DataBaseClient.getInstance(root.getContext()).getAppDatabase()
                        .contentDao().insertAllimagies(listOfimagies);



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


    private  void getImageTask(){
        class GetTasksImage extends AsyncTask<Void,Void, List<ImagePage>> {

            @Override
            protected List<ImagePage> doInBackground(Void... voids) {
                List<ImagePage> listofcontent = DataBaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .contentDao()
                        .getAllimagies();
                System.out.println("test111.4"+listofcontent.get(0).getImage());
                return listofcontent;
            }

            @Override
            protected void onPostExecute(List<ImagePage>listofcontent) {
                super.onPostExecute(listofcontent);
                System.out.println("test111.5");
                System.out.println("Post executed image");
                imagePages=listofcontent;
                ContentAdapter adapter = new ContentAdapter(root.getContext(),contentPagess,fragmentHomeManager,imagePages);
                recyclerView.setAdapter(adapter);
            }
        }
        GetTasksImage gt = new GetTasksImage();
        gt.execute();

    }




}