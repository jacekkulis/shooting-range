package com.politechnika.shootingrange.fragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.politechnika.shootingrange.constants.UrlConstants;
import com.politechnika.shootingrange.models.Event;
import com.politechnika.shootingrange.models.MainReferee;
import com.politechnika.shootingrange.models.Rater;
import com.politechnika.shootingrange.models.TypeOfCompetition;
import com.politechnika.shootingrange.utils.EventCardAdapter;
import com.politechnika.shootingrange.utils.RequestHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import com.politechnika.shootingrange.R;

public class OutdatedEventsFragment extends BaseFragment implements RecyclerView.OnScrollChangeListener {

    private List<Event> listEvents;

    @BindView(R.id.outdated_recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private RecyclerView.LayoutManager layoutManager;
    private EventCardAdapter adapter;


    public OutdatedEventsFragment() {
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
        adapter = new EventCardAdapter(listEvents, getContext(),this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

        getData();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_outdated_events;
    }

    //Request to get json from server we are passing an integer here
    //This integer will used to specify the page number for the request ?page = requestcount
    //This method would return a JsonArrayRequest that will be added to the request queue
    private JsonArrayRequest getDataFromServer() {
        Log.d(getClassTag(), "getDataFromServer: ");
        //Displaying Progressbar
        showProgressBar();

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(UrlConstants.URL_GET_OUTDATED_EVENTS, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(getClassTag(), "getDataFromServer: Response: ");
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
                        Toast.makeText(getActivity(), "No more items Available", Toast.LENGTH_SHORT).show();
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

    private void parseData(JSONArray array) {
        Log.d(getClassTag(), "parseData: start");
        for (int i = 0; i < array.length(); i++) {
            // Init vars
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


    /**
     * This method is fired when var isVisableToUser is changed.
     * @param isVisibleToUser Describing if fragment is visable to user.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(getClassTag(), "setUserVisibleHint: " + isVisibleToUser);
        if (isVisibleToUser && isFragmentCreated()) {
            // when switching fragments it can be used
        }
    }

    /**
     * This method would check that the RecyclerView scroll has reached the bottom or not.
     * @param recyclerView RecyclerView reference.
     * @return boolean Value whether last item displaying or not.
     */
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        Log.d(getClassTag(), "isLastItemDisplaying: ");
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    /**
     * This method is fired up when screen is scrolled up or down.
     */
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        Log.d(getClassTag(), "onScrollChange: ");
        if (isLastItemDisplaying(recyclerView)) {
            // Create and display snackbar to inform user that there is no more items.
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "There is no more items.", Snackbar.LENGTH_SHORT);

            snackbar.show();
        }
    }
}
