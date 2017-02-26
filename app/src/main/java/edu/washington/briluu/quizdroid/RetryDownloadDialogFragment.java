package edu.washington.briluu.quizdroid;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by briluu on 2/26/17.
 */

public class RetryDownloadDialogFragment extends DialogFragment {

    public static final String URL = "URL";
    public static String url;

    public static RetryDownloadDialogFragment newInstance(String url) {
        RetryDownloadDialogFragment retryDownloadDialogFragment = new RetryDownloadDialogFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        retryDownloadDialogFragment.setArguments(args);
        return retryDownloadDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Log.d("RetryDloadDialogFrag", "onCreateDialog called!");
        url = getArguments().getString(URL);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("The download for the quiz questions failed. Turn on Wi-Fi or data and click Retry.")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        retryDownload();
                    }
                })
                .setNegativeButton("No, quit and try again later", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void retryDownload() {
        // retry download for quiz
        DownLoadFilesTask downLoadFilesTask = new DownLoadFilesTask();
        try {
            downLoadFilesTask.execute(new URL(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
