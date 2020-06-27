package com.example.mapvers3;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "images")
public class ImagePage {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "contentID")
    int contentID;

    @ColumnInfo(name = "image")
    byte[] image;

    public ImagePage(int id, int contentID, byte[] image) {
        this.id = id;
        this.contentID = contentID;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContentID() {
        return contentID;
    }

    public void setContentID(int contentID) {
        this.contentID = contentID;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
