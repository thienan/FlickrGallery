package com.choliy.igor.galleryforflickr.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;

import com.choliy.igor.galleryforflickr.FlickrConstants;
import com.choliy.igor.galleryforflickr.R;
import com.choliy.igor.galleryforflickr.util.PrefUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BroadcastActivity {

    private String mGridValue;
    private String mStyleValue;
    private String mPictureValue;
    private String mAnimationValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            String[] settings = PrefUtils.getSettings(this);
            mGridValue = settings[FlickrConstants.INT_ZERO];
            mStyleValue = settings[FlickrConstants.INT_ONE];
            mPictureValue = settings[FlickrConstants.INT_TWO];
            mAnimationValue = settings[FlickrConstants.INT_THREE];
        } else {
            mGridValue = savedInstanceState.getString(getString(R.string.pref_key_grid));
            mStyleValue = savedInstanceState.getString(getString(R.string.pref_key_style));
            mPictureValue = savedInstanceState.getString(getString(R.string.pref_key_picture));
            mAnimationValue = savedInstanceState.getString(getString(R.string.pref_key_animation));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.pref_key_grid), mGridValue);
        outState.putString(getString(R.string.pref_key_style), mStyleValue);
        outState.putString(getString(R.string.pref_key_picture), mPictureValue);
        outState.putString(getString(R.string.pref_key_animation), mAnimationValue);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        checkAndFinish();
    }

    @OnClick(R.id.ic_return_settings)
    public void onReturnClick() {
        checkAndFinish();
    }

    private void checkAndFinish() {
        String[] settings = PrefUtils.getSettings(this);
        String gridValue = settings[FlickrConstants.INT_ZERO];
        String styleValue = settings[FlickrConstants.INT_ONE];
        String pictureValue = settings[FlickrConstants.INT_TWO];
        String animationValue = settings[FlickrConstants.INT_THREE];

        boolean gridEquals = gridValue.equals(mGridValue);
        boolean styleEquals = styleValue.equals(mStyleValue);
        boolean pictureEquals = pictureValue.equals(mPictureValue);
        boolean animationEquals = animationValue.equals(mAnimationValue);

        if (!gridEquals || !styleEquals || !pictureEquals || !animationEquals) {
            setResult(RESULT_OK);
            finish();
        } else {
            NavUtils.navigateUpFromSameTask(this);
            finish();
        }
    }
}