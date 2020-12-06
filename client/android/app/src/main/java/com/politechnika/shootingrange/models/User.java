package com.politechnika.shootingrange.models;

import com.politechnika.shootingrange.fragments.EditProfileFragment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Jacek on 04.11.2017.
 */

public class User implements Serializable {
    private int idUser;
    private String username;
    private String name;
    private String surname;
    private int idClub;
    private String legitimationNumber;
    private String phoneNumber;
    private String city;
    private String address;

    private String password;

    /**
     * This variable represents OnUserChangedListener passed in by the owning object.
     */
    private OnUserChangedListener userChangedListener;

    public User() {

    }

    public User(String username, String password, String name, String surname, int idClub, String legitimationNumber, String phoneNumber, String city, String address) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.idClub = idClub;
        this.legitimationNumber = legitimationNumber;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.address = address;
        this.password = password;
    }

    public User(int idUser, String username, String name, String surname, int idClub, String legitimationNumber, String phoneNumber, String city, String address) {
        this.idUser = idUser;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.idClub = idClub;
        this.legitimationNumber = legitimationNumber;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.address = address;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
        if (userChangedListener != null) userChangedListener.onUserChanged();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        if (userChangedListener != null) userChangedListener.onUserChanged();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (userChangedListener != null) userChangedListener.onUserChanged();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
        if (userChangedListener != null) userChangedListener.onUserChanged();
    }

    public int getIdClub() {
        return idClub;
    }

    public void setIdClub(int idClub) {
        this.idClub = idClub;
        if (userChangedListener != null) userChangedListener.onUserChanged();
    }

    public String getLegitimationNumber() {
        return legitimationNumber;
    }

    public void setLegitimationNumber(String legitimationNumber) {
        this.legitimationNumber = legitimationNumber;
        if (userChangedListener != null) userChangedListener.onUserChanged();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        if (userChangedListener != null) userChangedListener.onUserChanged();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        if (userChangedListener != null) userChangedListener.onUserChanged();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        if (userChangedListener != null) userChangedListener.onUserChanged();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(Locale.forLanguageTag("pl_PL"),"User# idUser: %s, " +
                "username: %s, " +
                "name: %s, " +
                "surname: %s, " +
                "phonenumber: %s, " +
                "legitimation: %s, " +
                "city: %s, address %s, " +
                "idClub: %d", idUser, username, name, surname, phoneNumber, legitimationNumber, city, address, idClub);
    }

    public Map<String,String> toMap() {
        Map<String, String> params = new HashMap<>();
        params.put("username", this.getUsername());
        params.put("name", this.getName());
        params.put("surname", this.getSurname());
        params.put("idClub", String.valueOf(this.getIdClub()));
        params.put("legitimationNumber", this.getLegitimationNumber());
        params.put("phoneNumber", this.getPhoneNumber());
        params.put("city", this.getCity());
        params.put("address", this.getAddress());
        return params;
    }

    public void setOnUserChangedListener(OnUserChangedListener userChangedListener) {
        this.userChangedListener = userChangedListener;
    }

    /**
     * Interface definition for a callback to be invoked when the user data changes.
     */
    public interface OnUserChangedListener {
        /**
         * Called when the user data has changed. No parameters needed.
         */
        void onUserChanged();
    }
}
