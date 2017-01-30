package edu.washington.briluu.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MathActivity extends AppCompatActivity {

    private static int correct;
    private static int total;
    public static final String TOTAL = "TOTAL";
    public static final String NUMBER_CORRECT = "NUMBER_CORRECT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        Button start = (Button) findViewById(R.id.mathStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MathActivity.this, QuestionActivity.class);
                intent.putExtra(TOTAL, total);
                intent.putExtra(NUMBER_CORRECT, correct);
                startActivity(intent);
            }
        });
    }
}
