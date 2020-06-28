package com.example.mapvers3.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapvers3.ImagePage;
import com.example.mapvers3.R;
import com.example.mapvers3.ui.home.ImageAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GalerryAdapter extends RecyclerView.Adapter<GalerryAdapter.ViewHolder>{

    private List<ImagePage> images;

    public GalerryAdapter(List<ImagePage> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagegalerry,parent,false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        byte[] image = images.get(position).getImage();
        Bitmap bitmab = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.image.setImageBitmap(bitmab);
    }

    @Override
    public int getItemCount() {
       return images.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;

        @SuppressLint("WrongConstant")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_viewgalerry);
        }
    }

}
