package com.politechnika.shootingrange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.politechnika.shootingrange.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.politechnika.shootingrange.constants.UrlConstants;
import com.politechnika.shootingrange.models.MainReferee;
import com.politechnika.shootingrange.utils.RequestHandler;
import com.politechnika.shootingrange.utils.SharedPrefManager;
import com.politechnika.shootingrange.models.User;
import com.politechnika.shootingrange.utils.ServerCallback;
import com.politechnika.shootingrange.utils.ToastUtils;
import com.politechnika.shootingrange.validators.EmailValidator;
import com.politechnika.shootingrange.validators.PasswordValidator;
import com.politechnika.shootingrange.validators.UsernameValidator;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity {
    @BindView(R.id.field_email) EditText emailField;
    @BindView(R.id.field_password) EditText passwordField;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        //FirebaseMessaging.getInstance().subscribeToTopic("events");
    }

    @Override
    protected void onStart() {
        super.onStart();

        initInputValidators();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(UrlConstants.URL_LOGIN, mAuth.getCurrentUser(), new ServerCallback() {
                @Override
                public void onSuccess(String response) {
                    hideProgressBar();
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (!obj.getBoolean("error")) {
                            Log.d(getClassTag(), "singIn:onResponse: No error - login successful.");
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();

                            User user = gson.fromJson(obj.getJSONObject("user").toString(), User.class);

                            // Store user locally
                            SharedPrefManager.getInstance(getApplicationContext()).setUser(user);

                            // go to main activity
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            finish();

                        } else {
                            Log.d(getClassTag(), "singIn:onResponse: Error: " + obj.getString("message"));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Log.d(getClassTag(), "singIn:onResponse: Unknown error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Exception exception) {
                    hideProgressBar();
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void initInputValidators() {
        emailField.addTextChangedListener(new EmailValidator(emailField));
        passwordField.addTextChangedListener(new PasswordValidator(passwordField));
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_sign_in;
    }

    @OnClick(R.id.sign_in_button)
    protected void onSignInButton() {
        Log.d(getClassTag(), String.format("attemptLogin"));
        if (!validateForm()) {
            Log.d(getClassTag(), String.format("signIn: validateForm failed."));
            return;
        }

        // TODO create disabling ui
        //disableUI();

        // Store values at the time of the login attempt.
        final String username = emailField.getText().toString();
        final String password = passwordField.getText().toString();

        // Reset errors.
        emailField.setError(null);
        passwordField.setError(null);

        showProgressBar();
        signIn(username, password);
    }

//    private void disableUI() {
//        emailField.setEnabled(false);
//        passwordField.setEnabled(false);
//    }

    public void onAuthSuccess(final String URL, final FirebaseUser user, final ServerCallback callback) {
        Log.d(getClassTag(), "onFirebaseSignUpSuccess: " + UsernameValidator.usernameFromEmail(user.getEmail()) + " started signing up.");
        HashMap<String, String> params = new HashMap<String, String>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response); // call call back function here
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", UsernameValidator.usernameFromEmail(user.getEmail()));
                return params;
            }
        };

        // Adding request to request queue
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void signIn(final String email, final String password) {
        Log.d(getClassTag(), String.format("signIn: %s started signing in.", email));

        showProgressBar();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(getClassTag(), "signIn:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            onAuthSuccess(UrlConstants.URL_LOGIN, task.getResult().getUser(), new ServerCallback() {
                                @Override
                                public void onSuccess(String response) {
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        if (!obj.getBoolean("error")) {
                                            Log.d(getClassTag(), "singIn:onResponse: No error - login successful.");
                                            GsonBuilder gsonBuilder = new GsonBuilder();
                                            Gson gson = gsonBuilder.create();

                                            User user = gson.fromJson(obj.getJSONObject("user").toString(), User.class);
                                            // Store user locally
                                            SharedPrefManager.getInstance(getApplicationContext()).setUser(user);

                                            hideProgressBar();
                                            // go to main activity
                                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                            finish();
                                        } else {
                                            Log.d(getClassTag(), "singIn:onSuccess: Error: " + obj.getString("message"));
                                            ToastUtils.showError(obj.getString("message"), getApplicationContext());
                                        }
                                    } catch (JSONException e) {
                                        Log.d(getClassTag(), "singIn:onSuccess: Unknown error: " + e.getMessage());
                                        e.printStackTrace();
                                        ToastUtils.showError(e.getMessage(), getApplicationContext());
                                    }
                                }

                                @Override
                                public void onError(Exception exception) {
                                    hideProgressBar();
                                    Log.d(getClassTag(), "singIn:onError: exception " + exception.getMessage());
                                    ToastUtils.showError(exception.getMessage(), getApplicationContext());
                                }
                            });

                        } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                            Toast.makeText(SignInActivity.this,
                                    "FirebaseAuthInvalidUserException", Toast.LENGTH_SHORT).show();
                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(SignInActivity.this,
                                    "FirebaseAuthInvalidCredentialsException", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(getClassTag(), task.getResult().toString());
                            Toast.makeText(SignInActivity.this, "Email or password is not correct.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean result = true;

        final String email = emailField.getText().toString();
        final String password = passwordField.getText().toString();

        if (email.isEmpty()) {
            emailField.setError(getString(R.string.error_field_required));
            return false;
        }

        if (password.isEmpty()) {
            passwordField.setError(getString(R.string.error_field_required));
            return false;
        }

        return result;
    }

    @OnClick(R.id.sign_up_button)
    protected void onSignUpButton() {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

}
