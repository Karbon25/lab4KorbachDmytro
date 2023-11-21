package com.example.lab4korbachdmytro.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(tableName = "universities")
@TypeConverters(ListStringConverter.class)
public class University {
    @PrimaryKey
    @NonNull
    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;


    @SerializedName("alpha_two_code")
    @ColumnInfo(name = "alpha_two_code")
    private String alphaTwoCode;


    @SerializedName("country")
    @ColumnInfo(name = "country")
    private String country;


    @SerializedName("state_province")
    @ColumnInfo(name = "state_province")
    private String stateProvince;


    @SerializedName("domains")
    @ColumnInfo(name = "domains")
    private List<String> domains;


    @SerializedName("web_pages")
    @ColumnInfo(name = "web_pages")
    private List<String> webPages;


    @SerializedName("established_year")
    @ColumnInfo(name = "established_year")
    private int establishedYear;

    public University(){
        if(this.name == null){
            this.name = "";
        }
        if(this.alphaTwoCode == null){
            this.alphaTwoCode = "";
        }
        if(this.country == null){
            this.country = "";
        }
        if(this.stateProvince == null){
            this.stateProvince = "";
        }
        if(this.webPages == null){
            this.webPages = new ArrayList<>();
        }
        if(this.domains == null){
            this.domains = new ArrayList<>();
        }
    }

    public University(String alphaTwoCode, String country, String stateProvince,
                      @NonNull String name, List<String> webPages, int establishedYear, List<String> domains) {
        this.alphaTwoCode = alphaTwoCode;
        this.country = country;
        this.stateProvince = stateProvince;
        this.name = name;
        this.webPages = webPages;
        this.establishedYear = establishedYear;
        this.domains = domains;
    }

    @NonNull
    public String getAlphaTwoCode() {
        return alphaTwoCode;
    }

    public String getCountry() {
        return country;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public String getName() {
        return name;
    }

    public List<String> getWebPages() {
        return webPages;
    }

    public int getEstablishedYear() {
        return establishedYear;
    }

    public List<String> getDomains() {
        return domains;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(!alphaTwoCode.equals(((University)obj).getAlphaTwoCode())){
            return(false);
        }
        if(!name.equals(((University)obj).getName())){
            return(false);
        }
        if(!country.equals(((University)obj).getCountry())){
            return(false);
        }
        if(!stateProvince.equals(((University)obj).getStateProvince())){
            return(false);
        }
        if(!Arrays.equals(domains.toArray(), ((University)obj).getDomains().toArray())){
            return(false);
        }
        if(!Arrays.equals(webPages.toArray(), ((University)obj).getWebPages().toArray())){
            return(false);
        }
        if(establishedYear != ((University)obj).getEstablishedYear()){
            return(false);
        }
        return(true);
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setAlphaTwoCode(String alphaTwoCode) {
        this.alphaTwoCode = alphaTwoCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public void setWebPages(List<String> webPages) {
        this.webPages = webPages;
    }

    public void setEstablishedYear(int establishedYear) {
        this.establishedYear = establishedYear;
    }
}