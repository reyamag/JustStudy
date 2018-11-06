package com.csufjuststudy.ren.juststudy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class QuizActivity extends AppCompatActivity {
    // Reference to the timer object
    CountDownTimer q_timer;

    // Context
    Context mContext;

    // Variables to store the min/max number of terms/defs
    final int MIN_TERMS = 4;
    final int MAX_TERMS = 10;
    final int MIN_DEFS = 4;

    // Variable to count the number of items currently stored in the SharedPreferences
    int totalSharedPreferences = 0;

    // Variables to count the number of items stored in the termsDefsArr array
    int total_termsDefsArr = 0;
    int total_termsArr = 0;
    int total_defsArr = 0;

    // Control variable used to track the number of questions generated
    // Found in: submit button onClickListener
    // Used by: submit button
    int control = 0;

    // Variable used to select a term
    int termIndex = 0;

    // Variable used to track the user's score
    int score = 0;

    // Boolean value to determine if the user answered correctly or incorrectly
    boolean result;

    // Arrays to store the terms and definitions
    String[] termsDefsArr = new String[40];
    String[] termsArr = new String[40];
    String[] defsArr = new String[40];

    // Glossary method: Uses SharedPreferences to store key values used throughout the app
    String[] getWords() {
        SharedPreferences sharedPrefs = getSharedPreferences("sharedPrefs", 0);
        String str_words = sharedPrefs.getString("words list", null);
        if (str_words == null || str_words.equals("")) {
            return new String[] {};
        }
        return str_words.split(",");
    }

    // Method used to generate a quiz question
    public void generateQuestion() {
        // Reference to the screen objects
        final TextView q_termsTV = (TextView) findViewById(R.id.term_TV);
        final RadioButton q_answer1 = (RadioButton) findViewById(R.id.q_answer1);
        final RadioButton q_answer2 = (RadioButton) findViewById(R.id.q_answer2);
        final RadioButton q_answer3 = (RadioButton) findViewById(R.id.q_answer3);
        final RadioButton q_answer4 = (RadioButton) findViewById(R.id.q_answer4);
        //final RadioButton q_noneOfTheAbove = (RadioButton) findViewById(R.id.q_noneOfTheAbove);

        // **********************************************************************
        // An ArrayList of integers from 0 up to the total number of items in SharedPreferences
        ArrayList<Integer> indices = new ArrayList<>();
        for (int index = 0; index < totalSharedPreferences; index++) {
            indices.add(index);
        }
        // Shuffle the numbers in the ArrayList
        Collections.shuffle(indices);

        // Brute Force Method
        // Select a term (in order from 0 to number of items in termsArr)
        q_termsTV.setText(termsArr[termIndex]);

        // Select four definitions
        for (int index = 0; index < MIN_DEFS; index++) {
            switch (index) {
                case 0:
                    q_answer1.setText(defsArr[indices.get(index)]);
                    break;
                case 1:
                    q_answer2.setText(defsArr[indices.get(index)]);
                    break;
                case 2:
                    q_answer3.setText(defsArr[indices.get(index)]);
                    break;
                case 3:
                    q_answer4.setText(defsArr[indices.get(index)]);
                    break;
                default:
                    Log.i("QuizActivity", "Error assigning definitions.");
            }
        }
        // **********************************************************************

        /* Check if the answer is correct
        if (q_answer1.getText().equals(defsArr[termIndex])) {
            result = q_answer1.isChecked();
            Log.i("QuizActivity", "Result: " + result);
        }
        else if (q_answer2.getText().equals(defsArr[termIndex])) {
            result = q_answer2.isChecked();
        }
        else if (q_answer3.getText().equals(defsArr[termIndex])) {
            result = q_answer3.isChecked();
        }
        else if (q_answer4.getText().equals(defsArr[termIndex])) {
            result = q_answer4.isChecked();
        }
        else {
            result = q_noneOfTheAbove.isChecked();
        }*/

        // Increment the term index (gets the next term)
        termIndex++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mContext = this;

        // Get terms/defs from glossary and store them in an array
        String[] terms_defs = getWords();

        // Logcat: Print the items in the terms_defs array
        for (String item : terms_defs) {
            Log.i("QuizActivity", "Terms/Defs: " + item);
        }

        // Store the total number of terms/defs
        totalSharedPreferences = terms_defs.length;

        // Logcat: Print the number of items in SharedPreferences to Logcat
        Log.i("QuizActivity", "Number of items: " + totalSharedPreferences);

        // Store the contents of terms_defs into a single string
        StringBuilder longString = new StringBuilder();
        for (String item : terms_defs) {
            longString.append(item);
            longString.append(": ");
        }

        // Print the contents of longString
        Log.i("QuizActivity", "longString: " + longString);

        // Split the longString and store the individual terms and definitions in termsDefsArr array
        termsDefsArr = longString.toString().split(": ");

        // Print the contents of termsDefsArr
        for (String item : termsDefsArr) {
            Log.i("QuizActivity", "Terms: " + item);
        }

        // Get the total number of items in the termsDefsArr
        total_termsDefsArr = termsDefsArr.length;

        // Print the number of items in termsDefsArr to Logcat
        Log.i("QuizActivity", "Items in termsDefArr: " + total_termsDefsArr);

        /*
         * Store the terms and definitions in separate arrays
         */

        // Variables to maintain consistency with the indices
        int index_term = 0;
        int index_def = 0;
        int index_control = 0;

        // Traverse the termsDefsArr
        for (int index = 0; index < termsDefsArr.length; index++) {
            // Check for even index
            if (index % 2 == 0) {
                // Print the current index
                Log.i("QuizActivity", "Entering first condition...index: " + index);
                // Store the term in the new array
                termsArr[index_term] = termsDefsArr[index];
                // Increment the control index
                index_term++;
            }
            // Check for odd index
            else if (index % index_control == 0) {
                // Print the current index
                Log.i("QuizActivity", "Entering second condition...index: " + index);
                // Store the definition in the new array
                defsArr[index_def] = termsDefsArr[index];
                // Increment the control index
                index_def++;
            }
            // An error occurred: print a message to Logcat
            else {
                Log.i("QuizActivity", "Error storing the data.");
            }
            // Increment the control index
            index_control++;
        }

        // Print the arrays: termsArr, defsArr (to Logcat)
        for (String item : termsArr) {
            Log.i("QuizActivity", "Terms: " + item);
        }
        for (String item : defsArr) {
            Log.i("QuizActivity", "Definitions: " + item);
        }

        // Get the total number of items in each array
        total_termsArr = termsArr.length;
        total_defsArr = defsArr.length;

        // References to the on-screen objects
        final Button q_begin_btn = (Button) findViewById(R.id.begin_quiz_btn);
        final Button q_submit_btn = (Button) findViewById(R.id.quiz_submit_btn);
        final Button q_continue_btn = (Button) findViewById(R.id.quiz_continue_btn);
        final TextView q_timer_TV = (TextView)findViewById(R.id.quiz_timerTV);
        final TextView q_cardsTV = (TextView) findViewById(R.id.flash_card_TV);
        final TextView q_termsTV = (TextView) findViewById(R.id.term_TV);
        final TextView q_answersTV = (TextView) findViewById(R.id.answers_TV);
        final TextView q_resultsTV = (TextView) findViewById(R.id.quiz_resultTV);
        final RadioGroup q_radioGroup = (RadioGroup) findViewById(R.id.quiz_radio_group);
        final RadioButton q_answer1 = (RadioButton) findViewById(R.id.q_answer1);
        final RadioButton q_answer2 = (RadioButton) findViewById(R.id.q_answer2);
        final RadioButton q_answer3 = (RadioButton) findViewById(R.id.q_answer3);
        final RadioButton q_answer4 = (RadioButton) findViewById(R.id.q_answer4);
        final RadioButton q_noneOfTheAbove = (RadioButton) findViewById(R.id.q_noneOfTheAbove);

        // Set objects invisible on activity create
        q_submit_btn.setVisibility(View.INVISIBLE);
        q_continue_btn.setVisibility(View.INVISIBLE);
        q_cardsTV.setVisibility(View.INVISIBLE);
        q_termsTV.setVisibility(View.INVISIBLE);
        q_answersTV.setVisibility(View.INVISIBLE);
        q_resultsTV.setVisibility(View.INVISIBLE);
        q_radioGroup.setVisibility(View.INVISIBLE);

        // Prevent the keypad from appearing after minimizing during activity run
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Set the text for the quiz_timerTV
        q_timer_TV.setText(R.string.quiz_descriptionTV);

        // OnClickListener for the begin button: starts the quiz if there are enough terms in the
        //  glossary ("enough" >= 4 terms/definitions)
        q_begin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check that there are enough terms/definitions stored in the glossary
                // ** There are not enough terms: prompt the user and send them back to main act **
                if (totalSharedPreferences < MIN_TERMS || totalSharedPreferences > MAX_TERMS) {
                    q_timer_TV.setText(R.string.quiz_notEnoughTermsTV);
                    q_begin_btn.setEnabled(false);
                    Context context = getApplicationContext();
                    CharSequence text = "Invalid number of terms";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                // ** There are enough terms: execute the quiz as intended **
                else {
                    // Set the begin button invisible
                    q_begin_btn.setVisibility(View.INVISIBLE);

                    // Set other objects to be visible
                    q_submit_btn.setVisibility(View.VISIBLE);
                    q_cardsTV.setVisibility(View.VISIBLE);
                    q_termsTV.setVisibility(View.VISIBLE);
                    q_answersTV.setVisibility(View.VISIBLE);
                    q_radioGroup.setVisibility(View.VISIBLE);

                    // Use the generateQuestion method to generate the first question
                    generateQuestion();

                    // Defines a variable to store the time (2 minutes)
                    int q_time = 120000;

                    // Provides functionality for the timer feature
                    q_timer = new CountDownTimer(q_time, 1000) {
                        @Override
                        public void onTick(long l) {
                            @SuppressLint("DefaultLocale") String text = String.format("Time limit: %02d:%02d",
                                    TimeUnit.MILLISECONDS.toMinutes(l) -
                                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),
                                    TimeUnit.MILLISECONDS.toSeconds(l) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                            q_timer_TV.setText(text);
                        }

                        @Override
                        public void onFinish() {
                            q_timer_TV.setText(R.string.quiz_quizOverTV);
                            // Disable the continue button
                            q_continue_btn.setEnabled(false);
                            q_submit_btn.setEnabled(false);
                            q_continue_btn.setVisibility(View.INVISIBLE);
                            q_submit_btn.setVisibility(View.INVISIBLE);
                            // Show the user's score
                            String scoreToString = String.valueOf(score);
                            String totalToString = String.valueOf(totalSharedPreferences);
                            q_resultsTV.setText(getString(R.string.quiz_score, scoreToString, totalToString));
                            q_resultsTV.setVisibility(View.VISIBLE);
                        }
                    }.start();
                }
            }
        });

        // Set the onClickListener for the submit button
        q_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Disable the submit button
                q_submit_btn.setEnabled(false);
                // Set the continue button visible
                q_continue_btn.setVisibility(View.VISIBLE);
                // Display the result
                q_resultsTV.setVisibility(View.VISIBLE);
                // Disable the radio buttons
                q_answer1.setEnabled(false);
                q_answer2.setEnabled(false);
                q_answer3.setEnabled(false);
                q_answer4.setEnabled(false);
                q_noneOfTheAbove.setEnabled(false);

                /*
                 * Get the results of the user's answer
                 */

                /* TEST: Print to Logcat the first answer and definition
                Log.i("QuizActivity", "A1: " + q_answer1.getText());
                Log.i("QuizActivity", "Def: " + defsArr[termIndex - 1]);
                */

                // Check what the user selected and get the result
                if (q_answer1.getText().equals(defsArr[termIndex - 1])) {
                    result = q_answer1.isChecked();
                    //Log.i("QuizActivity", "Result: " + result);
                }
                else if (q_answer2.getText().equals(defsArr[termIndex - 1])) {
                    result = q_answer2.isChecked();
                }
                else if (q_answer3.getText().equals(defsArr[termIndex - 1])) {
                    result = q_answer3.isChecked();
                }
                else if (q_answer4.getText().equals(defsArr[termIndex - 1])) {
                    result = q_answer4.isChecked();
                }
                else {
                    result = q_noneOfTheAbove.isChecked();
                }

                // Check the result
                if (result) {
                    q_resultsTV.setText(R.string.quiz_correct);
                    q_resultsTV.setTextColor(Color.parseColor("green"));
                    score++;
                    DailyMission.incProgress(mContext, "2");
                }
                else {
                    q_resultsTV.setText(R.string.quiz_incorrect);
                    q_resultsTV.setTextColor(Color.parseColor("red"));
                }

                // Stop the quiz if all terms have been generated
                if (control == totalSharedPreferences - 1) {
                    // Stop the timer
                    q_timer.cancel();
                    // Change the text of the TV
                    q_timer_TV.setText(R.string.quiz_quizOverTV);
                    // Disable the continue button
                    q_continue_btn.setEnabled(false);
                    q_continue_btn.setVisibility(View.INVISIBLE);
                    q_submit_btn.setVisibility(View.INVISIBLE);
                    // Show the user's score
                    String scoreToString = String.valueOf(score);
                    String totalToString = String.valueOf(totalSharedPreferences);
                    q_resultsTV.setText(getString(R.string.quiz_score, scoreToString, totalToString));
                    DailyMission.incProgress(mContext, "1");
                }
                // Update the control variable
                else if (control < totalSharedPreferences - 1 || control < MAX_TERMS - 1) {
                    Log.i("QuizActivity", "Entering the while loop.");
                    // Increment the control variable
                    control++;
                    // Print the control variable to Logcat
                    Log.i("QuizActivity", "control: " + control);
                }
                // An error occurred
                else {
                    Log.i("QuizActivity", "Error: SUBMIT Button");
                }
            }
        });

        // Set the onClickListener for the continue button
        q_continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the continue button invisible
                q_continue_btn.setVisibility(View.INVISIBLE);
                // Enable the submit button
                q_submit_btn.setEnabled(true);
                // Enable the radio group
                q_answer1.setEnabled(true);
                q_answer2.setEnabled(true);
                q_answer3.setEnabled(true);
                q_answer4.setEnabled(true);
                q_noneOfTheAbove.setEnabled(true);
                // Set the results to be invisible
                q_resultsTV.setVisibility(View.INVISIBLE);
                // Clear the checked radio button
                q_radioGroup.clearCheck();
                // Generate another question
                generateQuestion();
            }
        });
    }

    // Allows the app to be minimized
    /*@Override
    public void onBackPressed() { moveTaskToBack( true); }
    */
}
