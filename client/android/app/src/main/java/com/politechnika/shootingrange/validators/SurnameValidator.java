package com.politechnika.shootingrange.validators;

import android.widget.TextView;

import com.politechnika.shootingrange.R;
import com.politechnika.shootingrange.App;

/**
 * Created by Jacek on 20.11.2017.
 */

public class SurnameValidator extends TextValidator{

    public SurnameValidator(TextView textView) {
        super(textView);
    }

    @Override
    public String validate(TextView textView, String text) {
        if (text.isEmpty()) {
            return App.getContext().getResources().getString(R.string.error_field_required);
        } else if (text.length() < 3) {
            return App.getContext().getResources().getString(R.string.error_short);
        } else if (text.length() > 15){
            return App.getContext().getResources().getString(R.string.error_long);
        }

        return null;
    }
}
