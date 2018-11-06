package com.csufjuststudy.ren.juststudy;

import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;

public class FCset1 extends AppCompatActivity {

    String[] getWords(){
        SharedPreferences settings = getSharedPreferences("sharedPrefs", 0);
        //String wordsString = settings.getString("words list", null);//use when ready to integrate with glossary
        String wordsString = settings.getString("words list flashcards", null);//use when wanting to stay separate from glossary
        System.out.println("LOADED: "+wordsString);
        if (wordsString == null||wordsString=="") {
            return new String[]{};
        }
        return wordsString.split(",");
    }

    String[] getWords(String fc){
        SharedPreferences settings = getSharedPreferences("sharedPrefs", 0);
        //String wordsString = settings.getString("words list", null);//use when ready to integrate with glossary
        String wordsString = settings.getString("words list flashcards "+fc, null);//use when wanting to stay separate from glossary
        System.out.println("LOADED: "+wordsString);
        if (wordsString == null||wordsString=="") {
            return new String[]{};
        }
        return wordsString.split(",");
    }

    String Arr2Text(String[] arr){ //helper function not needed now but will be helpful later when you integrate with glossary
        String temp="";
        if (arr!=null&&arr.length>0) {
            for (int i = 0; i < arr.length; i++) {
                temp += arr[i] + ",";
            }
            temp = temp.substring(0, temp.length() - 1);//trim ,
        }
        return temp;
    }

    String Arr2Text(ArrayList arr){//helper function not needed now but will be helpful later when you integrate with glossary
        String temp="";
        if (arr!=null && arr.size()>0) {
            for (int i = 0; i < arr.size(); i++) {
                temp += arr.get(i) + ",";
            }
            temp = temp.substring(0, temp.length() - 1);//trim ,
        }
        return temp;
    }

    boolean setWords(String word){
        SharedPreferences settings=getSharedPreferences("sharedPrefs",0);
        SharedPreferences.Editor editor=settings.edit();
        //editor.putString("words list",word);//use this one instead when ready to integrate
        editor.putString("words list flashcards",word);//use this one if you want to separate from glossary words
        System.out.println("SAVED: "+word);
        editor.commit();
        return true;
    }

    boolean setWords(String word,String fc){
        SharedPreferences settings=getSharedPreferences("sharedPrefs",0);
        SharedPreferences.Editor editor=settings.edit();
        //editor.putString("words list",word);//use this one instead when ready to integrate
        editor.putString("words list flashcards "+fc,word);//use this one if you want to separate from glossary words
        System.out.println("SAVED: "+word);
        editor.commit();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcset1);

        final String ActivityName=this.getClass().getName();
        EasyFlipView ll = (EasyFlipView) findViewById(R.id.FC1);
        //FC1
        TextInputEditText fc1front= (TextInputEditText) ll.findViewById(R.id.editTextBack);
        TextInputEditText fc1back= (TextInputEditText) ll.findViewById(R.id.editText);
        //FC2
        ll = (EasyFlipView) findViewById(R.id.FC2);
        TextInputEditText fc2front= (TextInputEditText) ll.findViewById(R.id.editTextBack);
        TextInputEditText fc2back= (TextInputEditText) ll.findViewById(R.id.editText);
        //FC3
        ll = (EasyFlipView) findViewById(R.id.FC3);
        TextInputEditText fc3front= (TextInputEditText) ll.findViewById(R.id.editTextBack);
        TextInputEditText fc3back= (TextInputEditText) ll.findViewById(R.id.editText);
        //FC4
        ll = (EasyFlipView) findViewById(R.id.FC4);
        TextInputEditText fc4front= (TextInputEditText) ll.findViewById(R.id.editTextBack);
        TextInputEditText fc4back= (TextInputEditText) ll.findViewById(R.id.editText);
        //And so on...
        ll = (EasyFlipView) findViewById(R.id.FC5);
        TextInputEditText fc5front= (TextInputEditText) ll.findViewById(R.id.editTextBack);
        TextInputEditText fc5back= (TextInputEditText) ll.findViewById(R.id.editText);

        ll = (EasyFlipView) findViewById(R.id.FC6);
        TextInputEditText fc6front= (TextInputEditText) ll.findViewById(R.id.editTextBack);
        TextInputEditText fc6back= (TextInputEditText) ll.findViewById(R.id.editText);

        ll = (EasyFlipView) findViewById(R.id.FC7);
        TextInputEditText fc7front= (TextInputEditText) ll.findViewById(R.id.editTextBack);
        TextInputEditText fc7back= (TextInputEditText) ll.findViewById(R.id.editText);

        ll = (EasyFlipView) findViewById(R.id.FC8);
        TextInputEditText fc8front= (TextInputEditText) ll.findViewById(R.id.editTextBack);
        TextInputEditText fc8back= (TextInputEditText) ll.findViewById(R.id.editText);

        ll = (EasyFlipView) findViewById(R.id.FC9);
        TextInputEditText fc9front= (TextInputEditText) ll.findViewById(R.id.editTextBack);
        TextInputEditText fc9back= (TextInputEditText) ll.findViewById(R.id.editText);
        //FC10
        ll = (EasyFlipView) findViewById(R.id.FC10);
        TextInputEditText fc10front= (TextInputEditText) ll.findViewById(R.id.editTextBack);
        TextInputEditText fc10back= (TextInputEditText) ll.findViewById(R.id.editText);

        final TextInputEditText[][] fcs={{fc1front,fc1back},{fc2front,fc2back},{fc3front,fc3back},{fc4front,fc4back},
                {fc5front, fc5back}, {fc6front, fc6back}, {fc7front, fc7back}, {fc8front, fc8back},{fc9front, fc9back},
                {fc10front, fc10back}};

        //init created FCs
        String[] words=getWords(ActivityName);
        System.out.println("words: ");
        for (int i=0;i<words.length;i++) {
            System.out.println(words[i]);
        }

        for (int i=0;i<fcs.length && i<words.length;i++){
            System.out.println(i);
            try {
                String[] temp = words[i].split("[|]");

                System.out.println(temp[0]);
                fcs[i][0].setText(temp[0]);
                if (temp.length>1) {
                    System.out.println(temp[1]);
                    fcs[i][1].setText(temp[1]);
                }
            }catch (Exception e){
                System.out.println("ERR "+i);
            }
        }
        Button btn1 = (Button) findViewById(R.id.save_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wordsList = "";
                for (int i = 0; i < fcs.length; i++) {
                    wordsList += fcs[i][0].getText().toString();
                    wordsList += "|" + fcs[i][1].getText().toString() + ",";//streamline later
                }
                if (wordsList.length() > 0) {
                    wordsList.substring(0, wordsList.length() - 1);//trim comma
                    setWords(wordsList,ActivityName); //save words for reading later
                    //System.out.println("NAME: "+ActivityName); //unique name for each activity
                }
                Toast toast = Toast.makeText(getApplicationContext(),"SAVED!",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //Keeping the keyboard from automatically popping up once activity is selected
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
