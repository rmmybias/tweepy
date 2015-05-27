package com.ayibonte.twitter.fragments;

/**
 *
 * Created by alfred on 3/28/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ayibonte.twitter.R;

public class FragmentMain extends Fragment {
    public static final int TIMELINE=0;
    public static final int POST=1;

    private boolean mSearchCheck;
    public static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    public FragmentMain newInstance(String text){
        FragmentMain mFragment = new FragmentMain();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        //Select search item
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        menuItem.setVisible(true);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(this.getString(R.string.search));


        mSearchCheck = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_search:
                mSearchCheck = true;

                break;
        }
        return true;


    }


}
