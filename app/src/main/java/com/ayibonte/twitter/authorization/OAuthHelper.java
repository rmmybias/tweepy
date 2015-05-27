package com.ayibonte.twitter.authorization;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ayibonte.twitter.R;
import com.ayibonte.twitter.utils.SharedPreferenceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.auth.AccessToken;

/**
 * Created by alfred on 5/13/15.
 */
public class OAuthHelper {
    private static final String APPLICATION_PREFERENCES = "app_prefs";
    private static final String AUTH_KEY = "auth_key";
    private static final String AUTH_SEKRET_KEY = "auth_secret_key";
    Context context;
    AccessToken accessToken;
    public String consumerKey;
    public String consumerSecreteKey;
    public OAuthHelper(Context context){
        this.context = context;
       loadConsumerKeys();
    }

    public void configureOAuth(Twitter twitter){

        twitter.setOAuthConsumer(consumerKey, consumerSecreteKey);

    }

    public boolean hasAccessToken()
    {
        return SharedPreferenceUtils.loadAccessToken(context) != null;
    }
    public void loadConsumerKeys()
    {
        Properties prop = new Properties();
        InputStream inputStream = this.context.getResources().openRawResource(R.raw.oauth);
        try {
            prop.load(inputStream);
            consumerKey = prop.getProperty("consumer_key", "");
            consumerSecreteKey = prop.getProperty("consumer_secret_key", "");

        } catch (IOException e) {
            throw new RuntimeException("Unable to load consumer keys.", e);
        }
    }



}
