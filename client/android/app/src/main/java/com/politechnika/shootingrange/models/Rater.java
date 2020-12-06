package com.politechnika.shootingrange.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jacek on 16.11.2017.
 */

public class Rater  implements Serializable {
    @SerializedName("raterId")
    private int id;
    @SerializedName("raterName")
    private String name;
    @SerializedName("raterSurname")
    private String surname;
    @SerializedName("raterLegitimationNumber")
    private String legitimationNumber;
    @SerializedName("raterTitle")
    private String title;

    public Rater() {

    }

    public Rater(int id, String name, String surname, String legitimationNumber, String title) {
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
        return String.format("Rater#name: %s, surname: %s, legitimation: %s, title: %s", name, surname, legitimationNumber, title);
    }

}
