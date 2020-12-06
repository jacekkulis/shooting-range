package com.politechnika.shootingrange.utils;


/**
 * Created by Jacek on 03.11.2017.
 */

public interface ServerCallback {
    void onSuccess(String result);

    void onError(Exception exception);
}
