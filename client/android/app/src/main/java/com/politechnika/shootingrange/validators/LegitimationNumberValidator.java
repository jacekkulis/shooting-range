package com.politechnika.shootingrange.validators;

import android.widget.TextView;

import com.politechnika.shootingrange.R;
import com.politechnika.shootingrange.App;

/**
 * Created by Jacek on 20.11.2017.
 */

public class LegitimationNumberValidator extends TextValidator{
    private final String LEGITIMATION_NUMBER_PATTERN = "\\d+(?:\\.\\d+)?";

    public LegitimationNumberValidator(TextView textView) {
        super(textView);
    }

    @Override
    public String validate(TextView textView, String text) {
        if (text.isEmpty()) {
            return App.getContext().getResources().getString(R.string.error_field_required);
        } else if (text.length() < 1) {
           return App.getContext().getResources().getString(R.string.error_short);
        } else if (text.length() > 10) {
            return App.getContext().getResources().getString(R.string.error_long);
        } else if (!text.matches(LEGITIMATION_NUMBER_PATTERN)){
            return App.getContext().getResources().getString(R.string.error_only_numbers);
        }
        return null;
    }
}
