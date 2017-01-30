package edu.washington.briluu.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends AppCompatActivity {

    private String[] topics = {"Math", "Physics", "Marvel Super Heroes"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topics);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            private Intent intent;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch(position) {
                   case 0:
                       intent = new Intent(MenuActivity.this, MathActivity.class);
                       startActivity(intent);
                       break;
                   case 1:
                       intent = new Intent(MenuActivity.this, PhysicsActivity.class);
                       startActivity(intent);
                       break;
                   case 2:
                       intent = new Intent(MenuActivity.this, MarvelSuperHeroesActivity.class);
                       startActivity(intent);
                       break;
               }
            }
        });

    }




}
