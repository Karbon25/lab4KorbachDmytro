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

public class ViewModelShowTableUniversity extends ViewModel {
    private MutableLiveData<List<University>> universityData = new MutableLiveData<>();
    private UniversityRepository database;

    public void connectDatabase(Application app){
        database = new UniversityRepository(app);
    }
    public void getAllUniversity(){
        new GetDatabaseThread(database).start();
    }
    public void getUniversityByNameAndCountry(String name){
        new GetDatabaseThread(database, name).start();
    }
    public void setUniversityView(List<University> universityData) {
        this.universityData.setValue(universityData);
    }
    public LiveData<List<University>> getUniversityView() {
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
        public GetDatabaseThread(UniversityRepository database){
            handler = new Handler(Looper.myLooper());
            this.database = database;
        }
        @Override
        public void run() {
            if(database != null) {
                if( name == null){
                    List<University> list = database.getAllUniversities();
                    handler.post(new Thread() {
                        @Override
                        public void run() {
                            setUniversityView(list);
                        }
                    });
                }else{
                    List<University> list = database.getUniversityByNamePrefix(name);
                    handler.post(new Thread() {
                        @Override
                        public void run() {
                            setUniversityView(list);
                        }
                    });
                }
            }
        }
    }

}
