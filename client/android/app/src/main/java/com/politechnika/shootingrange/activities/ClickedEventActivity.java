package com.politechnika.shootingrange.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.politechnika.shootingrange.R;
import com.politechnika.shootingrange.fragments.EventDetailsFragment;
import com.politechnika.shootingrange.fragments.EventResultsFragment;
import com.politechnika.shootingrange.models.Event;
import com.politechnika.shootingrange.utils.RequestHandler;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by Jacek on 16.11.2017.
 */

public class ClickedEventActivity extends BaseActivity {
    @BindView(R.id.image_header_event)
    NetworkImageView eventImage;
    @BindView(R.id.field_header_event_title)
    TextView eventTitle;
    @BindView(R.id.field_header_event_price)
    TextView eventPrice;
    @BindView(R.id.field_header_event_participants)
    TextView eventParticipants;

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.clicked_event_viewpager)
    ViewPager viewPager;

    private Event event;

    private int[] tabIcons = {
            R.drawable.ic_details_white_24dp,
            R.drawable.ic_result_white_24dp
    };

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        event = (Event)getIntent().getSerializableExtra(Event.TAG_CLICKED_EVENT);

        Log.d(getClassTag(), "onCreate: Loading image from url");
        imageLoader = RequestHandler.getInstance(getApplicationContext()).getImageLoader();
        imageLoader.get(event.getThumbnailUrl(), ImageLoader.getImageListener(eventImage, R.drawable.ic_default_image, android.R.drawable.ic_dialog_alert));
        eventImage.setImageUrl(event.getThumbnailUrl(), imageLoader);

        eventTitle.setText(event.getCompetitionName());
        eventPrice.setText(String.valueOf(event.getPrice()));

        // TODO Have to check number of available tickets
        //eventParticipants.setText(event.getNumberOfCompetitors());

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }


    @Override
    protected int getActivityLayout() {
        return R.layout.activity_event_clicked;
    }

    private void setupTabIcons() {
        Log.d(getClassTag(), "setupTabIcons: ");
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(final ViewPager viewPager) {
        Log.d(getClassTag(), "setupViewPager: ");
        ClickedEventActivity.ClickedEventViewPagerAdapter adapter = new ClickedEventActivity.ClickedEventViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EventDetailsFragment(), "Details");
        adapter.addFragment(new EventResultsFragment(), "Results");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class ClickedEventViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private ClickedEventViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            Log.d(getClassTag(), "getCount: " + mFragmentList.size());
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            Log.d(getClassTag(), "addFragment: adding " + title);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Event.TAG_CLICKED_EVENT, event);
            fragment.setArguments(bundle);

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
