package edu.washington.briluu.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.TreeMap;

public class MenuActivity extends AppCompatActivity {

    private static final String TOPIC = "TOPIC";
    private HashMap<String, Topic> topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuizApp app = (QuizApp) this.getApplication();
        topics = app.getRepository().getAllTopics();

        // store all topics in an String array to use with ArrayAdapter to display in View
        final String[] topicTitles = new String[3];

        // store topics in a TreeMap so keySet provides an sorted set of strings
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




}
