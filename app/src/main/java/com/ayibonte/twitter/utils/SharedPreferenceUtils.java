package com.ayibonte.twitter.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ayibonte.twitter.activities.MainMenuActivity;
import com.ayibonte.twitter.fragments.FragmentMain;

import twitter4j.auth.AccessToken;

/**
 * Created by alfred on 3/11/15.
 */
public class SharedPreferenceUtils {
    private static final String BRANCH="BRANCH";
    private static final String TEMP = "TEMP";
    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String AVATAR = "AVATAR";
    private static final String AUTH_KEY = "TwitterAUTH_KEY";
    private static final String AUTH_SEKRET_KEY = "TwitterSCRETE_KEY";

    public static SharedPreferences getGCMPreferences(Context context){
      return  context.getSharedPreferences(
                context.getPackageName()+"_preferences", context.MODE_PRIVATE);
    }


    public static void storeAccessToken(Context context, AccessToken accessToken){
        SharedPreferences pref = context.getSharedPreferences(context.getPackageName()+"_preferences", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(AUTH_KEY, accessToken.getToken());
        editor.putString(AUTH_SEKRET_KEY, accessToken.getTokenSecret());
        editor.commit();
    }

    public static AccessToken loadAccessToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences(context.getPackageName()+"_preferences", 0);
        String token = pref.getString(AUTH_KEY, null);
        String tokenSecret = pref.getString(AUTH_SEKRET_KEY, null);
        if (null != token && null != tokenSecret) {
            return new AccessToken(token, tokenSecret);
        } else {
            return null;
        }
    }

    public static void writeFragmentNumberToReturnToAfterThisSession(Context context, int number){
        SharedPreferences pref = context.getSharedPreferences(context.getPackageName()+"_preferences", 0);
        pref.edit().putInt(
                MainMenuActivity.CURRENT_FRAGMENT, number).apply();
    }

    public static int getCurrentFragmentPosition(Context context){
        SharedPreferences pref = context.getSharedPreferences(context.getPackageName()+"_preferences", 0);
        return pref.getInt(MainMenuActivity.CURRENT_FRAGMENT, FragmentMain.TIMELINE);
    }


}
