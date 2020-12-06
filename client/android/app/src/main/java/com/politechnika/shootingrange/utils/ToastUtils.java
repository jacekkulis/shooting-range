package com.politechnika.shootingrange.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jacek on 20.11.2017.
 */

public class ToastUtils {
    public static void showError(final String message, final Context context) {
        getToast(message, context).show();
    }

    public static void showShortMessage(String message, Context context) {
        getToast(message, context, Toast.LENGTH_SHORT).show();
    }


    private static Toast getToast(String message, Context context) {
        return getToast(message, context, Toast.LENGTH_LONG);
    }

    private static Toast getToast(String message, Context context, int length) {
        return Toast.makeText(context, message, length);
    }
}
