package com.ayibonte.twitter.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ayibonte.twitter.R;
import com.ayibonte.twitter.controllers.OTweetApplication;
import com.ayibonte.twitter.tasks.PostTweetAsyncTask;

import twitter4j.Status;


/**
 * Created by alfred on 6/3/15.
 */
public class PostTweetActivity extends ActionBarActivity
    implements View.OnClickListener, PostTweetAsyncTask.PostTweetResponder {

    private OTweetApplication app;
    private TextView counterText;
    private EditText tweetContent;
    private ProgressDialog progressDialog;
    Button post_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_tweet_fragment);
        app = (OTweetApplication)getApplication();
        initializeUIStuff();

    }

    public void initializeUIStuff(){
        counterText = (TextView)findViewById(R.id.counter_text);
        post_button = (Button)findViewById(R.id.post_button);
        post_button.setOnClickListener(this);
        tweetContent = (EditText)findViewById(R.id.tweet_contents);
        tweetContent.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable text) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int charsLeft = 140 - s.length();
                counterText.setText(String.valueOf(charsLeft));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.post_button:
                postButtonClicked();
                break;
        }
    }

    public void tweetPosted(Status tweet) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(), R.string.tweet_posted, Toast.LENGTH_LONG).show();

    }

    public void tweetPosting() {
        progressDialog = ProgressDialog.show(
                getActivity(),
                getResources().getString(R.string.posting_title),
                getResources().getString(R.string.posting_description)
        );
    }

    private Activity getActivity(){
        return this;
    }

    private void postValidTweetOrWarn() {
        String postText = tweetContent.getText().toString();
        int postLength = postText.length();
        // TODO: remove validation and add TextUtil

        if (140 < postLength) {
            showMaterialDialog(R.string.too_many_characters, R.string.too_many_characters_description);
        } else if (0 == postLength) {
            showMaterialDialog(R.string.tweet_is_blank,R.string.blank_tweet_description );
        } else {
            new PostTweetAsyncTask(this, app.getTwitter()).execute(postText);
        }
    }

    // called when post button on view is clicked
    public void postButtonClicked() {
        postValidTweetOrWarn();
    }

    /**
     * @param title title
     * @param msg message
     */
    public void showMaterialDialog(int title, int msg){
        new MaterialDialog.Builder(this)
            .title(getResources().getString(title))
            .content( getResources().getString(msg))
            .callback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    dialog.dismiss();

                }
            })
            .show();
    }
}
