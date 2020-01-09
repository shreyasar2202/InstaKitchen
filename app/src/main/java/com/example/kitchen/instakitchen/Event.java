package com.example.kitchen.instakitchen;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Shreyas on 23-07-2017.
 */

public class Event {

    public final String title;

    public final Bitmap bmp;

    public final String ID;

    public Event(String eventTitle, Bitmap bitmap, String rID) {
        title = eventTitle;
        bmp = bitmap;
        ID = rID;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getBitmap() {
        return bmp;
    }

    public String getID() {
        return ID;
    }

}

