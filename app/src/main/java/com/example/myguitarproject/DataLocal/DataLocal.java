package com.example.myguitarproject.DataLocal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myguitarproject.user.User;

@Database(entities = {User.class}, version = 1)
public abstract class DataLocal extends RoomDatabase {
    private static final String DATABASE_NAME = "guitar.db";
    private static DataLocal instance;
    public static synchronized DataLocal getInstance(Context mContext){
        if(instance==null){
            instance = Room.databaseBuilder(mContext.getApplicationContext(), DataLocal.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract LocalDAO localDAO();
}
