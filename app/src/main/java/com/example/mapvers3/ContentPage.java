package com.example.mapvers3;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ContentPage implements Parcelable {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "link")
    private String link;

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

    public ContentPage(){}

    public ContentPage(int id, String nameInfo, String info, byte[] image, Double lat, Double longitudine,String link) {
        this.id = id;
        this.nameInfo = nameInfo;
        this.info = info;
        this.image = image;
        this.lat = lat;
        this.longitudine = longitudine;
        this.link=link;
    }





    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.nameInfo);
        parcel.writeString(this.info);
        parcel.writeByteArray(this.image);
        parcel.writeDouble(this.lat);
        parcel.writeDouble(this.longitudine);
        parcel.writeString(this.link);
    }

    protected ContentPage(Parcel in) {
        id = in.readInt();
        link = in.readString();
        nameInfo = in.readString();
        info = in.readString();
        image = in.createByteArray();
        if (in.readByte() == 0) {
            lat = null;
        } else {
            lat = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitudine = null;
        } else {
            longitudine = in.readDouble();
        }
    }

    public static final Creator<ContentPage> CREATOR = new Creator<ContentPage>() {
        @Override
        public ContentPage createFromParcel(Parcel in) {
            return new ContentPage(in);
        }

        @Override
        public ContentPage[] newArray(int size) {
            return new ContentPage[size];
        }
    };
}
