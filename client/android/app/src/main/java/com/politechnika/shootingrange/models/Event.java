package com.politechnika.shootingrange.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jacek on 16.11.2017.
 */

public class Event implements Serializable {
    public static final String TAG_ID_COMPETITION = "idCompetition";
    public static final String TAG_CLICKED_EVENT = "clickedEvent";
    public static final String TAG_THUMBNAIL_URL = "thumbnailUrl";
    public static final String TAG_EVENT_NAME = "competitionName";
    public static final String TAG_EVENT_DESCRIPTION = "competitionDescription";
    public static final String TAG_EVENT_DATE = "competitionDate";
    public static final String TAG_PRICE = "price";
    public static final String TAG_NUMBER_OF_COMPETITORS = "numberOfCompetitors";

    @Override
    public String toString() {
        return String.format("Event#idCompetition: %s, competitionName: %s, competitionDescription: %s, competitionDate: %s, price: %s, numberOfCompetitors: %s, thumbnailUrl: %s\n" +
                "mainReferee: %s\n" +
                "rater: %s\n" +
                "typeOfCompetition: %s\n", idCompetition, competitionName, competitionDescription, competitionDate, price, numberOfCompetitors, thumbnailUrl, mainReferee, rater, typeOfCompetition);
    }
    @SerializedName(TAG_ID_COMPETITION)
    private String idCompetition;
    @SerializedName(TAG_EVENT_NAME)
    private String competitionName;
    @SerializedName(TAG_EVENT_DESCRIPTION)
    private String competitionDescription;
    @SerializedName(TAG_EVENT_DATE)
    private String competitionDate;

    @SerializedName("mainReferee")
    private MainReferee mainReferee;
    @SerializedName("rater")
    private Rater rater;
    @SerializedName("typeOfCompetition")
    private TypeOfCompetition typeOfCompetition;

    @SerializedName("price")
    private double price;
    @SerializedName("numberOfCompetitors")
    private int numberOfCompetitors;
    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;

    // determines whether event is available or unavailable for user
    private boolean isAvailable;

    public Event() {

    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public String getCompetitionDescription() {
        return competitionDescription;
    }

    public void setCompetitionDescription(String competitionDescription) {
        this.competitionDescription = competitionDescription;
    }

    public String getCompetitionDate() {
        return competitionDate;
    }

    public void setCompetitionDate(String competitionDate) {
        this.competitionDate = competitionDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumberOfCompetitors() {
        return numberOfCompetitors;
    }

    public void setNumberOfCompetitors(int numberOfCompetitors) {
        this.numberOfCompetitors = numberOfCompetitors;
    }

    public String getIdCompetition() {
        return idCompetition;
    }

    public void setIdCompetition(String idCompetition) {
        this.idCompetition = idCompetition;
    }

    public MainReferee getMainReferee() {
        return mainReferee;
    }

    public void setMainReferee(MainReferee mainReferee) {
        this.mainReferee = mainReferee;
    }

    public Rater getRater() {
        return rater;
    }

    public void setRater(Rater rater) {
        this.rater = rater;
    }

    public TypeOfCompetition getTypeOfCompetition() {
        return typeOfCompetition;
    }

    public void setTypeOfCompetition(TypeOfCompetition typeOfCompetition) {
        this.typeOfCompetition = typeOfCompetition;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }


    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


    public boolean isOutdated() {
        Date competitionParsedDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            competitionParsedDate = sdf.parse(competitionDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (new Date().before(competitionParsedDate)){
            return false;
        }
        else {
            return true;
        }
    }

}
