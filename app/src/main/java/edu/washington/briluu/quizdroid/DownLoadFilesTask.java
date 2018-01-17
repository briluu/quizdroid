package edu.washington.briluu.quizdroid;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by briluu on 2/26/17.
 */

public class DownLoadFilesTask extends AsyncTask<URL, Integer, Long> {
    @Override
    protected Long doInBackground(URL... url) {
        String response;
        try {
            Log.d("DownLoadFilesTask", "doInBackground called.");

            // connect to url and GET the json
            HttpURLConnection urlConnection = (HttpURLConnection) url[0].openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            // convert stream to string
            response = convertStreamToString(in);

            Log.d("DownLoadFilesTask", response);

            // save string to a file to external storage directory private to the app
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS) + "/questions.json");

            Log.d("DownLoadFilesTask", file.getAbsolutePath());

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(response.getBytes());
            fos.close();

            urlConnection.disconnect();
        } catch (IOException e) {
            // let user know that url didnt work, etc
            Log.w("PreferencesActivity", e);
            e.printStackTrace();
        }
        return null;
    }

    // Followed parsing tutorial from http://www.androidhive.info/2012/01/android-json-parsing-tutorial/
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
