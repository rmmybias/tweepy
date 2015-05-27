package com.ayibonte.twitter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ayibonte.twitter.R;
import com.ayibonte.twitter.controllers.OTweetApplication;
import com.ayibonte.twitter.utils.ConnectionDetector;


public class SplashActivity extends Activity {
    OTweetApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        renderFullScreen();
        setContentView(R.layout.activity_splash);
        app = (OTweetApplication)getApplication();

    }


    @Override
    protected void onResume() {
        super.onResume();
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {


                    if (!app.isAuthorized()) {
                        beginAuthorizationActivity();
                    } else
                    {
                        loadMainMenuActivity();
                    }



                }

            }
        };
        thread.start();
    }
    public void loadMainMenuActivity(){
      loadActivity(MainMenuActivity.class);
    }

    public void beginAuthorizationActivity(){
        if(new ConnectionDetector(this).isConnectingToInternet()){
            loadActivity(TwitterOAuthActivity.class);
        }
        else{
            showMessage();
        }

    }

    public void showMessage(){
        new MaterialDialog.Builder(this)
                .title("unable to connect")
                .content("Please check you internet connection.")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                    }
                });
    }

    public void loadActivity(Class Class)
    {
        Intent intent = new Intent(SplashActivity.this, Class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }




    /**
     * makes window fullscreen.
     */
    public void renderFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


}



