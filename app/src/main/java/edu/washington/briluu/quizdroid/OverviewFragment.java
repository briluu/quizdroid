package edu.washington.briluu.quizdroid;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    private static final String TOPIC = "TOPIC";
    private String topic;
    Button beginBtn;

    public OverviewFragment() {
        // Required empty public constructor
    }

    public static OverviewFragment newInstance(String topic) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putString(TOPIC, topic);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topic = getArguments().getString(TOPIC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("OverviewFragment", "Topic is: " + topic);
        View v = inflater.inflate(R.layout.fragment_overview, container, false);

        // Grab the activity's repo of topics and current topic
        QuizActivity quiz = (QuizActivity) getActivity();
        HashMap<String, Topic> allTopics = quiz.topics;
        Topic currentTopic = quiz.currentTopic;

        int numQuestions = currentTopic.getQuestions().size();
        TextView numQuestionsTV = (TextView) v.findViewById(R.id.numQuestions);
        numQuestionsTV.setText("This quiz will contain " + numQuestions + " questions.");

        // Set description text according to corresponding topic
        switch(topic) {
            case "Math":
                TextView mathDescription = (TextView) v.findViewById(R.id.description);
                mathDescription.setText(allTopics.get("Math").getLongDescription());
                break;
            case "Physics":
                TextView physicsDescription = (TextView) v.findViewById(R.id.description);
                physicsDescription.setText(allTopics.get("Physics").getLongDescription());
                break;
            case "Marvel Super Heroes":
                TextView marvelDescription = (TextView) v.findViewById(R.id.description);
                marvelDescription.setText(allTopics.get("Marvel Super Heroes").getLongDescription());
                break;
        }

        beginBtn = (Button) v.findViewById(R.id.begin);
        beginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment qf = QuestionFragment.newInstance(0, 0);
                FragmentTransaction tx = getFragmentManager().beginTransaction();
                tx.replace(R.id.quiz_fragment, qf);
                tx.commit();
            }
        });

        return v;
    }

}
