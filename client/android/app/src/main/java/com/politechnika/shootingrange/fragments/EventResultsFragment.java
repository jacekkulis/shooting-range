package com.politechnika.shootingrange.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.politechnika.shootingrange.R;

/**
 * Created by Jacek on 27.11.2017.
 */

public class EventResultsFragment  extends BaseFragment {
    public EventResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // do sth with view
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_event_results;
    }
}
