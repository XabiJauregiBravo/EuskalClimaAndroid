package com.example.ejereuskalmet.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Balizas.class,Datos.class}, version = 12)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BalizasDao balizasDao();
}