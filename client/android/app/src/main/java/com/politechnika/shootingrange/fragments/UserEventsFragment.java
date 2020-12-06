package com.politechnika.shootingrange.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

import com.politechnika.shootingrange.R;
import com.politechnika.shootingrange.utils.ToastUtils;

public class UserEventsFragment extends BaseFragment implements RecyclerView.OnScrollChangeListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, MainActivity.DataUpdateListener{
    private List<Event> listEvents;

    @BindView(R.id.user_recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Nullable
    @BindView(R.id.incoming_recyclerView)
    RecyclerView incomingEventsRecyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private EventCardAdapter adapter;


    public UserEventsFragment() {
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

        //initializing our adapter
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
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

//       .setOnDataChangedListener(new BaseFragment.OnDataChangedListener() {
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
            if (direction == ItemTouchHelper.LEFT){
                final Event deletedItem = listEvents.get(viewHolder.getAdapterPosition());
                // TODO check if event is not outdated, inform user
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY);

                Date currentDate = new Date();
                Date eventDate = null;
                try {
                    eventDate = sdf.parse(deletedItem.getCompetitionDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (currentDate.before(eventDate)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //alert for confirm to delete
                    builder.setMessage("Are you sure to unregister?");    //set message

                    builder.setPositiveButton("UNREGISTER", new DialogInterface.OnClickListener() { //when click on DELETE
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(getClassTag(), "UNREGISTER");
                            showProgressBar();
                            // get the removed item name to display it in snack bar
                            final String name = listEvents.get(viewHolder.getAdapterPosition()).getCompetitionName();

                            // remove the item from recycler view
                            adapter.removeItem(viewHolder.getAdapterPosition());

                            RequestUtil requestUtil = new RequestUtil(getActivity(), getContext());
                            requestUtil.unregisterFromEvent(deletedItem.getIdCompetition(), new ServerCallback() {
                                @Override
                                public void onSuccess(String response) {
                                    hideProgressBar();
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        if (!obj.getBoolean("error")) {
                                            Log.d(getClassTag(), "registerUserToEvent:onSuccess: Error: " + obj.getString("message"));
                                            // inform parent classes that you registered and have to change views
                                            ((MainActivity) getActivity()).incomingEventsDataUpdated();
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
                else {
                    //create snackbar// showing snack bar with Undo option
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "This event is outdated. You can't delete this event.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            }
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_user_events;
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
        Log.d(getClassTag(), "getDataFromServer: start");
        //Displaying Progressbar
        showProgressBar();

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(UrlConstants.URL_GET_USER_EVENTS + "?username=" + SharedPrefManager.getInstance(getContext()).getUser().getUsername(),
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
                        ToastUtils.showShortMessage("No More Items Available", getActivity());
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
        Log.d(getClassTag(), "parseData: ");
        for (int i = 0; i < array.length(); i++) {
            Event event = new Event();

            MainReferee mainReferee;
            Rater rater;
            TypeOfCompetition typeOfCompetition;
            JSONObject json = null;
            try {
                // getting object element from array
                json = array.getJSONObject(i);
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

                event.setAvailable(false);
                Log.d(getClassTag(), event.toString());
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

        }
    }

    @Override
    public void onUserEventsDataUpdate() {
        Log.d(getClassTag(), "DataUpdateListener: onUserEventsDataUpdate: updating data");
        updateData();
    }

    @Override
    public void onIncomingEventsDataUpdate() {
    }
}
