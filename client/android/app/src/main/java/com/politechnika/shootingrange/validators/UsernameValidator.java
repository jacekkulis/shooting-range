package com.politechnika.shootingrange.validators;

import android.widget.TextView;

import com.politechnika.shootingrange.App;
import com.politechnika.shootingrange.R;

/**
 * Created by Jacek on 20.11.2017.
 */

public class UsernameValidator extends TextValidator{

    public UsernameValidator(TextView textView) {
        super(textView);
    }

    @Override
    public String validate(TextView textView, String text) {
        if (text.isEmpty()) {
            return App.getContext().getResources().getString(R.string.error_field_required);
        }  else if (text.length() < 5) {
            return App.getContext().getResources().getString(R.string.error_short);
        } else if (text.length() > 25) {
            return App.getContext().getResources().getString(R.string.error_long);
        }

        return null;
    }

    public static String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
}
