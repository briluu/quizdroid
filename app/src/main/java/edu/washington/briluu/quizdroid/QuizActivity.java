package edu.washington.briluu.quizdroid;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuizActivity extends AppCompatActivity {

    private static final String TOPIC = "TOPIC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        String topic = getIntent().getStringExtra(TOPIC);

        // create the corresponding topic fragment based on the topic that was clicked
        Fragment overview = OverviewFragment.newInstance(topic);

        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.replace(R.id.quiz_fragment, overview);
        tx.commit();
    }
}
