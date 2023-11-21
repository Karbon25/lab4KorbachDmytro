
package com.example.lab4korbachdmytro.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lab4korbachdmytro.R;
import com.example.lab4korbachdmytro.database.University;
import com.example.lab4korbachdmytro.database.UniversityRepository;
import com.example.lab4korbachdmytro.viewModel.ViewModelShowTableUniversity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements UniversityAdapter.OnItemClickListener {
    private ViewModelShowTableUniversity viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(ViewModelShowTableUniversity.class);
        viewModel.connectDatabase(getApplication());
        viewModel.getAllUniversity();
        RecyclerView recyclerUniversityView = findViewById(R.id.list_university);
        viewModel.getUniversityView().observe(this, new Observer<List<University>>() {
            @Override
            public void onChanged(List<University> universities) {
                recyclerUniversityView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                UniversityAdapter adapter = new UniversityAdapter(MainActivity.this, universities);
                adapter.setOnItemClickListener(MainActivity.this);
                recyclerUniversityView.setAdapter(adapter);
            }
        });
        EditText findUniversityByName = findViewById(R.id.find_university_by_name);
        findUniversityByName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
              if(TextUtils.isEmpty(findUniversityByName.getText().toString())){
                    viewModel.getAllUniversity();
              }else{
                    viewModel.getUniversityByNameAndCountry(findUniversityByName.getText().toString());
              }
            }
        });

    }

    @Override
    public void onItemClickEvent(University universityClick){
        Intent changeActivity = new Intent(this, DetailedInformationActivity.class);
        changeActivity.putExtra("name", universityClick.getName());
        startActivity(changeActivity);
    }
}