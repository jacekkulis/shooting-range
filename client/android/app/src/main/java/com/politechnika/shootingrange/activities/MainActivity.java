package com.politechnika.shootingrange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.politechnika.shootingrange.R;
import com.politechnika.shootingrange.fragments.EditProfileFragment;
import com.politechnika.shootingrange.fragments.EventsFragment;
import com.politechnika.shootingrange.fragments.GunsFragment;
import com.politechnika.shootingrange.fragments.HomeFragment;
import com.politechnika.shootingrange.fragments.InstructorsFragment;
import com.politechnika.shootingrange.fragments.SettingsFragment;
import com.politechnika.shootingrange.utils.SharedPrefManager;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    private View navHeader;

    private Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;

    private TextView fullNameField;
    private ImageView profileImage;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_EVENTS = "events";
    private static final String TAG_GUNS = "guns";
    private static final String TAG_INSTRUCTORS = "instructors";
    private static final String TAG_EDIT_PROFILE = "edit_profile";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    private Handler mHandler;

    private List<DataUpdateListener> mListeners;

    public interface DataUpdateListener {
        void onUserEventsDataUpdate();
        void onIncomingEventsDataUpdate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);


        fullNameField = navHeader.findViewById(R.id.nav_field_full_name);
        profileImage = navHeader.findViewById(R.id.nav_image_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.navigation_labels);

        // load nav menu header data
        loadNavHeaderData();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        mListeners = new ArrayList<>();
    }

    public synchronized void registerDataUpdateListener(DataUpdateListener listener) {
        mListeners.add(listener);
    }

    public synchronized void unregisterDataUpdateListener(DataUpdateListener listener) {
        mListeners.remove(listener);
    }

    // method which informs all listeners that data has been updated
    public synchronized void userEventsDataUpdated() {
        for (DataUpdateListener listener : mListeners) {
            listener.onUserEventsDataUpdate();
        }
    }

    // method which informs all listeners that data has been updated
    public synchronized void incomingEventsDataUpdated() {
        for (DataUpdateListener listener : mListeners) {
            listener.onIncomingEventsDataUpdate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeaderData() {
        // name, website
        String fullName = SharedPrefManager.getInstance(this).getUser().getName() + " " + SharedPrefManager.getInstance(this).getUser().getSurname();
        Log.d(getClassTag(), fullName);
        fullNameField.setText(fullName);
        //profileImage.setImageBitmap();
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                return new HomeFragment();
            case 1:
                return new EventsFragment();
            case 2:
                return new GunsFragment();
            case 3:
                return new InstructorsFragment();
            case 4:
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                editProfileFragment.setOnUserChangedListener(new EditProfileFragment.OnUserChangedListener() {
                    @Override
                    public void onUserChanged() {
                        Log.d(getClassTag(), "onUserChanged: ");
                        loadNavHeaderData();
                    }
                });
                return editProfileFragment;
            case 5:
                return new SettingsFragment();
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_events:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_EVENTS;
                        break;
                    case R.id.nav_guns:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_GUNS;
                        break;
                    case R.id.nav_instructors:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_INSTRUCTORS;
                        break;
                    case R.id.nav_edit_profile:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_EDIT_PROFILE;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, RangePolicyActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }
}
