package com.example.practice.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.practice.model.models.UserData;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<UserData> users);

    @Query("SELECT * FROM users")
    List<UserData> getAllUsers();  // Fetch all users stored in the database

}


