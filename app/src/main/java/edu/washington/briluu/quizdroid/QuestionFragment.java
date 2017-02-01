package edu.washington.briluu.quizdroid;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    Button submitBtn;
    private Boolean correctAnswer = false;
    public static String user_option;
    private static int correct;
    private static int total;

    private static final String CORRECT = "CORRECT";
    private static final String TOTAL = "TOTAL";
    public static final String USER_OPTION = "USER_OPTION";

    public QuestionFragment() {
        // Required empty public constructor
    }

    public static QuestionFragment newInstance(int correct, int total) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(CORRECT, correct);
        args.putInt(TOTAL, total);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.correct = getArguments().getInt(CORRECT);
            this.total = getArguments().getInt(TOTAL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_question, container, false);
        submitBtn = (Button) v.findViewById(R.id.submit);
        total++;
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer) {
                    correct++;
                }
                // for now, loop the quiz so the user will run into this activity 5 times before it ends
                Fragment answer = AnswerFragment.newInstance(correct, total, user_option);
                FragmentTransaction tx = getFragmentManager().beginTransaction();
                tx.replace(R.id.quiz_fragment, answer);
                tx.commit();
            }

        });

        RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.answers);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                submitBtn.setVisibility(View.VISIBLE);
                // Check which radio button was clicked
                switch (checkedId) {
                    case R.id.first_choice:
                        correctAnswer = false;
                        user_option = ((RadioButton) v.findViewById(checkedId)).getText().toString();
                        break;
                    case R.id.second_choice:
                        // correct choice!
                        correctAnswer = true;
                        user_option = ((RadioButton) v.findViewById(checkedId)).getText().toString();
                        break;
                    case R.id.third_choice:
                        correctAnswer = false;
                        user_option = ((RadioButton) v.findViewById(checkedId)).getText().toString();
                        break;
                    case R.id.fourth_choice:
                        correctAnswer = false;
                        user_option = ((RadioButton) v.findViewById(checkedId)).getText().toString();
                        break;
                }
            }
        });

        return v;
    }

}
