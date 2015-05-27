package com.ayibonte.twitter.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ayibonte.twitter.R;
import com.ayibonte.twitter.activities.MainMenuActivity;
import com.ayibonte.twitter.controllers.OTweetApplication;
import com.ayibonte.twitter.tasks.PostTweetAsyncTask;

import twitter4j.Status;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class PostTweetFragment extends FragmentMain implements View.OnClickListener, PostTweetAsyncTask.PostTweetResponder{

    private OTweetApplication app;
    private TextView counterText;
    private EditText tweetContent;
    private ProgressDialog progressDialog;
    Button post_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.post_tweet_fragment, container, false);
        app = (OTweetApplication)getActivity().getApplication();
        initializeUIStuff(rootView);

        return rootView;
    }


    public void initializeUIStuff(View view){
        counterText = (TextView)view.findViewById(R.id.counter_text);
        post_button = (Button)view.findViewById(R.id.post_button);
        post_button.setOnClickListener(this);
        tweetContent = (EditText)view.findViewById(R.id.tweet_contents);
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

    private void postValidTweetOrWarn() {
        String postText = tweetContent.getText().toString();
        int postLength = postText.length();
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
     *
     * @param title title
     * @param msg message
     */
    public void showMaterialDialog(int title, int  msg){
        new MaterialDialog.Builder(getActivity())
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
