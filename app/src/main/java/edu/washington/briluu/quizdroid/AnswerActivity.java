package edu.washington.briluu.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AnswerActivity extends AppCompatActivity {


    public static final String TOTAL = "TOTAL";
    public static final String NUMBER_CORRECT = "NUMBER_CORRECT";
    public static final String USER_OPTION = "USER_OPTION";
    public static String user_option;
    public static int total;
    public static int correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        correct = getIntent().getIntExtra(NUMBER_CORRECT, 0);
        total = getIntent().getIntExtra(TOTAL, 0);
        user_option = getIntent().getStringExtra(USER_OPTION);

        TextView user_answer = (TextView) findViewById(R.id.userAnswer);
        user_answer.setText(user_option);

        String answersCorrectText = "You have " + correct + " out of " + total + " correct.";
        TextView answersCorrect = (TextView) findViewById(R.id.answersCorrect);
        answersCorrect.setText(answersCorrectText);

        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnswerActivity.this, QuestionActivity.class);
                intent.putExtra(TOTAL, total);
                intent.putExtra(NUMBER_CORRECT, correct);
                startActivity(intent);
            }
        });
    }
}
