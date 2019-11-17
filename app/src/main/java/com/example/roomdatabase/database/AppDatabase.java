package com.example.roomdatabase.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.roomdatabase.dao.MahasiswaDAO;
import com.example.roomdatabase.model.ModelMahasiswa;

@Database(entities = ModelMahasiswa.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MahasiswaDAO mahasiswaDAO();
}
