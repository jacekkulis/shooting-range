package com.politechnika.shootingrange.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jacek on 16.11.2017.
 */

public class TypeOfCompetition implements Serializable {
    @SerializedName("typeOfCompetitionId")
    private int id;
    @SerializedName("typeOfCompetitionNameOfType")
    private String nameOfType;
    @SerializedName("typeOfCompetitionFullName")
    private String fullName;

    public TypeOfCompetition() {

    }

    public TypeOfCompetition(int id, String nameOfType, String fullName) {
        this.id = id;
        this.nameOfType = nameOfType;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfType() {
        return nameOfType;
    }

    public void setNameOfType(String nameOfType) {
        this.nameOfType = nameOfType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return String.format("TypeOfCompetition#name: %s, nameOfType: %s, fullName: %s", String.valueOf(id), nameOfType, fullName);
    }
}
