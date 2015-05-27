package com.ayibonte.twitter.activities;

import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.ayibonte.twitter.R;
import com.ayibonte.twitter.controllers.OTweetApplication;
import com.ayibonte.twitter.fragments.HomeTimelineFragment;
import com.ayibonte.twitter.fragments.PostTweetFragment;
import com.ayibonte.twitter.utils.ConnectionDetector;
import com.ayibonte.twitter.utils.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 *This shows the drawer with all the main_menu on it.
 */
public class MainMenuActivity extends MaterialNavigationDrawer implements MaterialAccountListener {
    public final static String CURRENT_FRAGMENT = "CURRENT_FRAGMENT";
    public static FragmentManager fragmentManager;
    private final static String TAG = MainMenuActivity.class.getSimpleName();
    Context context;
    OTweetApplication app ;




    public void setPhoto(Twitter twitter){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);//setting policy.
            User user = twitter.showUser(twitter.getId());
            setUsername(user.getName());

            try {
                URL url = new URL(user.getProfileImageURL());
                try {
                    setFirstAccountPhoto(Drawable.createFromStream(
                            url.openStream(), url.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }




        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init(Bundle savedInstanceState) {
        app = (OTweetApplication)getApplication();
        Twitter twitter = app.getTwitter();

//        userPhoto
        if(new ConnectionDetector(this).isConnectingToInternet()){
            setPhoto(twitter);
        }

        //Creation of the list items is here
        setDrawerHeaderImage(R.drawable.ic_user_background);


        // set listener
        this.setAccountListener(this);
        this.setDefaultSectionLoaded(
                0);

        // create sections
        this.addSection(newSection(getResources().getString(R.string.home_timeline),R.drawable.home, new HomeTimelineFragment()));
        this.addSection(newSection(getResources().getString(R.string.post),R.drawable.dialog, new PostTweetFragment()));
        // create bottom section
        this.addBottomSection(
                newSection(getResources().getString(R.string.settings),
                        R.drawable.ic_settings_black_24dp,
                new Intent(this,SettingsActivity.class) )
        );

        fragmentManager = getSupportFragmentManager();
        context = getApplicationContext();






    }



//        @Override
//        protected void onResume() {
//            super.onResume();
//            Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
//            setDefaultSectionLoaded(SharedPreferenceUtils.getCurrentFragmentPosition(this));
//        }





    @Override
    public void onAccountOpening(MaterialAccount materialAccount) {

    }

    @Override
    public void onChangeAccount(MaterialAccount materialAccount) {

    }





}
