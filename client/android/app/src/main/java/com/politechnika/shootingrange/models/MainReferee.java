package com.politechnika.shootingrange.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jacek on 16.11.2017.
 */

public class MainReferee implements Serializable {
    @SerializedName("mainRefereeId")
    private int id;
    @SerializedName("mainRefereeName")
    private String name;
    @SerializedName("mainRefereeSurname")
    private String surname;
    @SerializedName("mainRefereeLegitimationNumber")
    private String legitimationNumber;
    @SerializedName("mainRefereeTitle")
    private String title;

    public MainReferee() {
    }

    public MainReferee(int id, String name, String surname, String legitimationNumber, String title) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.legitimationNumber = legitimationNumber;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLegitimationNumber() {
        return legitimationNumber;
    }

    public void setLegitimationNumber(String legitimationNumber) {
        this.legitimationNumber = legitimationNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullname(){
        return this.name + " " + this.surname;
    }

    @Override
    public String toString() {
        return String.format("MainReferee#name: %s, surname: %s, legitimation: %s, title: %s", name, surname, legitimationNumber, title);
    }
}
