package com.example.lab4korbachdmytro.viewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab4korbachdmytro.R;
import com.example.lab4korbachdmytro.database.University;
import com.example.lab4korbachdmytro.database.UniversityRepository;

import java.util.List;

public class ViewModelShowDetailedInformation extends ViewModel {
    private MutableLiveData<University> universityData = new MutableLiveData<>();
    private UniversityRepository database;

    public void connectDatabase(Application app){
        database = new UniversityRepository(app);
    }
    public void setUniversity(String name){
        new GetDatabaseThread(database, name).start();
    }

    public void setUniversityView(University universityData) {
        this.universityData.setValue(universityData);
    }
    public LiveData<University> getUniversityView() {
        return universityData;
    }
    private class GetDatabaseThread extends Thread{
        Handler handler;
        String name;
        UniversityRepository database;
        public GetDatabaseThread(UniversityRepository database,String name){
            handler = new Handler(Looper.myLooper());
            this.name = name;
            this.database = database;
        }

        @Override
        public void run() {
            if(database != null && name != null) {
                University u = database.getUniversityByName(name);
                handler.post(new Thread() {
                    @Override
                    public void run() {
                        setUniversityView(u);
                    }
                });
            }
        }
    }

}