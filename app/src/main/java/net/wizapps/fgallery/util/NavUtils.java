package net.wizapps.fgallery.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import net.wizapps.fgallery.R;
import net.wizapps.fgallery.activity.GalleryActivity;
import net.wizapps.fgallery.activity.SavedActivity;
import net.wizapps.fgallery.activity.SettingsActivity;

public final class NavUtils {

    private NavUtils() {}

    public static void onNavDrawerClicked(Context context, int id) {
        switch (id) {
            case R.id.nav_saved:
                showSavedPictures(context);
                break;
            case R.id.nav_history:
                DialogUtils.historyDialog(context);
                break;
            case R.id.nav_settings:
                showSettings((AppCompatActivity) context);
                break;
            case R.id.nav_about:
                DialogUtils.aboutDialog(context);
                break;
            case R.id.nav_share:
                shareIntent((AppCompatActivity) context);
                break;
            case R.id.nav_email:
                emailIntent(context);
                break;
            case R.id.nav_feedback:
                feedbackIntent(context);
                break;
            case R.id.nav_apps:
                appsIntent(context);
                break;
        }
    }

    public static void webIntent(Context context, int textResId) {
        String url = context.getString(textResId);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        checkBeforeLaunching(context, intent);
    }

    static void checkBeforeLaunching(Context context, Intent intent) {
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, context.getString(R.string.text_no_browser), Toast.LENGTH_SHORT).show();
        }
    }

    private static void emailIntent(Context context) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + context.getString(R.string.text_email)));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.text_subject));
        emailIntent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.text_hello));
        checkBeforeLaunching(context, emailIntent);
    }

    private static void feedbackIntent(Context context) {
        String url = context.getString(R.string.app_url);
        Intent feedbackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        checkBeforeLaunching(context, feedbackIntent);
    }

    private static void shareIntent(Activity activity) {
        ShareCompat.IntentBuilder
                .from(activity)
                .setType("text/plain")
                .setChooserTitle(activity.getString(R.string.app_name))
                .setText(activity.getString(R.string.app_url))
                .startChooser();
    }

    private static void appsIntent(Context context) {
        String url = context.getString(R.string.apps_url);
        Intent appsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        checkBeforeLaunching(context, appsIntent);
    }

    private static void showSavedPictures(Context context) {
        context.startActivity(new Intent(context, SavedActivity.class));
    }

    private static void showSettings(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), SettingsActivity.class);
        activity.startActivityForResult(intent, GalleryActivity.REQUEST_CODE);
    }
}