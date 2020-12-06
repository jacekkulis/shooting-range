package com.politechnika.shootingrange.validators;

import android.util.Patterns;
import android.widget.TextView;

import com.politechnika.shootingrange.R;
import com.politechnika.shootingrange.App;

/**
 * Created by Jacek on 20.11.2017.
 */

public class PhoneNumberValidator extends TextValidator{
    public PhoneNumberValidator(TextView textView) {
        super(textView);
    }

    @Override
    public String validate(TextView textView, String text) {
        if (text.isEmpty()) {
            return App.getContext().getResources().getString(R.string.error_field_required);
        } else if (text.length() < 6) {
            return App.getContext().getResources().getString(R.string.error_short);
        } else if (text.length() > 10){
            return App.getContext().getResources().getString(R.string.error_long);
        } else if (!Patterns.PHONE.matcher(text).matches()) {
            return App.getContext().getResources().getString(R.string.error_phone_incorrect);
        }
        return null;
    }
}
