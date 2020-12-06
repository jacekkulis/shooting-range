package com.politechnika.shootingrange.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.politechnika.shootingrange.R;
import com.politechnika.shootingrange.constants.UrlConstants;
import com.politechnika.shootingrange.models.Event;

import org.w3c.dom.Text;

import butterknife.BindView;

/**
 * Created by Jacek on 27.11.2017.
 */

public class EventDetailsFragment  extends BaseFragment {
    private Event event;

    @BindView(R.id.field_details_event_date)
    TextView eventDate;
    @BindView(R.id.field_details_event_description)
    TextView eventDescription;
    @BindView(R.id.field_details_main_referee)
    TextView mainReferee;
    @BindView(R.id.field_details_type_of_competition)
    TextView typeOfCompetition;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            event = (Event) getArguments().getSerializable(Event.TAG_CLICKED_EVENT);
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventDate.setText(event.getCompetitionDate());
        eventDescription.setText(event.getCompetitionDescription());
        mainReferee.setText(event.getMainReferee().getFullname());
        typeOfCompetition.setText(event.getTypeOfCompetition().getFullName());

        Log.d(getClassTag(), "onViewCreated: added data to textViews");
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_event_details;
    }
}
