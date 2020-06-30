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

public class HomeAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {


    @NonNull
    @Override
    public ContentAdapter.ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ContentAdapter.ContentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
