package com.politechnika.shootingrange.validators;

import android.util.Patterns;
import android.widget.TextView;

import com.politechnika.shootingrange.App;
import com.politechnika.shootingrange.R;

import java.util.regex.Pattern;

/**
 * Created by Jacek on 20.11.2017.
 */

public class EmailValidator extends TextValidator{

    String emailPattern = "\"[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+\"";

    public EmailValidator(TextView textView) {
        super(textView);
    }

    @Override
    public String validate(TextView textView, String text) {
        if (text.isEmpty()) {
            return App.getContext().getResources().getString(R.string.error_field_required);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            return App.getContext().getResources().getString(R.string.error_email_incorrect);
        } else if (text.length() < 4){
            return App.getContext().getResources().getString(R.string.error_short);
        }
        return null;
    }
}
