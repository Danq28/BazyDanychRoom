package com.example.myapplication;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Zwierze.class},version = 1)
public abstract class ZwierzetaDataBase extends RoomDatabase {
    public abstract ZwierzeDAO zwrocZwierzeDao();
}
