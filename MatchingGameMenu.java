package com.csufjuststudy.ren.juststudy;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MatchingGameMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_quiz_menu);



        Button start = (Button) findViewById(R.id.begin_quiz_btn1);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent stepIntent = new Intent(MatchingGameMenu.this, MatchingGame.class);
                startActivity(stepIntent);
            }
        });
    }
}