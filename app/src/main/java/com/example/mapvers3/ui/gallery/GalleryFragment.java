package com.example.mapvers3.ui.gallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapvers3.ContentPage;
import com.example.mapvers3.DataBaseClient;
import com.example.mapvers3.ImagePage;
import com.example.mapvers3.R;

import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
 private    RecyclerView recyclerViewgalerry;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerViewgalerry = root.findViewById(R.id.recyclerViewgalerry);
        recyclerViewgalerry.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewgalerry.setLayoutManager(layoutManager);
            getImage();

        return root;
    }
    private void getImage(){
        class GetTasks extends AsyncTask<Void,Void, List<ImagePage>> {

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
            protected void onPostExecute(List<ImagePage> images) {

                super.onPostExecute(images);
                mAdapter = new GalerryAdapter(images);
                recyclerViewgalerry.setAdapter(mAdapter);
            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }

}