package com.politechnika.shootingrange.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.politechnika.shootingrange.constants.UrlConstants;
import com.politechnika.shootingrange.models.User;
import com.politechnika.shootingrange.utils.RequestHandler;
import com.politechnika.shootingrange.utils.ServerCallback;
import com.politechnika.shootingrange.utils.SharedPrefManager;
import com.politechnika.shootingrange.utils.ToastUtils;
import com.politechnika.shootingrange.validators.LegitimationNumberValidator;
import com.politechnika.shootingrange.validators.NameValidator;
import com.politechnika.shootingrange.validators.PhoneNumberValidator;
import com.politechnika.shootingrange.validators.SurnameValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

import com.politechnika.shootingrange.R;

/**
 * Created by Jacek on 14.11.2017.
 */

public class EditProfileFragment extends BaseFragment {

    @BindView(R.id.field_name)
    EditText nameField;
    @BindView(R.id.field_surname)
    EditText surnameField;
    @BindView(R.id.field_phone_number)
    EditText phoneNumberField;
    @BindView(R.id.field_legitimation_number)
    EditText legitimationNumberField;
    @BindView(R.id.field_city)
    EditText cityField;
    @BindView(R.id.field_address)
    EditText addressField;
    @BindView(R.id.spinner_club)
    Spinner clubSpinner;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private String idClub;

    private ArrayAdapter<String> clubAdapter;

    private List<String> clubNames;
    private List<String> clubIds;

    @BindView(R.id.fab_submit)
    FloatingActionButton submitButton;

    private User user;

    /**
     * This variable represents OnUserChangedListener passed in by the owning object.
     *
             */
    private OnUserChangedListener userChangedListener;

    public void setOnUserChangedListener(OnUserChangedListener listener) {
        this.userChangedListener = listener;
    }


    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClassTag(), "onCreate:");

        // init arrays
        clubNames = new ArrayList<>();
        clubIds = new ArrayList<>();

        user = SharedPrefManager.getInstance(getContext()).getUser();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // values from shared preferences
        nameField.setText(user.getName());
        surnameField.setText(user.getSurname());
        legitimationNumberField.setText(user.getLegitimationNumber());
        phoneNumberField.setText(user.getPhoneNumber());
        cityField.setText(user.getCity());
        addressField.setText(user.getAddress());

        idClub = String.valueOf(user.getIdClub());

        initAdapter();
        initInputValidators();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_edit_profile;
    }

    private void initAdapter() {
        showProgressBar();
        this.getClubListFromServer(UrlConstants.URL_GETCLUBLIST, new ServerCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        createClubSpinnerAdapter(obj.getJSONArray("clubList"));
                    } else {
                        ToastUtils.showShortMessage(obj.getString("message"), getContext());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception exception) {
                ToastUtils.showError(exception.getMessage(), getContext());
            }
        });

    }

    private void initInputValidators() {
        nameField.addTextChangedListener(new NameValidator(nameField));
        surnameField.addTextChangedListener(new SurnameValidator(surnameField));
        phoneNumberField.addTextChangedListener(new PhoneNumberValidator(phoneNumberField));
        legitimationNumberField.addTextChangedListener(new LegitimationNumberValidator(legitimationNumberField));
    }

    private void createClubSpinnerAdapter(JSONArray clubList) {
        try {
            for (int i = 0; i < clubList.length(); i++) {
                Log.d(getClassTag(), clubList.getString(i) + "clubId: " + (i + 1));
                String clubName = clubList.getString(i);
                clubNames.add(clubName);
                clubIds.add(String.valueOf(i + 1));
            }

            clubAdapter = new ArrayAdapter<String>(getContext(),
                    R.layout.spinner_item, clubNames);

            clubSpinner.setAdapter(clubAdapter);
            if (Integer.valueOf(idClub) == 1) {
                clubSpinner.setSelection(clubAdapter.getPosition("Niezrzeszony"));
            } else {
                clubSpinner.setSelection(clubAdapter.getPosition(clubNames.get(Integer.valueOf(idClub) - 1)));
            }

            hideProgressBar();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getClubListFromServer(final String URL, final ServerCallback callback) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response); // call call back function here
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressBar();
                        ToastUtils.showError(error.getMessage(), getContext());
                    }
                });

        // Adding request to request queue
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    @OnClick(R.id.fab_submit)
    public void attemptEditProfile() {
        showProgressBar();
        Log.d(getClassTag(), String.format("attemptEditProfile"));
        if (!validateForm()) {
            Log.d(getClassTag(), String.format("attemptEditProfile: validateForm failed."));
            return;
        }

        resetErrors();

        user.setName(nameField.getText().toString());
        user.setSurname(surnameField.getText().toString());
        user.setIdClub((int)clubSpinner.getSelectedItemId() + 1);
        user.setLegitimationNumber(legitimationNumberField.getText().toString());
        user.setPhoneNumber(phoneNumberField.getText().toString());
        user.setCity(cityField.getText().toString());
        user.setAddress(addressField.getText().toString());

        // hiding keyboard
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        onEditProfile(UrlConstants.URL_EDIT_PROFILE, user, new ServerCallback() {
            @Override
            public void onSuccess(String response) {
                hideProgressBar();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        Log.d(getClassTag(), "attemptEditProfile:onResponse: No error - edit profile successful.");
                        SharedPrefManager.getInstance(getContext()).setUser(user);

                        if (userChangedListener != null)
                            userChangedListener.onUserChanged();

                        // create snackbar
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Editing profile went successful", Snackbar.LENGTH_SHORT);

                        snackbar.show();
                    } else {
                        Log.d(getClassTag(), "attemptEditProfile:onResponse: Error: " + obj.getString("message"));
                        ToastUtils.showShortMessage(obj.getString("message"), getContext());
                    }
                } catch (JSONException e) {
                    Log.d(getClassTag(), "attemptEditProfile:onResponse: Unknown error: " + e.getMessage());
                    e.printStackTrace();
                    ToastUtils.showError(e.getMessage(), getContext());
                }
            }

            @Override
            public void onError(Exception exception) {
                hideProgressBar();
                ToastUtils.showError(exception.getMessage(), getContext());
            }
        });

    }

    private void resetErrors() {
        nameField.setError(null);
        surnameField.setError(null);
        phoneNumberField.setError(null);
        legitimationNumberField.setError(null);
        cityField.setError(null);
        addressField.setError(null);
    }

    public void onEditProfile(final String URL, final User user, final ServerCallback callback) {
        Log.d(getClassTag(), "onEditProfile: " + user.getUsername() + " started edit profile process.");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response); // call call back function here
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError exception) {
                        hideProgressBar();
                        ToastUtils.showError(exception.getMessage(), getContext());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return user.toMap();
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private boolean validateForm() {
        boolean result = true;

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

    /**
     * Interface definition for a callback to be invoked when the user data changes.
     */
    public interface OnUserChangedListener {
        /**
         * Called when the user data has changed. No parameters needed.
         */
        void onUserChanged();
    }
}