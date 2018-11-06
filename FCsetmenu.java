package com.csufjuststudy.ren.juststudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// import features needed to make a button
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class FCsetmenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcsetmenu);

        fcsetMenu();
    }

    public void fcsetMenu() {create_buttons();}

    public void create_buttons() {
        Button button1java = (Button) findViewById(R.id.fcSet1);
        button1java.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent stepIntent = new Intent(FCsetmenu.this, FCset1.class);
                startActivity(stepIntent);
            }
        });

        Button button2java = (Button) findViewById(R.id.fcSet2);
        button2java.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent stepIntent = new Intent(FCsetmenu.this, FCset2.class);
                startActivity(stepIntent);
            }
        });

        Button button3java = (Button) findViewById(R.id.fcSet3);
        button3java.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent stepIntent = new Intent(FCsetmenu.this, FCset3.class);
                startActivity(stepIntent);
            }
        });

        Button button4java = (Button) findViewById(R.id.fcSet4);
        button4java.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent stepIntent = new Intent(FCsetmenu.this, FCset4.class);
                startActivity(stepIntent);
            }
        });

    }
}
