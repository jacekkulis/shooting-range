package com.politechnika.shootingrange.fragments;

import android.os.Bundle;
import android.view.View;

import com.politechnika.shootingrange.R;

/**
 * Created by Jacek on 14.11.2017.
 */

public class SettingsFragment extends BaseFragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // do sth
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_settings;
    }
}