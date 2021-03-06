package net.wizapps.fgallery.activity;

import android.os.Bundle;

import net.wizapps.fgallery.R;
import net.wizapps.fgallery.base.BaseActivity;
import net.wizapps.fgallery.tool.Constants;
import net.wizapps.fgallery.tool.Events;
import net.wizapps.fgallery.util.PrefUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    private String mGridValue;
    private String mStyleValue;
    private String mAnimationValue;

    @Override
    protected int layoutRes() {
        return R.layout.activity_settings;
    }

    @Override
    protected void setUi(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            String[] settings = PrefUtils.getSettings(this);
            mGridValue = settings[Constants.ZERO];
            mStyleValue = settings[Constants.ONE];
            mAnimationValue = settings[Constants.TWO];
        } else {
            mGridValue = savedInstanceState.getString(getString(R.string.pref_key_grid));
            mStyleValue = savedInstanceState.getString(getString(R.string.pref_key_style));
            mAnimationValue = savedInstanceState.getString(getString(R.string.pref_key_animation));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.pref_key_grid), mGridValue);
        outState.putString(getString(R.string.pref_key_style), mStyleValue);
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
        String gridValue = settings[Constants.ZERO];
        String styleValue = settings[Constants.ONE];
        String animationValue = settings[Constants.TWO];

        boolean gridEquals = gridValue.equals(mGridValue);
        boolean styleEquals = styleValue.equals(mStyleValue);
        boolean animationEquals = animationValue.equals(mAnimationValue);

        if (!gridEquals || !styleEquals) setResult(RESULT_OK);

        if (!animationEquals) {
            boolean isAnimationOn = PrefUtils.getAnimationSettings(this);
            EventBus.getDefault().postSticky(new Events.AnimPrefEvent(isAnimationOn));
        }

        finish();
    }
}