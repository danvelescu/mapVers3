package com.example.mapvers3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ContentPage implements Serializable {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "nameInfo")
    private String nameInfo;

    @ColumnInfo(name = "info")
    private String info;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB,name = "image")
    private byte[] image;

    @ColumnInfo(name = "lat")
    private Double lat;

    @ColumnInfo(name = "long")
    private Double longitudine;

    public ContentPage(int id, String nameInfo, String info, byte[] image, Double lat, Double longitudine) {
        this.id = id;
        this.nameInfo = nameInfo;
        this.info = info;
        this.image = image;
        this.lat = lat;
        this.longitudine = longitudine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameInfo() {
        return nameInfo;
    }

    public void setNameInfo(String nameInfo) {
        this.nameInfo = nameInfo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(Double longitudine) {
        this.longitudine = longitudine;
    }

}
