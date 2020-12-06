package com.politechnika.shootingrange.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

import com.politechnika.shootingrange.R;

/**
 * Created by Jacek on 14.11.2017.
 */

public class EventsFragment extends BaseFragment {

    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.ic_assignment_late_white_24dp,
            R.drawable.ic_assignment_ind_white_24dp,
            R.drawable.ic_history_white_24dp
    };


    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_events;
    }

    private void setupTabIcons() {
        Log.d(getClassTag(), "setupTabIcons: ");
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(final ViewPager viewPager) {
        Log.d(getClassTag(), "setupViewPager: ");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new IncomingEventsFragment(), "Incoming");
        adapter.addFragment(new UserEventsFragment(), "Yours");
        adapter.addFragment(new OutdatedEventsFragment(), "Outdated");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private  ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            Log.d(getClassTag(), "addFragment: adding " + title);
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}