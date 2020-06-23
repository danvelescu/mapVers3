package com.example.mapvers3;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ContenDao {
    @Query("SELECT * FROM ContentPage")
    List<ContentPage> getAll();

    @Insert
    void insert(ContentPage content);

    @Delete
    void delete(ContentPage content);

    @Update
    void update(ContentPage task);
}
