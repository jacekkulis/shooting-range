package com.politechnika.shootingrange.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by Jacek on 23.11.2017.
 */

public class ProgressBarUtil {
    private final ProgressBar mProgressBar;
    private final String classTag;
    private int progressBarWidth;
    private int progressBarHeight;

    public ProgressBarUtil(RelativeLayout relativeLayout, Context context, String classTag) {
        this.progressBarWidth = 120;
        this.progressBarHeight = 120;
        this.classTag = classTag;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(progressBarWidth, progressBarHeight);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);

        mProgressBar = new ProgressBar(context);
        mProgressBar.setVisibility(View.GONE);
        mProgressBar.setIndeterminate(true);

        if (relativeLayout != null){
            Log.d(classTag, "ProgressBar: constructor passed");
            relativeLayout.addView(mProgressBar, params);
        }
        else {
            Log.d(classTag, "ProgressBar: constructor failed");
        }
    }

    public void showProgressBar() {
        if (mProgressBar != null) {
            Log.d(classTag, "showProgressBar: showing");
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (mProgressBar != null && (mProgressBar.getVisibility() == View.VISIBLE)) {
            Log.d(classTag, "showProgressBar: hiding");
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
