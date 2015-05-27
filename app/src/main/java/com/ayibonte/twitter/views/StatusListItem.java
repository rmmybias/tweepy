package com.ayibonte.twitter.views;

import java.net.MalformedURLException;
import java.net.URL;



import twitter4j.Status;
import twitter4j.User;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayibonte.twitter.R;
import com.ayibonte.twitter.tasks.LoadImageAsyncTask;

public class StatusListItem extends RelativeLayout implements LoadImageAsyncTask.LoadImageAsyncTaskResponder {

  private ImageView     avatarView;
  private TextView      screenName;
  private TextView      statusText;
  private AsyncTask<URL, Void, Drawable> latestLoadTask;

  public StatusListItem(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void setStatus(Status status) {
    final User user = status.getUser();
    findViews();
    screenName.setText(user.getScreenName());
    statusText.setText(status.getText());
    // cancel old task
    if (null != latestLoadTask) {
      latestLoadTask.cancel(true);
    }
      try {
          latestLoadTask = new LoadImageAsyncTask(this).execute(new URL(user.getProfileImageURL()));
      } catch (MalformedURLException e) {
          e.printStackTrace();
      }
  }

  public void imageLoading() {
    avatarView.setImageDrawable(null);
  }

  public void imageLoadCancelled() {
    // do nothing
  }

  public void imageLoaded(Drawable drawable) {
    avatarView.setImageDrawable(drawable);
  }

  private void findViews() {
    avatarView = (ImageView) findViewById(R.id.user_avatar);
    screenName = (TextView) findViewById(R.id.status_user_name_text);
    statusText = (TextView) findViewById(R.id.status_text);
  }

}
