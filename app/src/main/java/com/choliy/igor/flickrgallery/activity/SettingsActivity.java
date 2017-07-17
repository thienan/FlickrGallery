package com.choliy.igor.flickrgallery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.choliy.igor.flickrgallery.FlickrConstants;
import com.choliy.igor.flickrgallery.R;
import com.choliy.igor.flickrgallery.util.PrefUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SettingsActivity extends AppCompatActivity {

    private String mGridValue;
    private String mStyleValue;
    private String mPictureValue;
    private String mAnimationValue;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null) {
            String[] settings = PrefUtils.getSettings(this);
            mGridValue = settings[0];
            mStyleValue = settings[1];
            mPictureValue = settings[2];
            mAnimationValue = settings[3];
        } else {
            mGridValue = savedInstanceState.getString(FlickrConstants.GRID_KEY);
            mStyleValue = savedInstanceState.getString(FlickrConstants.STYLE_KEY);
            mPictureValue = savedInstanceState.getString(FlickrConstants.PICTURE_KEY);
            mAnimationValue = savedInstanceState.getString(FlickrConstants.ANIMATION_KEY);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(FlickrConstants.GRID_KEY, mGridValue);
        outState.putString(FlickrConstants.STYLE_KEY, mStyleValue);
        outState.putString(FlickrConstants.PICTURE_KEY, mPictureValue);
        outState.putString(FlickrConstants.ANIMATION_KEY, mAnimationValue);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        checkAndFinish();
    }

    public void onReturnClick(View view) {
        checkAndFinish();
    }

    private void checkAndFinish() {
        String[] settings = PrefUtils.getSettings(this);
        String gridValue = settings[0];
        String styleValue = settings[1];
        String pictureValue = settings[2];
        String animationValue = settings[3];

        boolean gridEquals = gridValue.equals(mGridValue);
        boolean styleEquals = styleValue.equals(mStyleValue);
        boolean pictureEquals = pictureValue.equals(mPictureValue);
        boolean animationEquals = animationValue.equals(mAnimationValue);

        if (!gridEquals || !styleEquals || !pictureEquals || !animationEquals) {
            setResult(RESULT_OK, new Intent());
            finish();
        } else {
            NavUtils.navigateUpFromSameTask(this);
            finish();
        }
    }
}