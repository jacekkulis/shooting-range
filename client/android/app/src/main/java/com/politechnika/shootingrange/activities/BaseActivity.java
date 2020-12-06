package com.politechnika.shootingrange.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.politechnika.shootingrange.R;
import com.politechnika.shootingrange.utils.ProgressBarUtil;
import com.politechnika.shootingrange.utils.SharedPrefManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String mClassTag;
    private Unbinder unbinder;
    private ProgressBarUtil progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
        setClassTag(this.getClass().getSimpleName());
        mAuth = FirebaseAuth.getInstance();
        unbinder = ButterKnife.bind(this);

        RelativeLayout rootLayout = findViewById(R.id.root_layout);
        progressBar = new ProgressBarUtil(rootLayout, this, getClassTag());

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            Log.d(getClassTag(), e.getMessage());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        progressBar.hideProgressBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * Every activity has to set content view. We have added this method to avoid duplicate all
     * setContentView() code in every fragment. You only have to return the layout to
     * set content view in this method when extends BaseActivity.
     */
    protected abstract int getActivityLayout();

    public FirebaseAuth getFirebaseAuth() {
        return mAuth;
    }

    public void showProgressBar() {
        progressBar.showProgressBar();
    }

    public void hideProgressBar() {
        progressBar.hideProgressBar();
    }

    public String getClassTag() {
        return mClassTag;
    }

    public void setClassTag(String classTag) {
        this.mClassTag = classTag;
    }


    // this works
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            SharedPrefManager.getInstance(this).logout();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return true;
        }  else if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}