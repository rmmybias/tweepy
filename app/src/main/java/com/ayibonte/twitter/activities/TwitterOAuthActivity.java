package com.ayibonte.twitter.activities;

import android.app.Activity;
import twitter4j.auth.AccessToken;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ayibonte.twitter.authorization.OAuthHelper;
import com.ayibonte.twitter.controllers.OTweetApplication;
import com.ayibonte.twitter.utils.SharedPreferenceUtils;
import com.ayibonte.twitter.views.TwitterOAuthView;


public class TwitterOAuthActivity extends Activity implements TwitterOAuthView.Listener
{
    OAuthHelper helper;
    // Replace values of the parameters below with your own.
    private static final String CALLBACK_URL = "http://gretest.herokuapp.com/words";
    private static final boolean DUMMY_CALLBACK_URL = true;


    private TwitterOAuthView view;
    private boolean oauthStarted;
    OTweetApplication app;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        helper = new OAuthHelper(this);

        app = (OTweetApplication)getApplication();


        // Create an instance of TwitterOAuthView.
        view = new TwitterOAuthView(this);
        view.setDebugEnabled(true);

        setContentView(view);

        oauthStarted = false;
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        if (oauthStarted)
        {
//            Log.e("Auth", "auth started");
            return;
        }

        oauthStarted = true;

        // Start Twitter OAuth process. Its result will be notified via
        // TwitterOAuthView.Listener interface.
        view.start(helper.consumerKey, helper.consumerSecreteKey, CALLBACK_URL, DUMMY_CALLBACK_URL, this);
    }


    public void onSuccess(TwitterOAuthView view, AccessToken accessToken)
    {
        // The application has been authorized and an access token
        // has been obtained successfully. Save the access token
        // for later use.
        showMessage("Authorized by " + accessToken.getScreenName());
        app.setAccessToken(accessToken);

        SharedPreferenceUtils.storeAccessToken(this, accessToken);
//
        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }


    public void onFailure(TwitterOAuthView view, TwitterOAuthView.Result result)
    {
        // Failed to get an access token.
        showMessage("Failed due to " + result);

    }


    private void showMessage(String message)
    {
        // Show a popup message.
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        new MaterialDialog.Builder(this).title(message)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                    }
                });
        finish();
    }
}
