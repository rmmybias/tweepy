package com.ayibonte.twitter.controllers;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.ayibonte.twitter.authorization.OAuthHelper;
import com.ayibonte.twitter.utils.SharedPreferenceUtils;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


/**
 * Created by alfred on 5/13/15.
 */
public class OTweetApplication extends Application{
    private OAuthHelper oAuthHelper;
    RequestToken currentRequestToken;
    Twitter twitter;

    @Override
    public void onCreate() {
        super.onCreate();
        oAuthHelper = new OAuthHelper(this);
        twitter = new TwitterFactory().getInstance();
        oAuthHelper.configureOAuth(twitter);
    }

    public Twitter getTwitter() {
        setAccessToken(SharedPreferenceUtils.loadAccessToken(this));
        return twitter;
    }

    public boolean isAuthorized() {
        return oAuthHelper.hasAccessToken();
    }

    public void setAccessToken(AccessToken accessToken){
        twitter.setOAuthAccessToken(accessToken);
    }
}
