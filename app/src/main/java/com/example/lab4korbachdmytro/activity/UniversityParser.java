package com.example.lab4korbachdmytro.activity;

import com.example.lab4korbachdmytro.database.University;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class UniversityParser {
    public static List<University> parseJsonArrayToUniversities(String jsonArrayString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<University>>(){}.getType();
        return gson.fromJson(jsonArrayString, listType);
    }
}