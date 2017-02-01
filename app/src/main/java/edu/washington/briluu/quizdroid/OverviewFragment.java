package edu.washington.briluu.quizdroid;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


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

        // Set description text according to corresponding topic
        switch(topic) {
            case "Math":
                TextView mathDescription = (TextView) v.findViewById(R.id.description);
                mathDescription.setText(getString(R.string.math_description));


                break;
            case "Physics":
                TextView physicsDescription = (TextView) v.findViewById(R.id.description);
                physicsDescription.setText(getString(R.string.physics_description));
                break;
            case "Marvel Super Heroes":
                TextView marvelDescription = (TextView) v.findViewById(R.id.description);
                marvelDescription.setText(getString(R.string.marvel_description));
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
