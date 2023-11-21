package com.example.lab4korbachdmytro.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {University.class}, version = 1)
public abstract class UniversityDatabase extends RoomDatabase {
    public abstract UniversityDao universityDao();

    private static volatile UniversityDatabase INSTANCE;

    public static UniversityDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UniversityDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UniversityDatabase.class, "university_database").build();;
                }
            }
        }
        return INSTANCE;
    }
}