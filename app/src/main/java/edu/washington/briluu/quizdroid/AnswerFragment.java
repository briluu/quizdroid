package edu.washington.briluu.quizdroid;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerFragment extends Fragment {

    Button nextBtn;
    private static int total;
    private static int correct;
    public static String user_option;
    private static final String CORRECT = "CORRECT";
    private static final String TOTAL = "TOTAL";

    public static final String USER_OPTION = "USER_OPTION";


    public AnswerFragment() {
        // Required empty public constructor
    }

    public static AnswerFragment newInstance(int correct, int total, String user_option) {
        AnswerFragment fragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putInt(CORRECT, correct);
        args.putInt(TOTAL, total);
        args.putString(USER_OPTION, user_option);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.correct = getArguments().getInt(CORRECT);
            this.total = getArguments().getInt(TOTAL);
            this.user_option = getArguments().getString(USER_OPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_answer, container, false);

        // set the text from the user's previous selection
        TextView userAnswer = (TextView) v.findViewById(R.id.userAnswer);
        userAnswer.setText(user_option);

        // Set the text to tell the user how many questions they have answered correctly
        if (total >= 5) {
            String answersCorrectText = "You have " + correct + " out of " + total + " correct." +
                    "The quiz is now done! Click 'Finish' to go back to the menu.";
            TextView answersCorrect = (TextView) v.findViewById(R.id.answersCorrect);
            answersCorrect.setText(answersCorrectText);
        } else {
            String answersCorrectText = "You have " + correct + " out of " + total + " correct.";
            TextView answersCorrect = (TextView) v.findViewById(R.id.answersCorrect);
            answersCorrect.setText(answersCorrectText);
        }
        nextBtn = (Button) v.findViewById(R.id.next);

        if (total >= 5) {
            // if quiz is done, set the text of button to "Finish"
            nextBtn.setText(R.string.finish);
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total >= 5) {
                    Intent intent = new Intent(getActivity(), MenuActivity.class);
                    startActivity(intent);
                } else {
                    QuestionFragment af = new QuestionFragment();
                    FragmentTransaction tx = getFragmentManager().beginTransaction();
                    tx.replace(R.id.quiz_fragment, af);
                    tx.commit();
                }
            }
        });

        return v;
    }

}
