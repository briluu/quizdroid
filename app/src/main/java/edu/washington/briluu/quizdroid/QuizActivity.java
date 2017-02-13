package edu.washington.briluu.quizdroid;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

public class QuizActivity extends AppCompatActivity {

    private static final String TOPIC = "TOPIC";
    public static HashMap<String, Topic> topics;
    public static Topic currentTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // grab the topic string selected by user
        String topic = getIntent().getStringExtra(TOPIC);

        // access topic repo and grab the current topic;
        QuizApp app = (QuizApp) this.getApplication();
        topics = app.getRepository().getAllTopics();
        currentTopic = topics.get(topic);
        Log.d("QuizActivity", "Topic has been set to " + currentTopic);

        // create the corresponding topic fragment based on the topic that was clicked
        Fragment overview = OverviewFragment.newInstance(topic);
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.replace(R.id.quiz_fragment, overview);
        tx.commit();
    }
}
