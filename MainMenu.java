package com.csufjuststudy.ren.juststudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// import features needed to make a button
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.csufjuststudy.ren.juststudy.Notifications.create_notification;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mainmenuButtons();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public void mainmenuButtons(){
        make_buttons();
    }

    public void make_buttons(){
        Button button1java = (Button) findViewById(R.id.button);
        button1java.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent stepIntent = new Intent(MainMenu.this, FCsetmenu.class);
                startActivity(stepIntent);
            }
        });

        Button button2java = (Button) findViewById(R.id.button2);
        button2java.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent stepIntent = new Intent(MainMenu.this, main_glossary_activity.class);
                startActivity(stepIntent);
            }
        });

        Button button3java = (Button) findViewById(R.id.button3);
        button3java.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent stepIntent = new Intent(MainMenu.this, create_notification.class);
                startActivity(stepIntent);
            }
        });

        Button button4java = (Button) findViewById(R.id.button4);
        button4java.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent stepIntent = new Intent(MainMenu.this, QuizActivity.class);
                startActivity(stepIntent);
            }
        });

        Button button5java = (Button) findViewById(R.id.button5);
        button5java.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent stepIntent = new Intent(MainMenu.this, MatchingGameMenu.class);
                startActivity(stepIntent);
            }
        });


        Button button6java = (Button) findViewById(R.id.button6);
        button6java.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent stepIntent = new Intent(MainMenu.this, DailyMissions.class);
                startActivity(stepIntent);
            }
        });

    }
}
