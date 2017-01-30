package edu.washington.briluu.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class QuestionActivity extends AppCompatActivity {

    Button submit;
    private static int correct;
    private static int total;
    public static String user_option;
    public static final String TOTAL = "TOTAL";
    public static final String NUMBER_CORRECT = "NUMBER_CORRECT";
    public static final String USER_OPTION = "USER_OPTION";

    private Boolean correctAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        correct = getIntent().getIntExtra(NUMBER_CORRECT, 0);
        total = getIntent().getIntExtra(TOTAL, 0);
        total++;

        RadioGroup answers = (RadioGroup) findViewById(R.id.answers);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer) {
                    correct++;
                }

                // for now, loop the quiz so the user will run into this activity 5 times before it ends
                if (total < 5) {
                    Intent intent = new Intent(QuestionActivity.this, AnswerActivity.class);
                    intent.putExtra(TOTAL, total);
                    intent.putExtra(NUMBER_CORRECT, correct);
                    intent.putExtra(USER_OPTION, user_option);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(QuestionActivity.this, LastAnswerActivity.class);
                    intent.putExtra(TOTAL, total);
                    intent.putExtra(NUMBER_CORRECT, correct);
                    intent.putExtra(USER_OPTION, user_option);
                    startActivity(intent);
                }
            }
        });
    }


    // "android:onClick="onRadioButtonClicked" is included in each RadioButton's XML
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        if (checked) {
            submit.setVisibility(View.VISIBLE);
            // Check which radio button was clicked
            switch(view.getId()) {
                case R.id.first_choice:
                    correctAnswer = false;
                    user_option = ((RadioButton) view).getText().toString();
                    break;
                case R.id.second_choice:
                    // correct choice!
                    correctAnswer = true;
                    user_option = ((RadioButton) view).getText().toString();
                    break;
                case R.id.third_choice:
                    correctAnswer = false;
                    user_option = ((RadioButton) view).getText().toString();
                    break;
                case R.id.fourth_choice:
                    correctAnswer = false;
                    user_option = ((RadioButton) view).getText().toString();
                    break;
            }
        }
    }
}
