package edu.washington.briluu.quizdroid;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class PreferencesActivity extends AppCompatActivity {

    public EditText urlET;
    public EditText intervalET;
    public Button save;
    public String download_url;
    DownLoadFilesTask downLoadFilesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        save = (Button) findViewById(R.id.save);
        urlET = (EditText) findViewById(R.id.url);
        intervalET = (EditText) findViewById(R.id.interval);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checks to see if airplane mode is on, if it is prompt user to turn it off
                boolean isNetworkConnected = isNetworkConnected();
                // check if form is valid and if connected
                if (isFormValid() && isNetworkConnected) {
                    // check network availability (wifi or mobile data)
                    download_url = urlET.getText().toString();
                    String interval = intervalET.getText().toString();

                    URL url = null;
                    try {
                        url = new URL(download_url);
                    } catch (IOException e) {
                        e.printStackTrace();
                        // if url doesnt work then deal with it here
                    }
                    downloadQuizQuestions(url, Integer.parseInt(interval));

                    Toast.makeText(getApplicationContext(),
                            "Download will begin shortly and check every " + interval + " minute(s).",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // check the validity of the user inputs
    private Boolean isFormValid() {
        String url = urlET.getText().toString();
        String interval = intervalET.getText().toString();
        if (url.length() == 0) {
            urlET.setError("Need to set a URL to retrieve quiz questions from!");
            return false;
        } else if (interval.length() == 0) {
            intervalET.setError("Need to set an interval length!");
            return false;
        } else {
            return true;
        }
    }

    // sets a timer that spins up the DownLoadFilesTask thread every x minutes
    public void downloadQuizQuestions(final URL url, int interval) {
        final Handler handler = new Handler();
        final Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            if (isNetworkConnected()) {
                                downLoadFilesTask = new DownLoadFilesTask();
                                downLoadFilesTask.execute(url);
                            } else {
                                timer.cancel();
                                DialogFragment dialogFragment = RetryDownloadDialogFragment.newInstance(download_url);
                                dialogFragment.show(getFragmentManager(), "RetryDownloadDialogFragment");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.w("PreferencesActivity", e);
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 1000 * 60 * interval); //execute every 'interval' minutes
    }

    // checks if connected to network and prompts user to turn off airplane mode if it is on
    // otherwise pops a toast to let user that it is not connected
    public boolean isNetworkConnected() {
        if (!isNetworkAvailable()) {
            if (isAirplaneModeOn()) {
                FragmentManager fm = this.getFragmentManager();
                DialogFragment dialogFragment = new AirplaneModeDialogFragment();
                dialogFragment.show(fm, "AirPlaneModeDialogFragment");
            } else {
                // "punt gracefully"
                Log.w("MenuActivity", "Not connected to the internet.");
                Toast.makeText(getApplicationContext(), "No access to internet! :(",
                        Toast.LENGTH_SHORT).show();
            }
            return false;
        } else {
            return true;
        }
    }

    // check if network is available, returns true if it is, otherwise false
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    // Returns true if Airplane mode is on, false otherwise
    @TargetApi(17)
    private boolean isAirplaneModeOn() {
        return Settings.Global.getInt(getApplicationContext().getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

}
