package com.ayibonte.twitter.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ayibonte.twitter.R;
import com.ayibonte.twitter.controllers.OTweetApplication;

public class SettingsActivity extends ActionBarActivity {

    private EditText passwordText;
    private EditText usernameText;
    private OTweetApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (OTweetApplication)getApplication();
        setContentView(R.layout.settings);
        setUpViews();
        loadSettings();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_post:
                break;
        }
        return true;
    }
    
    public void saveButtonClicked(View view) {
        saveSettings();
    }

    private void loadSettings() {
        // usernameText.setText(app.getTwitPicUsername());
    }

    private void saveSettings() {
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        // app.saveTwitPicCredentials(username, password);
        // Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_LONG).show();
        finish();
    }

    private void setUpViews() {
        usernameText = (EditText)findViewById(R.id.username);
        passwordText = (EditText)findViewById(R.id.password);
    }
}
