package com.politechnika.shootingrange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.politechnika.shootingrange.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.politechnika.shootingrange.constants.UrlConstants;
import com.politechnika.shootingrange.utils.RequestHandler;
import com.politechnika.shootingrange.utils.SharedPrefManager;
import com.politechnika.shootingrange.models.User;
import com.politechnika.shootingrange.utils.ServerCallback;
import com.politechnika.shootingrange.validators.EmailValidator;
import com.politechnika.shootingrange.validators.PasswordValidator;
import com.politechnika.shootingrange.validators.RepeatPasswordValidator;
import com.politechnika.shootingrange.validators.TextValidator;

import butterknife.BindView;
import butterknife.OnClick;


public class SignUpActivity extends BaseActivity {
    @BindView(R.id.field_email) EditText emailField;
    @BindView(R.id.field_password) EditText passwordField;
    @BindView(R.id.field_repeat_password) EditText repeatPasswordField;
    @BindView(R.id.field_name) EditText nameField;
    @BindView(R.id.field_surname) EditText surnameField;
    @BindView(R.id.field_phone_number) EditText phoneNumberField;
    @BindView(R.id.field_legitimation_number) EditText legitimationNumberField;
    @BindView(R.id.field_city) EditText cityField;
    @BindView(R.id.field_address) EditText addressField;

    @BindView(R.id.fab_submit) FloatingActionButton submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_sign_up;
    }


    @Override
    protected void onStart() {
        super.onStart();
        initInputValidators();
    }

    private void initInputValidators(){
        emailField.addTextChangedListener(new EmailValidator(emailField));
        passwordField.addTextChangedListener(new PasswordValidator(passwordField));
        repeatPasswordField.addTextChangedListener(new RepeatPasswordValidator(repeatPasswordField, passwordField));
    }

    @OnClick(R.id.fab_submit)
    protected void attemptSignUp() {
        Log.d(getClassTag(), String.format("attemptSignUp"));
        if (!validateForm()) {
            Log.d(getClassTag(), String.format("attemptSignUp: validateForm failed."));
            return;
        }

        // Store values at the time of the login attempt.
        final String email = emailField.getText().toString();
        final String password = passwordField.getText().toString();
        final String name = nameField.getText().toString();
        final String surname = surnameField.getText().toString();
        final String phoneNumber = phoneNumberField.getText().toString();
        final String legitimationNumber = legitimationNumberField.getText().toString();
        final String city = cityField.getText().toString();
        final String address = addressField.getText().toString();

        resetErrors();

        User user = new User(usernameFromEmail(email), password, name, surname, 1, legitimationNumber, phoneNumber, city, address);
        signUp(email, password, user);
    }

    private void resetErrors() {
        emailField.setError(null);
        passwordField.setError(null);
        repeatPasswordField.setError(null);
        nameField.setError(null);
        surnameField.setError(null);
        phoneNumberField.setError(null);
        legitimationNumberField.setError(null);
        cityField.setError(null);
        addressField.setError(null);
    }

    private void signUp(final String email, final String password, final User user) {
        Log.d(getClassTag(), String.format("signUp: %s started signing up.", email));

        showProgressBar();

        getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(getClassTag(), "signUp:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Log.d(getClassTag(), "signUp: starting onFirebaseSignUpSuccess as " + user.getUsername());

                            onFirebaseSignUpSuccess(UrlConstants.URL_REGISTER, user, new ServerCallback() {
                                @Override
                                public void onSuccess(String response) {
                                    showProgressBar();
                                    try {
                                        JSONObject obj = new JSONObject(response);

                                        if (!obj.getBoolean("error")) {
                                            Log.d(getClassTag(), "onFirebaseSignUpSuccess:onResponse: No error - register successful.");
                                            SharedPrefManager.getInstance(getApplicationContext()).setUser(user);
                                            onSignUpSuccess();
                                        } else {
                                            Log.d(getClassTag(), "onFirebaseSignUpSuccess:onResponse: Error: " + obj.getString("message"));
                                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        Log.d(getClassTag(), "signUp:onResponse: Unknown error: " + e.getMessage());
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(Exception exception) {
                                    hideProgressBar();

                                    Toast.makeText(
                                            getApplicationContext(),
                                            exception.getMessage(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            });

                        } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                            Toast.makeText(SignUpActivity.this,
                                    "FirebaseAuthInvalidUserException", Toast.LENGTH_SHORT).show();
                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(SignUpActivity.this,
                                    "FirebaseAuthInvalidCredentialsException", Toast.LENGTH_SHORT).show();
                        } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(SignUpActivity.this,
                                    "FirebaseAuthUserCollisionException", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(getClassTag(), task.getResult().toString());
                            Toast.makeText(SignUpActivity.this, "Sign up failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    public void onFirebaseSignUpSuccess(final String URL, final User user, final ServerCallback callback) {
        Log.d(getClassTag(), "onFirebaseSignUpSuccess: " + user.getUsername() + " started signing up.");
        HashMap<String, String> params = new HashMap<String, String>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response); // call call back function here
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", user.getUsername());
                params.put("name", user.getName());
                params.put("surname", user.getSurname());
                params.put("legitimationNumber", user.getLegitimationNumber());
                params.put("phoneNumber", user.getPhoneNumber());
                params.put("city", user.getCity());
                params.put("address", user.getAddress());
                params.put("password", user.getPassword());
                return params;
            }
        };

        // Adding request to request queue
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private boolean validateForm() {
        boolean result = true;

        if (emailField.getText().toString().isEmpty()) {
            emailField.setError(getString(R.string.error_field_required));
            result = false;
        }

        if (passwordField.getText().toString().isEmpty()) {
            passwordField.setError(getString(R.string.error_field_required));
            result = false;
        }

        if (repeatPasswordField.getText().toString().isEmpty()) {
            repeatPasswordField.setError(getString(R.string.error_field_required));
            result = false;
        } else if (!repeatPasswordField.getText().toString().equals(passwordField.getText().toString())) {
            repeatPasswordField.setError(getString(R.string.error_password_incorrect));
            return false;
        }

        if (nameField.getText().toString().isEmpty()) {
            nameField.setError(getString(R.string.error_field_required));
            result = false;
        }

        if (surnameField.getText().toString().isEmpty()) {
            surnameField.setError(getString(R.string.error_field_required));
            result = false;
        }

        if (phoneNumberField.getText().toString().isEmpty()) {
            phoneNumberField.setError(getString(R.string.error_field_required));
            result = false;
        }

        if (legitimationNumberField.getText().toString().isEmpty()) {
            legitimationNumberField.setError(getString(R.string.error_field_required));
            result = false;
        }

        if (cityField.getText().toString().isEmpty()) {
            cityField.setError(getString(R.string.error_field_required));
            result = false;
        }

        if (addressField.getText().toString().isEmpty()) {
            addressField.setError(getString(R.string.error_field_required));
            result = false;
        }


        return result;
    }

    private void onSignUpSuccess() {
        Log.d(getClassTag(), "onRegisterSuccess: Finishing signInActivity, starting MainActivity.");
        // Go to MainActivity
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        finish();
    }
}
