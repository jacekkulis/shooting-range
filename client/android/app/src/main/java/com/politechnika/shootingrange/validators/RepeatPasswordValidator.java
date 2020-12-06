package com.politechnika.shootingrange.validators;

import android.widget.TextView;

import com.politechnika.shootingrange.App;
import com.politechnika.shootingrange.R;

/**
 * Created by Jacek on 20.11.2017.
 */

public class RepeatPasswordValidator extends TextValidator{
    private TextView password;

    public RepeatPasswordValidator(TextView textView, TextView password) {
        super(textView);
        this.password = password;
    }

    @Override
    public String validate(TextView textView, String text) {
        if (text.isEmpty()) {
            return App.getContext().getResources().getString(R.string.error_field_required);
        } else if (!text.equals(password.getText().toString())) {
            return App.getContext().getResources().getString(R.string.error_password_incorrect);
        }
        return null;
    }
}
