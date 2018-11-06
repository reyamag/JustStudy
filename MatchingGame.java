package com.csufjuststudy.ren.juststudy;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

public class MatchingGame extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 300000;

    Context mContext;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private Button mbutton6;
    private Button mbutton7;
    private Button mbutton8;
    private Button mbutton9;
    private Button mbutton10;
    private Button mbutton11;
    private Button mbutton12;
    private Button mbutton13;

    private Button mbuttonSubmit;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    int score=0;
    Button activeButton=null;

    String[] getWords() {
        SharedPreferences sharedPrefs = getSharedPreferences("sharedPrefs", 0);
        String str_words = sharedPrefs.getString("words list", null);
        if (str_words == null || str_words.equals("")) {
            return new String[] {};
        }
        return str_words.split(",");
    }

    String[][] getWordDef(){
        String[] words=getWords();
        String[][] worddefs=new String[words.length][];
        for (int i=0;i<words.length;i++){
            worddefs[i]=words[i].split(": ");
        }
        return worddefs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_game);
        mContext = this;

        mTextViewCountDown = (TextView) findViewById(R.id.text_view_countdown);

        mButtonStartPause = (Button) findViewById(R.id.button_start_pause);
        mButtonReset = (Button) findViewById(R.id.button_reset);

        mbutton6 = (Button) findViewById(R.id.button6);//def1
        mbutton7 = (Button) findViewById(R.id.button7);
        mbutton8 = (Button) findViewById(R.id.button8);
        mbutton9 = (Button) findViewById(R.id.button9);

        mbutton10 = (Button) findViewById(R.id.button10);//def1
        mbutton11 = (Button) findViewById(R.id.button11);
        mbutton12 = (Button) findViewById(R.id.button12);
        mbutton13 = (Button) findViewById(R.id.button13);

        mbutton6.setEnabled(false);
        mbutton7.setEnabled(false);
        mbutton8.setEnabled(false);
        mbutton9.setEnabled(false);
        mbutton10.setEnabled(false);
        mbutton11.setEnabled(false);
        mbutton12.setEnabled(false);
        mbutton13.setEnabled(false);
        mbuttonSubmit = (Button) findViewById(R.id.button14);

        mbuttonSubmit.setVisibility(View.INVISIBLE);


        String[][] term_with_defs=getWordDef();
        for (int i=0;i<term_with_defs.length;i++) { //later the array order should be randomized order
            Log.i("arraydefs", "      Term" + i + ": " + term_with_defs[i][0]);
            Log.i("arraydefs", "Definition" + i + ": " + term_with_defs[i][1]);
            switch (i){
                case 0:
                    mbutton13.setText(term_with_defs[i][0]);
                    mbutton6.setText(term_with_defs[i][1]);
                case 1:
                    mbutton12.setText(term_with_defs[i][0]);
                    mbutton7.setText(term_with_defs[i][1]);
                case 2:
                    mbutton11.setText(term_with_defs[i][0]);
                    mbutton8.setText(term_with_defs[i][1]);
                case 3:
                    mbutton10.setText(term_with_defs[i][0]);
                    mbutton9.setText(term_with_defs[i][1]);
            }
        }

        final TextView textView = (TextView) findViewById(R.id.textView6);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mbuttonSubmit.setVisibility(View.VISIBLE);
                if (mTimerRunning) {
                    mbutton6.setEnabled(false);
                    mbutton7.setEnabled(false);
                    mbutton8.setEnabled(false);
                    mbutton9.setEnabled(false);
                    mbutton10.setEnabled(false);
                    mbutton11.setEnabled(false);
                    mbutton12.setEnabled(false);
                    mbutton13.setEnabled(false);
                    textView.setText("Touch Resume to Continue!");
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    //pauseTimer();
                } else {
                    mbutton6.setEnabled(true);
                    mbutton7.setEnabled(true);
                    mbutton8.setEnabled(true);
                    mbutton9.setEnabled(true);
                    mbutton10.setEnabled(true);
                    mbutton11.setEnabled(true);
                    mbutton12.setEnabled(true);
                    mbutton13.setEnabled(true);
                    textView.setText("Select the Question!");
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Touch Play to begin!");
                resetTimer();
                mbutton6.setEnabled(false);
                mbutton7.setEnabled(false);
                mbutton8.setEnabled(false);
                mbutton9.setEnabled(false);
                mbutton10.setEnabled(false);
                mbutton11.setEnabled(false);
                mbutton12.setEnabled(false);
                mbutton13.setEnabled(false);
            }
        });

        updateCountDownText();

        mbuttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("You Scored "+score+"/4. Go back to Play Again!");
                DailyMission.incProgress(mContext, "3");
                pauseTimer();
                mbutton6.setEnabled(false);
                mbutton7.setEnabled(false);
                mbutton8.setEnabled(false);
                mbutton9.setEnabled(false);
                mbutton10.setEnabled(false);
                mbutton11.setEnabled(false);
                mbutton12.setEnabled(false);
                mbutton13.setEnabled(false);
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        });
        resetTimer();
        updateCountDownText();

        mbutton6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {//first def
                textView.setText("Select next Question or Select SUBMIT when done");
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                mbutton6.setEnabled(false);
                if (activeButton!=null){
                    if (activeButton==mbutton13){
                        score+=1;
                        Log.i("mbutton6","correct");
                    }else{
                        Log.i("mbutton6","wrong expected "+mbutton13.getText().toString());
                    }
                }
                activeButton=mbutton6;
            }

        });

        mbutton7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                textView.setText("Select next Question or Select SUBMIT when done");
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                mbutton7.setEnabled(false);
                if (activeButton!=null){
                    if (activeButton==mbutton12){
                        score+=1;
                        Log.i("mbutton7","correct");
                    }else{
                        Log.i("mbutton7","wrong expected "+mbutton12.getText().toString());
                    }

                }
                activeButton=mbutton7;
            }

        });

        mbutton8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                textView.setText("Select next Question or Select SUBMIT when done");
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                if (activeButton!=null){
                    if (activeButton==mbutton11){
                        score+=1;
                        Log.i("mbutton8","correct");
                    }else{
                        Log.i("mbutton8","wrong expected "+mbutton11.getText().toString());
                    }
                }
                mbutton8.setEnabled(false);
            }

        });

        mbutton9.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                textView.setText("Select next Question or Select SUBMIT when done");
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                mbutton9.setEnabled(false);
                if (activeButton!=null){
                    if (activeButton==mbutton10){
                        score+=1;
                        Log.i("mbutton9","correct");
                    }else{
                        Log.i("mbutton9","wrong expected "+mbutton10.getText().toString());
                    }
                }
                activeButton=mbutton9;
            }

        });

        mbutton10.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {//first term
                textView.setText("Match the answer!");
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                mbutton10.setEnabled(false);
                activeButton=mbutton10;
            }

        });

        mbutton11.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                textView.setText("Match the answer!");
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                mbutton11.setEnabled(false);
                activeButton=mbutton11;
            }

        });

        mbutton12.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                textView.setText("Match the answer!");
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                mbutton12.setEnabled(false);
                activeButton=mbutton12;
            }

        });

        mbutton13.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                textView.setText("Match the answer!");
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                mbutton13.setEnabled(false);
                activeButton=mbutton13;
            }

        });
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Play");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("Pause");
        mButtonReset.setVisibility(View.INVISIBLE);
        mbuttonSubmit.setVisibility(View.VISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mbuttonSubmit.setVisibility(View.INVISIBLE);
        mButtonStartPause.setText("Resume");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonStartPause.setText("Play");
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
        mbuttonSubmit.setVisibility(View.INVISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}