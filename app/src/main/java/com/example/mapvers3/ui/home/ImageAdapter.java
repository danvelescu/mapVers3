package com.example.mapvers3.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.mapvers3.ContentPage;
import com.example.mapvers3.DataBaseClient;
import com.example.mapvers3.ImagePage;
import com.example.mapvers3.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
private Context mCtx;
    private List<ImagePage> images = new ArrayList<>();

int superpostion;
    public ImageAdapter(List<ImagePage> images, int parentadaptorpostion) {
        this.superpostion=parentadaptorpostion;
        for(ImagePage i:images){
            if(i.getContentID()==superpostion){
                this.images.add(images.get(i.getId()));
            }
        }
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.image.setImageDrawable(null);
        if(images.get(position).getContentID()==superpostion) {
            byte[] image = images.get(position).getImage();
            Bitmap bitmab = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.image.setImageBitmap(bitmab);
        }
    }



    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;

        @SuppressLint("WrongConstant")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view2);
        }
    }
}
