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
    private RecyclerView recyclerView;
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


            List<ContentPage> listOfContents = new ArrayList<>();
            List<ImagePage> listOfimagies = new ArrayList<>();
            //int id, String nameInfo, String info, byte[] image, Double lat, Double longitudine
            @Override
            protected Void doInBackground(Void... voids) {
                listOfimagies.add(new ImagePage(0,0,imageViewToByte(getString(R.string.image1din1))));
                listOfimagies.add(new ImagePage(1,0,imageViewToByte(getString(R.string.image1din2))));
                listOfimagies.add(new ImagePage(2,0,imageViewToByte(getString(R.string.image1din3))));
                listOfimagies.add(new ImagePage(3,1,imageViewToByte(getString(R.string.image2din1))));
                listOfimagies.add(new ImagePage(4,1,imageViewToByte(getString(R.string.image2din2))));
                listOfimagies.add(new ImagePage(5,1,imageViewToByte(getString(R.string.image2din3))));
                listOfimagies.add(new ImagePage(6,2,imageViewToByte(getString(R.string.image3din1))));
                listOfimagies.add(new ImagePage(7,2,imageViewToByte(getString(R.string.image3din2))));
                listOfimagies.add(new ImagePage(8,2,imageViewToByte(getString(R.string.image3din3))));
                listOfimagies.add(new ImagePage(9,3,imageViewToByte(getString(R.string.image4din1))));
                listOfimagies.add(new ImagePage(10,3,imageViewToByte(getString(R.string.image4din2))));
                listOfimagies.add(new ImagePage(11,3,imageViewToByte(getString(R.string.image4din3))));
                listOfimagies.add(new ImagePage(12,3,imageViewToByte(getString(R.string.image4din4))));
                listOfimagies.add(new ImagePage(13,3,imageViewToByte(getString(R.string.image4din5))));
                listOfimagies.add(new ImagePage(14,99,imageViewToByte(getString(R.string.image4din5))));
                listOfimagies.add(new ImagePage(15,4,imageViewToByte(getString(R.string.image5din1))));
                listOfimagies.add(new ImagePage(16,4,imageViewToByte(getString(R.string.image5din2))));
                listOfimagies.add(new ImagePage(17,4,imageViewToByte(getString(R.string.image5din3))));
                listOfimagies.add(new ImagePage(18,4,imageViewToByte(getString(R.string.image5din4))));
                listOfimagies.add(new ImagePage(19,99,imageViewToByte(getString(R.string.image5din4))));
                listOfimagies.add(new ImagePage(20,99,imageViewToByte(getString(R.string.image5din4))));
                listOfimagies.add(new ImagePage(21,5,imageViewToByte(getString(R.string.image6din1))));
                listOfimagies.add(new ImagePage(22,5,imageViewToByte(getString(R.string.image6din2))));
                listOfimagies.add(new ImagePage(23,5,imageViewToByte(getString(R.string.image6din3))));
                listOfimagies.add(new ImagePage(24,5,imageViewToByte(getString(R.string.image6din4))));
                listOfimagies.add(new ImagePage(25,5,imageViewToByte(getString(R.string.image6din5))));
                listOfimagies.add(new ImagePage(26,5,imageViewToByte(getString(R.string.image6din6))));
                listOfimagies.add(new ImagePage(27,6,imageViewToByte(getString(R.string.image7din1))));
                listOfimagies.add(new ImagePage(28,6,imageViewToByte(getString(R.string.image7din2))));
                listOfimagies.add(new ImagePage(29,6,imageViewToByte(getString(R.string.image7din3))));
                listOfimagies.add(new ImagePage(30,7,imageViewToByte(getString(R.string.image8din1))));
                listOfimagies.add(new ImagePage(31,7,imageViewToByte(getString(R.string.image8din2))));
                listOfimagies.add(new ImagePage(32,7,imageViewToByte(getString(R.string.image8din3))));
                listOfimagies.add(new ImagePage(33,7,imageViewToByte(getString(R.string.image8din4))));
                listOfimagies.add(new ImagePage(34,99,imageViewToByte(getString(R.string.image8din1))));
                listOfimagies.add(new ImagePage(35,99,imageViewToByte(getString(R.string.image8din1))));
                listOfimagies.add(new ImagePage(36,8,imageViewToByte(getString(R.string.image9din1))));
                listOfimagies.add(new ImagePage(37,8,imageViewToByte(getString(R.string.image9din2))));
                listOfimagies.add(new ImagePage(38,8,imageViewToByte(getString(R.string.image9din3))));
                listOfimagies.add(new ImagePage(39,8,imageViewToByte(getString(R.string.image9din4))));
                listOfimagies.add(new ImagePage(40,8,imageViewToByte(getString(R.string.image9din5))));
                listOfimagies.add(new ImagePage(41,9,imageViewToByte(getString(R.string.image10din1))));
                listOfimagies.add(new ImagePage(42,9,imageViewToByte(getString(R.string.image10din2))));
                listOfimagies.add(new ImagePage(43,9,imageViewToByte(getString(R.string.image10din3))));
                listOfimagies.add(new ImagePage(44,9,imageViewToByte(getString(R.string.image10din4))));
                listOfimagies.add(new ImagePage(45,10,imageViewToByte(getString(R.string.image11din1))));
                listOfimagies.add(new ImagePage(46,10,imageViewToByte(getString(R.string.image11din2))));
                listOfimagies.add(new ImagePage(47,10,imageViewToByte(getString(R.string.image11din3))));
                listOfimagies.add(new ImagePage(48,10,imageViewToByte(getString(R.string.image11din4))));
                listOfimagies.add(new ImagePage(49,11,imageViewToByte(getString(R.string.image12din1))));
                listOfimagies.add(new ImagePage(50,12,imageViewToByte(getString(R.string.image13din1))));
                listOfimagies.add(new ImagePage(51,13,imageViewToByte(getString(R.string.image14din1))));
                listOfimagies.add(new ImagePage(52,14,imageViewToByte(getString(R.string.image15din1))));
                listOfimagies.add(new ImagePage(53,15,imageViewToByte(getString(R.string.image16din1))));
                listOfimagies.add(new ImagePage(54,16,imageViewToByte(getString(R.string.image17din1))));
                listOfimagies.add(new ImagePage(55,17,imageViewToByte(getString(R.string.image18din1))));
                listOfimagies.add(new ImagePage(56,18,imageViewToByte(getString(R.string.image19din1))));
                listOfimagies.add(new ImagePage(57,19,imageViewToByte(getString(R.string.image20din1))));

               // int id, String nameInfo, String info, byte[] image, Double lat, Double longitudine,String link
                listOfContents.add(new ContentPage(0,getString(R.string.nameinfo1),
                                getString(R.string.contentinfo1),
                                imageViewToByte(getString(R.string.image1din1)),
                                Double.parseDouble(getString(R.string.lat1)),
                                        Double.parseDouble(getString(R.string.long1)),
                                        getString(R.string.link1)));
                listOfContents.add(new ContentPage(1,getString(R.string.nameinfo2),
                        getString(R.string.contentinfo2),
                        imageViewToByte(getString(R.string.image2din1)),
                        Double.parseDouble(getString(R.string.lat2)),
                        Double.parseDouble(getString(R.string.long2)),
                        getString(R.string.link2)));
                listOfContents.add(new ContentPage(2,getString(R.string.nameinfo3),
                        getString(R.string.contentinfo3),
                        imageViewToByte(getString(R.string.image3din1)),
                        Double.parseDouble(getString(R.string.lat3)),
                        Double.parseDouble(getString(R.string.long3)),
                        getString(R.string.link3)));
                listOfContents.add(new ContentPage(3,getString(R.string.nameinfo4),
                        getString(R.string.contentinfo4),
                        imageViewToByte(getString(R.string.image4din1)),
                        Double.parseDouble(getString(R.string.lat4)),
                        Double.parseDouble(getString(R.string.long4)),
                        getString(R.string.link4)));
                listOfContents.add(new ContentPage(4,getString(R.string.nameinfo5),
                        getString(R.string.contentinfo5),
                        imageViewToByte(getString(R.string.image5din1)),
                        Double.parseDouble(getString(R.string.lat5)),
                        Double.parseDouble(getString(R.string.long5)),
                        getString(R.string.link5)));
                listOfContents.add(new ContentPage(5,getString(R.string.nameinfo6),
                        getString(R.string.contentinfo6),
                        imageViewToByte(getString(R.string.image6din1)),
                        Double.parseDouble(getString(R.string.lat6)),
                        Double.parseDouble(getString(R.string.long6)),
                        getString(R.string.link6)));
                listOfContents.add(new ContentPage(6,getString(R.string.nameinfo7),
                        getString(R.string.contentinfo7),
                        imageViewToByte(getString(R.string.image7din1)),
                        Double.parseDouble(getString(R.string.lat7)),
                        Double.parseDouble(getString(R.string.long7)),
                        getString(R.string.link7)));
                listOfContents.add(new ContentPage(7,getString(R.string.nameinfo8),
                        getString(R.string.contentinfo8),
                        imageViewToByte(getString(R.string.image8din1)),
                        Double.parseDouble(getString(R.string.lat8)),
                        Double.parseDouble(getString(R.string.long8)),
                        getString(R.string.link8)));
                listOfContents.add(new ContentPage(8,getString(R.string.nameinfo9),
                        getString(R.string.contentinfo9),
                        imageViewToByte(getString(R.string.image9din1)),
                        Double.parseDouble(getString(R.string.lat9)),
                        Double.parseDouble(getString(R.string.long9)),
                        getString(R.string.link9)));
                listOfContents.add(new ContentPage(9,getString(R.string.nameinfo10),
                        getString(R.string.contentinfo10),
                        imageViewToByte(getString(R.string.image10din1)),
                        Double.parseDouble(getString(R.string.lat10)),
                        Double.parseDouble(getString(R.string.long10)),
                        getString(R.string.link10)));
                listOfContents.add(new ContentPage(10,getString(R.string.nameinfo11),
                        getString(R.string.contentinfo11),
                        imageViewToByte(getString(R.string.image11din1)),
                        Double.parseDouble(getString(R.string.lat11)),
                        Double.parseDouble(getString(R.string.long11)),
                        getString(R.string.link11)));
                listOfContents.add(new ContentPage(11,getString(R.string.nameinfo12),
                        getString(R.string.contentinfo12),
                        imageViewToByte(getString(R.string.image12din1)),
                        Double.parseDouble(getString(R.string.lat12)),
                        Double.parseDouble(getString(R.string.long12)),
                        getString(R.string.link12)));
                listOfContents.add(new ContentPage(12,getString(R.string.nameinfo13),
                        getString(R.string.contentinfo13),
                        imageViewToByte(getString(R.string.image13din1)),
                        Double.parseDouble(getString(R.string.lat13)),
                        Double.parseDouble(getString(R.string.long13)),
                        getString(R.string.link13)));
                listOfContents.add(new ContentPage(13,getString(R.string.nameinfo14),
                        getString(R.string.contentinfo14),
                        imageViewToByte(getString(R.string.image14din1)),
                        Double.parseDouble(getString(R.string.lat14)),
                        Double.parseDouble(getString(R.string.long14)),
                        getString(R.string.link14)));
                listOfContents.add(new ContentPage(14,getString(R.string.nameinfo15),
                        getString(R.string.contentinfo15),
                        imageViewToByte(getString(R.string.image15din1)),
                        Double.parseDouble(getString(R.string.lat15)),
                        Double.parseDouble(getString(R.string.long15)),
                        getString(R.string.link15)));
                listOfContents.add(new ContentPage(15,getString(R.string.nameinfo16),
                        getString(R.string.contentinfo16),
                        imageViewToByte(getString(R.string.image16din1)),
                        Double.parseDouble(getString(R.string.lat16)),
                        Double.parseDouble(getString(R.string.long16)),
                        getString(R.string.link16)));
                listOfContents.add(new ContentPage(16,getString(R.string.nameinfo17),
                        getString(R.string.contentinfo17),
                        imageViewToByte(getString(R.string.image17din1)),
                        Double.parseDouble(getString(R.string.lat17)),
                        Double.parseDouble(getString(R.string.long17)),
                        getString(R.string.link17)));
                listOfContents.add(new ContentPage(17,getString(R.string.nameinfo18),
                        getString(R.string.contentinfo18),
                        imageViewToByte(getString(R.string.image18din1)),
                        Double.parseDouble(getString(R.string.lat18)),
                        Double.parseDouble(getString(R.string.long18)),
                        getString(R.string.link18)));
                listOfContents.add(new ContentPage(18,getString(R.string.nameinfo19),
                        getString(R.string.contentinfo19),
                        imageViewToByte(getString(R.string.image19din1)),
                        Double.parseDouble(getString(R.string.lat19)),
                        Double.parseDouble(getString(R.string.long19)),
                        getString(R.string.link19)));
                listOfContents.add(new ContentPage(19,getString(R.string.nameinfo20),
                        getString(R.string.contentinfo20),
                        imageViewToByte(getString(R.string.image20din1)),
                        Double.parseDouble(getString(R.string.lat20)),
                        Double.parseDouble(getString(R.string.long20)),
                        getString(R.string.link19)));



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
         name=name.toLowerCase();
        System.out.println(name);
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

                return listofcontent;
            }

            @Override
            protected void onPostExecute(List<ImagePage>listofcontent) {
                super.onPostExecute(listofcontent);
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