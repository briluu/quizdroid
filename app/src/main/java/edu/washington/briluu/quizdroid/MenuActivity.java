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
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

public class MenuActivity extends AppCompatActivity {

    private static final String TOPIC = "TOPIC";
    private HashMap<String, Topic> topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        QuizApp app = (QuizApp) this.getApplication();
        try {
            topics = app.getRepository().getAllTopics();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (topics.size() != 0) {
            // store all topics in an String array to use with ArrayAdapter to display in View
            final String[] topicTitles = new String[topics.size()];

            // store topics in a TreeMap so keySet provides a sorted set of strings
            TreeMap<String, Topic> sortedTitles = new TreeMap<String, Topic>(topics);
            int index = 0;
            for (String topic: sortedTitles.keySet()) {
                topicTitles[index] = topic;
                index++;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topicTitles);

            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                private Intent intent;
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intent = new Intent(MenuActivity.this, QuizActivity.class);
                    switch(position) {
                        case 0:
                            intent.putExtra(TOPIC, topics.get(topicTitles[0]).getTitle());
                            startActivity(intent);
                            break;
                        case 1:
                            intent.putExtra(TOPIC, topics.get(topicTitles[1]).getTitle());
                            startActivity(intent);
                            break;
                        case 2:
                            intent.putExtra(TOPIC, topics.get(topicTitles[2]).getTitle());
                            startActivity(intent);
                            break;
                    }
                }
            });
        }

        // check network connectivity
        if (!isNetworkAvailable() && isAirplaneModeOn()) {
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
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(MenuActivity.this, PreferencesActivity.class);
                startActivity(intent);
                return true;
            default:
                // this returns false on default
                return super.onOptionsItemSelected(item);
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
