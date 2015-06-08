package com.ayibonte.twitter.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ayibonte.twitter.R;
import com.ayibonte.twitter.activities.MainMenuActivity;
import com.ayibonte.twitter.activities.PostTweetActivity;
import com.ayibonte.twitter.activities.StatusDetailActivity;
import com.ayibonte.twitter.adapters.StatusListAdapter;
import com.ayibonte.twitter.controllers.OTweetApplication;
import com.ayibonte.twitter.tasks.LoadMoreAsyncTask;
import com.ayibonte.twitter.utils.ConnectionDetector;
import com.ayibonte.twitter.utils.SharedPreferenceUtils;
import com.ayibonte.twitter.views.LoadMoreListItem;
import com.melnykov.fab.FloatingActionButton;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;

import java.util.ArrayList;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class HomeTimelineFragment extends FragmentMain implements LoadMoreAsyncTask.LoadMoreStatusesResponder, AdapterView.OnItemClickListener {


    final private Handler handler = new Handler();
     OTweetApplication app;
    private Twitter twitter;
     LoadMoreListItem headerView;
     LoadMoreListItem footerView;
    private StatusListAdapter adapter;
    protected ProgressDialog progressDialog;
    private ListView listView;
    FloatingActionButton fab;



    private Runnable finishedLoadingListTask = new Runnable() {
        public void run() {
            finishedLoadingList();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_timeline_fragment, container, false);
        app = (OTweetApplication)getActivity().getApplication();
        twitter = app.getTwitter();
        initializeUIStuff(rootView);
        if(new ConnectionDetector(getActivity()).isConnectingToInternet())
        {
                loadTimelineIfNotLoaded();
        }else
        {
            displayMessage("Error connecting...", "You are not connected to the internet.");

        }


        return rootView;
    }

    public void loadTimelineIfNotLoaded(){

            if (null == ( listView.getAdapter()) )
            {


                progressDialog = ProgressDialog.show(
                        getActivity(),
                        getResources().getString(R.string.loading_title),
                        getResources().getString(R.string.loading_home_timeline_description)
                );




                Thread loadHomeTimelineThread = new Thread() {
                    public void run() {
                        loadHomeTimeline();
                        handler.post(finishedLoadingListTask);
                    }
                };
                loadHomeTimelineThread.start();
            }



    }

    protected void finishedLoadingList() {
        setLoadMoreViews();
        listView.setAdapter(adapter);
        listView.setSelection(1);
        progressDialog.dismiss();
    }

    private void setLoadMoreViews() {
        headerView = (LoadMoreListItem)getActivity().getLayoutInflater().inflate(R.layout.load_more, null);
        headerView.showHeaderText();
        footerView = (LoadMoreListItem)getActivity().getLayoutInflater().inflate(R.layout.load_more, null);
        footerView.showFooterText();
        listView.addHeaderView(headerView);
        listView.addFooterView(footerView);
    }

    private void loadHomeTimeline() {

        try {
            ArrayList<Status> statii = (ArrayList<Status>) twitter.getHomeTimeline();
            adapter = new StatusListAdapter(getActivity(), statii);



        } catch (TwitterException e) {
            throw new RuntimeException("Unable to load home timeline",e);
        }
    }

    public void displayMessage(String msg, String title){
        new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(msg)
                .show();
    }






    public void initializeUIStuff(View view){

        listView = (ListView)view.findViewById(android.R.id.list);
        listView.setOnItemClickListener(this);
         fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), PostTweetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });




    }



    @Override
    public void loadingMoreStatuses()
    {

    }

    private void loadNewerTweets() {
        headerView.showProgress();
        new LoadMoreAsyncTask(this, twitter, adapter.getFirstId(), true).execute();
    }

    private void loadOlderTweets() {
        footerView.showProgress();
        new LoadMoreAsyncTask(this, twitter, adapter.getLastId()-1, false).execute();
    }

    @Override
    public void statusesLoaded(LoadMoreAsyncTask.LoadMoreStatusesResult result) {

//        progressDialog.setIndeterminate(false);
//        progressDialog.set

        if (result.newer) {
            headerView.hideProgress();
            adapter.appendNewer(result.statuses);
            listView.setSelection(1);
        } else {
            footerView.hideProgress();
            adapter.appendOlder(result.statuses);
        }
    }



    private void startDetailedActivity(Status status){
        Intent intent = new Intent(getActivity(), StatusDetailActivity.class);
        intent.putExtra(StatusDetailActivity.STATUS, status);
        startActivity(intent);
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("Clicked", "i have been clicked");
        if (view.equals(headerView)) {
            headerView.showProgress();
            loadNewerTweets();
        } else if (view.equals(footerView)) {
            footerView.showProgress();
            loadOlderTweets();
        } else {
            // Watch out! Doesn't account for header/footer! -> Status status = adapter.getItem(position);
            Status status = (Status)listView.getItemAtPosition(position);
            startDetailedActivity(status);
        }

    }
}
