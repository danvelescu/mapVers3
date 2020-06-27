package com.example.mapvers3.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
   public static List<ContentPage> content;
    private static int adapterposition;
    private Context mCtx;
    private List<ContentPage> contentlist;
    FragmentManager root;
   private List<ImagePage>imagePages;
  private  RecyclerView recyclerView1;
  private ImageAdapter adapter;
    public ContentAdapter(Context mCtx, List<ContentPage> contentlist, FragmentManager fragmentManager,List<ImagePage>imagePages){
        this.mCtx = mCtx;
        this.contentlist = contentlist;
        this.root = fragmentManager;
        this.imagePages=imagePages;
    }



    @Override
    public ContentViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks,parent,false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        ContentPage c = contentlist.get(position);
        holder.text1.setText(c.getInfo());
        holder.text2.setText(c.getNameInfo());
        System.out.println("test111.3");
        List<ImagePage>list=getSortImages(position,imagePages);
        adapter = new ImageAdapter(list,mCtx,position);
        recyclerView1.setAdapter(adapter);


        //---------------------------------------------
//        byte[] image = c.getImage();
//        Bitmap bitmab = BitmapFactory.decodeByteArray(image,0,image.length);
//        holder.image.setImageBitmap(bitmab);
    }

    @Override
    public int getItemCount() {
        return contentlist.size();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text1,text2;


        public ContentViewHolder(@NonNull View itemView)  {
            super(itemView);
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
             sortimg.add(imagePage.get(i));
         }
         System.out.println(imagePage.size()+"ddddddd");
     }
     return sortimg;
  }

}
