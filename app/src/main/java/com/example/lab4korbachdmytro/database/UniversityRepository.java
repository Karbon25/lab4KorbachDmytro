package com.example.lab4korbachdmytro.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UniversityRepository {
    private UniversityDao universityDao;
    private LiveData<List<University>> allUniversities;

    public UniversityRepository(Application application) {
        UniversityDatabase db = UniversityDatabase.getDatabase(application);
        universityDao = db.universityDao();
    }

    public List<University> getAllUniversities() {
        return universityDao.getAllUniversities();
    }
    public University getUniversityByName(String name){
        return universityDao.getUniversityByName(name);
    }

    public List<University> getUniversityByNamePrefix(String namePrefix){
        return universityDao.getUniversityByNamePrefix(namePrefix);
    }
    public void setUniversities(List<University> universities) {
        universityDao.deleteAll();
        for(University u:universities){
            if(u.getAlphaTwoCode().equals("UA")){
                universityDao.insert(u);
            }

        }
    }
}