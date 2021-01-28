package com.example.gsonrxjavaretrofittest.converters;

import androidx.room.TypeConverter;

import com.example.gsonrxjavaretrofittest.pojo.Specialty;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.converter.gson.GsonConverterFactory;

public class Converter {
    @TypeConverter
    public String listSpecialtyToString(List<Specialty> specialties){
        return new Gson().toJson(specialties);
    }

    @TypeConverter
    public List<Specialty> stringSpecialtyToListSpecialty(String stringSpecialties){
        Gson gson = new Gson();
        ArrayList objects = gson.fromJson(stringSpecialties,ArrayList.class);
        ArrayList<Specialty> specialties = new ArrayList<>();
        for (Object o:objects){
            specialties.add(gson.fromJson(o.toString(),Specialty.class));
        }
        return specialties;

    }
}
