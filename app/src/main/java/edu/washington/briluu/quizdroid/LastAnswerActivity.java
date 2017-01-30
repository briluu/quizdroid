package edu.washington.briluu.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LastAnswerActivity extends AppCompatActivity {

    public static final String TOTAL = "TOTAL";
    public static final String NUMBER_CORRECT = "NUMBER_CORRECT";
    public static int total;
    public static int correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_answer);

        correct = getIntent().getIntExtra(NUMBER_CORRECT, 0);
        total = getIntent().getIntExtra(TOTAL, 0);

        String answersCorrectText = "You have " + correct + " out of " + total + " correct. " +
                "The quiz is now done! Click 'Finish' to go back to the menu.";
        TextView answersCorrect = (TextView) findViewById(R.id.answersCorrect);
        answersCorrect.setText(answersCorrectText);

        Button button = (Button) findViewById(R.id.finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LastAnswerActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
