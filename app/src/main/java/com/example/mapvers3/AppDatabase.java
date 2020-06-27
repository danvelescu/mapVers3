package com.example.mapvers3;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ContentPage.class,ImagePage.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContenDao contentDao();
}