package com.politechnika.shootingrange.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.politechnika.shootingrange.activities.MainActivity;
import com.politechnika.shootingrange.constants.UrlConstants;
import com.politechnika.shootingrange.models.Event;
import com.politechnika.shootingrange.models.MainReferee;
import com.politechnika.shootingrange.models.Rater;
import com.politechnika.shootingrange.models.TypeOfCompetition;
import com.politechnika.shootingrange.utils.EventCardAdapter;
import com.politechnika.shootingrange.utils.RecyclerItemTouchHelper;
import com.politechnika.shootingrange.utils.RequestHandler;
import com.politechnika.shootingrange.utils.RequestUtil;
import com.politechnika.shootingrange.utils.ServerCallback;
import com.politechnika.shootingrange.utils.SharedPrefManager;
import com.politechnika.shootingrange.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import com.politechnika.shootingrange.R;


public class IncomingEventsFragment extends BaseFragment implements RecyclerView.OnScrollChangeListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, MainActivity.DataUpdateListener {

    private List<Event> listEvents;

    @BindView(R.id.incoming_recyclerView)
    RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private EventCardAdapter adapter;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Nullable
    @BindView(R.id.user_recyclerView)
    RecyclerView userEventsRecyclerView;

    public IncomingEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClassTag(), "onCreate: ");
        setFragmentCreated(true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        listEvents = new ArrayList<>();


        //Adding an scroll change listener to recyclerview
        recyclerView.setOnScrollChangeListener(this);

        // Init adapter
        adapter = new EventCardAdapter(listEvents, getContext(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

//        this.setOnDataChangedListener(new BaseFragment.OnDataChangedListener() {
//            @Override
//            public void onDataChanged() {
//                Log.d(getClassTag(), "onDataChanged: ");
//
//            }
//        });

        getData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).registerDataUpdateListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) getActivity()).unregisterDataUpdateListener(this);
    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof EventCardAdapter.ViewHolder) {
            if (direction == ItemTouchHelper.RIGHT) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //alert for confirm to delete
                builder.setMessage("Are you sure to register?");    //set message

                builder.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() { //when click on DELETE
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(getClassTag(), "REGISTER");
                        showProgressBar();
                        // get the removed item name to display it in snack bar
                        final String name = listEvents.get(viewHolder.getAdapterPosition()).getCompetitionName();

                        // backup of removed item for undo purpose
                        final Event deletedItem = listEvents.get(viewHolder.getAdapterPosition());

                        // remove the item from recycler view
                        adapter.removeItem(viewHolder.getAdapterPosition());

                        RequestUtil requestUtil = new RequestUtil(getActivity(), getContext());
                        requestUtil.registerUserToEvent(deletedItem.getIdCompetition(), new ServerCallback() {
                            @Override
                            public void onSuccess(String response) {
                                hideProgressBar();
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    if (!obj.getBoolean("error")) {
                                        Log.d(getClassTag(), "registerUserToEvent:onSuccess: Error: " + obj.getString("message"));
                                        // inform parent classes that you registered and have to change views
                                        ((MainActivity) getActivity()).userEventsDataUpdated();

                                        //create snackbar// showing snack bar with Undo option
                                        Snackbar snackbar = Snackbar
                                                .make(coordinatorLayout, "You have unregistered from " + name + " competition!", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    } else {
                                        Log.d(getClassTag(), "registerUserToEvent:onSuccess: Error: " + obj.getString("message"));
                                        Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    Log.d(getClassTag(), "registerUserToEvent:onSuccess: Unknown error: " + e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onError(Exception exception) {
                                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                        return;
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(getClassTag(), "CANCEL");
                        // Animating back into original position
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        return;
                    }
                }).setCancelable(false).show();
            }
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_incoming_events;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(getClassTag(), "setUserVisibleHint: " + isVisibleToUser);
        if (isVisibleToUser && isFragmentCreated()) {
            // when switching fragments it can be used
        }
    }

    //Request to get json from server we are passing an integer here
    //This integer will used to specify the page number for the request ?page = requestcount
    //This method would return a JsonArrayRequest that will be added to the request queue
    private JsonArrayRequest getDataFromServer() {
        Log.d(getClassTag(), "getDataFromServer: ");
        showProgressBar();

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(UrlConstants.URL_GET_INCOMING_EVENTS + "?username=" + SharedPrefManager.getInstance(getContext()).getUser().getUsername(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(getClassTag(), "getDataFromServer: onResponse: ");
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
                        hideProgressBar();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(getClassTag(), "getDataFromServer: onErrorResponse: " + error.getMessage());
                        //If an error occurs that means end of the list has reached
                        ToastUtils.showShortMessage(error.getMessage(), getActivity());
                        hideProgressBar();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }

    //This method will get data from the web api
    private void getData() {
        Log.d(getClassTag(), " getData() : getting data");
        RequestHandler.getInstance(getContext()).addToRequestQueue(getDataFromServer(), getClassTag());
    }

    public void updateData(){
        Log.d(getClassTag(), "updateData");
        adapter.clearList();
        getData();
    }

    private void parseData(JSONArray array) {
        Log.d(getClassTag(), "parseData: start");
        for (int i = 0; i < array.length(); i++) {
            Event event = new Event();

            MainReferee mainReferee;
            Rater rater;
            TypeOfCompetition typeOfCompetition;
            JSONObject json = null;
            try {
                // getting object element from array
                json = array.getJSONObject(i);
                Log.d(getClassTag(), json.toString());

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("yy/M/d/ hh:mm:ss");
                Gson gson = gsonBuilder.create();

                mainReferee = gson.fromJson(json.getJSONObject("mainReferee").toString(), MainReferee.class);
                rater = gson.fromJson(json.getJSONObject("rater").toString(), Rater.class);
                typeOfCompetition = gson.fromJson(json.getJSONObject("typeOfCompetition").toString(), TypeOfCompetition.class);

                //Adding data to the event object
                event.setMainReferee(mainReferee);
                event.setRater(rater);
                event.setTypeOfCompetition(typeOfCompetition);

                event.setIdCompetition(json.getString(Event.TAG_ID_COMPETITION));
                event.setThumbnailUrl(UrlConstants.URL_THUMBNAIL + json.getString(Event.TAG_THUMBNAIL_URL));
                event.setCompetitionName(json.getString(Event.TAG_EVENT_NAME));
                event.setCompetitionDescription(json.getString(Event.TAG_EVENT_DESCRIPTION));
                event.setCompetitionDate(json.getString(Event.TAG_EVENT_DATE));
                event.setPrice(Integer.parseInt(json.getString(Event.TAG_PRICE)));
                event.setNumberOfCompetitors(Integer.parseInt(json.getString(Event.TAG_NUMBER_OF_COMPETITORS)));

                event.setAvailable(true);
                //Log.d(getClassTag(), event.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listEvents.add(event);
        }
        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }


    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        Log.d(getClassTag(), "isLastItemDisplaying: ");
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        Log.d(getClassTag(), "onScrollChange: ");
        if (isLastItemDisplaying(recyclerView)) {
            // create snackbar// showing snack bar with Undo option
//                        Snackbar snackbar = Snackbar
//                                .make(coordinatorLayout, "You have unregistered from " + name + " competition!", Snackbar.LENGTH_LONG);
//                        snackbar.setAction("UNDO", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                // undo is selected, restore the deleted item
//                                adapter.restoreItem(deletedItem, deletedIndex);
//                            }
//                        });
//                        snackbar.setActionTextColor(Color.YELLOW);
//                        snackbar.show();
        }
    }

    @Override
    public void onUserEventsDataUpdate() {
    }

    @Override
    public void onIncomingEventsDataUpdate() {
        Log.d(getClassTag(), "DataUpdateListener: onIncomingEventsDataUpdate: updating data");
        updateData();
    }
}
