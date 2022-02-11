package com.example.myguitarproject.DataLocal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myguitarproject.user.User;

import java.util.List;
@Dao
public interface LocalDAO {
    @Insert
    void insertUserLocal(User user);

    @Query("SELECT * FROM User WHERE username= :name")
    List<User> checkUserExists(String name);

    @Query("SELECT * FROM User")
    List<User> getListUserLocal();

    @Query("DELETE FROM User")
    void deleteAllUser();
}
