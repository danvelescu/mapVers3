package com.example.mapvers3.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mapvers3.ContentPage;
import com.example.mapvers3.DataBaseClient;
import com.example.mapvers3.ImagePage;
import com.example.mapvers3.MapActivity;
import com.example.mapvers3.MapsFragment;
import com.example.mapvers3.R;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.Inflater;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
     public static List<ContentPage> content;
     private static int adapterposition;
     private Context mCtx;
     private List<ContentPage> contentlist;
     FragmentManager root;
     private List<ImagePage>imagePages = new ArrayList<>();
     private  RecyclerView recyclerView1;
     private ImageAdapter adapter;


    public ContentAdapter(Context mCtx, List<ContentPage> contentlist,List<ImagePage>imagePages , FragmentManager root){
        this.mCtx = mCtx;
        this.contentlist = contentlist;
        this.imagePages=imagePages;
        this.root = root;
    }

    public ContentAdapter(Context mCtx, List<ContentPage> contentlist, FragmentManager root){
        this.mCtx = mCtx;
        this.contentlist = contentlist;
        for (ContentPage i:contentlist) {
            this.imagePages.add(new ImagePage(i.getId(),i.getId(),i.getImage()));
        }
        this.root = root;
    }



    @Override
    public ContentViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks,parent,false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContentViewHolder holder, final int position) {
        ContentPage c = contentlist.get(position);
        holder.text1.setText(c.getInfo());
        holder.text2.setText(c.getNameInfo());
        holder.linkbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(contentlist.get(position).getLink()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mCtx.startActivity(intent);
            }
        });
        adapter = new ImageAdapter(imagePages,c.getId());
        setAdapter();
       // recyclerView1.setAdapter(adapter);
    }
    private void setAdapter(){
        recyclerView1.setAdapter(adapter);
    }
    @Override
    public int getItemCount() {
        return contentlist.size();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text1,text2;
        Button linkbtn;


        public ContentViewHolder(@NonNull View itemView)  {
            super(itemView);
             linkbtn = itemView.findViewById(R.id.button);
            text1 = itemView.findViewById(R.id.textView2);
            text2 = itemView.findViewById(R.id.textView3);
            recyclerView1 = itemView.findViewById(R.id.recyclerView2);
            recyclerView1.setLayoutManager(new LinearLayoutManager(mCtx,LinearLayoutManager.HORIZONTAL,false));

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            adapterposition = getAdapterPosition();
                ContentPage contentPage = contentlist.get(getAdapterPosition());
                content = contentlist;
               openMapLocation();

        }
    }

    public void openMapLocation() {
        Bundle argument = new Bundle();
        argument.putInt("id",contentlist.get(adapterposition).getId());
        MapsFragment mapFrag = new MapsFragment();
        FragmentTransaction transaction =root.beginTransaction();
        mapFrag.setArguments(argument);
        transaction.add(R.id.nav_host_fragment,mapFrag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
  List<ImagePage> getSortImages(int pos,List<ImagePage> imagePage){
      List<ImagePage> sortimg=new ArrayList<>();
     for(int i=0;i<imagePage.size();i++){
         if(pos==imagePage.get(i).getContentID()){
             System.out.println("----------------------------------------"+imagePage.get(i).getContentID());
             sortimg.add(imagePage.get(i));
         }
     }
     return sortimg;
  }

}
