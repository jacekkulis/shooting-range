package com.politechnika.shootingrange.validators;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.politechnika.shootingrange.R;

/**
 * Created by Jacek on 25.10.2017.
 */

public abstract class TextValidator implements TextWatcher {
    private final TextView textView;

    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract String validate(TextView textView, String text);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = s.toString();
        String error = validate(textView, text);
        if (error != null){
            textView.setError(error);
        }
        else {
            textView.setError(null);
        }
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}