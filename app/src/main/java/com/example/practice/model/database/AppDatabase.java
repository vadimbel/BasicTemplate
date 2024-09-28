package com.example.practice.model.database;

import com.example.practice.model.models.UserData;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // this class extends RoomDatabase and serves as the main access point to your Room database.
    public abstract UserDao userDao();
}
