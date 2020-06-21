package com.example.mapvers3.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapvers3.ContentPage;
import com.example.mapvers3.MapActivity;
import com.example.mapvers3.R;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
    private Context mCtx;
    private List<ContentPage> contentlist;

    public ContentAdapter(Context mCtx, List<ContentPage> contentlist){
        this.mCtx = mCtx;
        this.contentlist = contentlist;
        Log.println(Log.ASSERT,"alert",contentlist.get(0).getInfo());
        Log.println(Log.ASSERT,"alert","lista primita");
    }



    @Override
    public ContentViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        Log.println(Log.ASSERT,"alert","lista trimisa1");
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks,parent,false);
        Log.println(Log.ASSERT,"alert","lista trimisa1");
        return new ContentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        Log.println(Log.ASSERT,"alert","lista trimisa1");
        ContentPage c = contentlist.get(position);
        holder.text1.setText(c.getInfo());
        holder.text2.setText(c.getNameInfo());
        byte[] image = c.getImage();
        Bitmap bitmab = BitmapFactory.decodeByteArray(image,0,image.length);
        holder.image.setImageBitmap(bitmab);
        Log.println(Log.ASSERT,"alert","lista trimisa1");
    }



    @Override
    public int getItemCount() {
        Log.println(Log.ASSERT,"alert","lista trimisa1");
        return contentlist.size();

    }

    class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text1,text2;
        ImageView image;

        public ContentViewHolder(@NonNull View itemView) {

            super(itemView);
            Log.println(Log.ASSERT,"alert","lista trimisa1");
            text1 = itemView.findViewById(R.id.textView2);
            text2 = itemView.findViewById(R.id.textView3);
            image = itemView.findViewById(R.id.imageContent);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Log.println(Log.ASSERT,"alert","lista trimisa1");
                ContentPage contentPage = contentlist.get(getAdapterPosition());

                Intent intent=new Intent(mCtx, MapActivity.class);
        }
    }
}
