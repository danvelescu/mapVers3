package com.example.mapvers3;

import android.content.Context;

import androidx.room.Room;

public class DataBaseClient {
    private Context mCtx;
    private static DataBaseClient mInstance;

    private AppDatabase appDatabase;

    private DataBaseClient(Context mCtx){
        this.mCtx = mCtx;
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class,"contentToMap").build();
    }

    public static synchronized  DataBaseClient getInstance(Context mCtx){
        if(mInstance == null){
            mInstance = new DataBaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase(){return appDatabase;}

}
