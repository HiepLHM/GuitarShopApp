package com.example.myguitarproject.DataLocal;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myguitarproject.user.User;

@Database(entities = {User.class}, version = 2)
public abstract class DataLocal extends RoomDatabase {
    public static Migration migration_1_to_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE User ADD COLUMN RoleAccount Text");
        }
    };
    private static final String DATABASE_NAME = "guitar.db";
    private static DataLocal instance;

    public static synchronized DataLocal getInstance(Context mContext) {
        if (instance == null) {
            instance = Room.databaseBuilder(mContext.getApplicationContext(), DataLocal.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addMigrations(migration_1_to_2)
                    .build();
        }
        return instance;
    }

    public abstract LocalDAO localDAO();
}
