package com.example.lab4korbachdmytro.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UniversityDao {
    @Query("SELECT * FROM universities")
    List<University> getAllUniversities();
    @Query("SELECT * FROM universities WHERE name = :name")
    University getUniversityByName(String name);
    @Query("SELECT * FROM universities WHERE name LIKE '%' || :namePrefix || '%'")
    List<University> getUniversityByNamePrefix(String namePrefix);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(University university);
    @Update
    void update(University university);
    @Delete
    void delete(University university);
    @Query("DELETE FROM universities")
    void deleteAll();
}