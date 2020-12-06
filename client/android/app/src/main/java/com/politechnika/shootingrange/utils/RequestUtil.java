package com.politechnika.shootingrange.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.politechnika.shootingrange.constants.UrlConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacek on 15.12.2017.
 */

public class RequestUtil {

    private Activity activity;
    private Context context;
    private final int idUser;

    public RequestUtil(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        idUser = SharedPrefManager.getInstance(context).getUser().getIdUser();
    }

    public void registerUserToEvent(final String idCompetition, final ServerCallback callback) {
        HashMap<String, String> params = new HashMap<String, String>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlConstants.URL_REGISTER_TO_EVENT, new Response.Listener<String>() {
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
                params.put("userId", String.valueOf(idUser));
                params.put("competitionId", String.valueOf(idCompetition));
                return params;
            }
        };

        // Adding request to request queue
        RequestHandler.getInstance(activity).addToRequestQueue(stringRequest);
    }

    public void unregisterFromEvent(final String idCompetition, final ServerCallback callback) {
        HashMap<String, String> params = new HashMap<String, String>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlConstants.URL_UNREGISTER_FROM_EVENT, new Response.Listener<String>() {
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
                params.put("userId", String.valueOf(idUser));
                params.put("competitionId", String.valueOf(idCompetition));
                return params;
            }
        };
        // Adding request to request queue
        RequestHandler.getInstance(activity).addToRequestQueue(stringRequest);
    }
}
