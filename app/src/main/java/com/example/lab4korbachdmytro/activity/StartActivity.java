package com.example.lab4korbachdmytro.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lab4korbachdmytro.R;
import com.example.lab4korbachdmytro.database.University;
import com.example.lab4korbachdmytro.database.UniversityRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class StartActivity extends AppCompatActivity {
    private final String urlRequest = "http://universities.hipolabs.com/search";
    private CountDownTimer timerChangeActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        timerChangeActivity = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent changeActivity = new Intent(StartActivity.this, MainActivity.class);
                changeActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                StartActivity.this.startActivity(changeActivity);
            }
        };

        TextView textState = findViewById(R.id.textViewStateConnection);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        ObjectAnimator.ofInt(progressBar, "progress", 50).setDuration(1000).start();
        new UpdateDatabaseThread().start();
    }




    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private class UpdateDatabaseThread extends Thread{
        Handler handler;
        public UpdateDatabaseThread(){
            handler = new Handler(Looper.myLooper());
        }
        @Override
        public void run() {
            if(StartActivity.isNetworkAvailable(StartActivity.this) == false){
                handler.post(new Thread() {
                    @Override
                    public void run() {
                        TextView textStatus = findViewById(R.id.textViewStateConnection);
                        textStatus.setText("Немає з'єднання з мережею");
                        timerChangeActivity.start();
                    }
                });
            }else{
                handler.post(new Thread() {
                    @Override
                    public void run() {
                        TextView textStatus = findViewById(R.id.textViewStateConnection);
                        textStatus.setText("Підключення до API сервісу");
                    }
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String responseMessage = null;
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(urlRequest)
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        throw new IOException(response.code() + " " + response.message());
                    }
                    responseMessage = response.body().string();
                    handler.post(new Thread() {
                        @Override
                        public void run() {
                            TextView textStatus = findViewById(R.id.textViewStateConnection);
                            textStatus.setText("Дані отримано");
                        }
                    });
                }catch (Exception e) {
                    handler.post(new Thread() {
                        @Override
                        public void run() {
                            TextView textStatus = findViewById(R.id.textViewStateConnection);
                            textStatus.setText("Сервіс недоступний");
                            timerChangeActivity.start();
                        }
                    });
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(responseMessage != null) {
                    Gson gson = new Gson();
                    List<University> universityList = gson.fromJson(responseMessage, new TypeToken<List<University>>() {}.getType());
                    handler.post(new Thread() {
                        @Override
                        public void run() {
                            TextView textStatus = findViewById(R.id.textViewStateConnection);
                            textStatus.setText("Оновлення бази даних");
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    UniversityRepository database = new UniversityRepository(StartActivity.this.getApplication());
                    database.setUniversities(universityList);
                    handler.post(new Thread() {
                        @Override
                        public void run() {
                            TextView textStatus = findViewById(R.id.textViewStateConnection);
                            textStatus.setText("Оновлення завершено");
                            timerChangeActivity.start();
                        }
                    });
                }
            }


        }
    }
}