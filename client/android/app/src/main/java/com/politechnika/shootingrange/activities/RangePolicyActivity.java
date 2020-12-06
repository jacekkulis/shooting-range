package com.politechnika.shootingrange.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.politechnika.shootingrange.R;

/**
 * Created by Jacek on 15.11.2017.
 */

public class RangePolicyActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            Log.d(getClassTag(), e.getMessage());
        }

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_range_policy;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

