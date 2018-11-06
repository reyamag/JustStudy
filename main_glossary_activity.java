package com.csufjuststudy.ren.juststudy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main_glossary_activity extends AppCompatActivity {

    String[] getWords(){
        SharedPreferences settings = getSharedPreferences("sharedPrefs", 0);
        String wordsString = settings.getString("words list", null);
        if (wordsString == null||wordsString=="") {
            return new String[]{};
        }
        return wordsString.split(",");
    }

    String Arr2Text(String[] arr){
        String temp="";
        if (arr!=null&&arr.length>0) {
            for (int i = 0; i < arr.length; i++) {
                temp += arr[i] + ",";
            }
            temp = temp.substring(0, temp.length() - 1);//trim ,
        }
        return temp;
    }

    String Arr2Text(ArrayList arr){
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
        editor.putString("words list",word);
        System.out.println("SAVED: "+word);
        editor.commit();
        return true;
    }

    boolean addWord(String word){
        SharedPreferences settings=getSharedPreferences("sharedPrefs",0);
        SharedPreferences.Editor editor=settings.edit();
        String data=Arr2Text(getWords());
        if (data==""||data==null){
            editor.putString("words list",word);
        }else{
            editor.putString("words list",data+","+word);
        }
        System.out.println("SAVED: "+word);
        editor.commit();
        return true;
    }

    boolean ListhasAny(List<Integer> boolList ){
        for (int i=0;i<boolList.size();i++){
            if (boolList.get(i)==1) return true;
        }
        return false;
    }

    boolean removeword(String word){
        String[] cwords=getWords();
        boolean removed=false;
        ArrayList cList = new ArrayList<String>(Arrays.asList(cwords));
        for (int i=0;i<cList.size();i++){
            if (cList.get(i).equals(word)){
                System.out.println(i+" REMOVED: "+cList.get(i));
                cList.remove(i);
                removed=true;
                break;
            }
        }
        if (removed) {
            SharedPreferences settings = getSharedPreferences("sharedPrefs", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("words list", Arr2Text(cList));
            editor.commit();
        }
        return removed;
    }

    ImageButton btn2 = null;

    ArrayAdapter<String> wordAdapter;
    EditText txtSearch;
    ArrayList<String> wordList;
    List<Integer> checkedItems;
    ListView WordsListView = null;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary_main);

        String[] words=getWords();
        String wordsstr=Arr2Text(words);
        System.out.println("Loaded: "+wordsstr);
        WordsListView = (ListView) findViewById(R.id.list);
        wordList=new ArrayList<String>();
        //wordList = new ArrayList<String>(Arrays.asList(words));
        //add words in alphabetic order
        if (words.length>0) {
            wordList.add(words[0]);
            for (int i = 1; i < words.length; i++) {
                int j = 0;
                for (j = 0; j < wordList.size(); ++j) {
                    if (wordList.get(j).compareToIgnoreCase(words[i]) > 0) {
                        break;
                    }
                }
                System.out.println("j: " + j);
                System.out.println("i: " + i);
                wordList.add(j, words[i]);
            }
        }

        checkedItems=new ArrayList<Integer>();
        do{
            checkedItems.add(0);
        }while(checkedItems.size()<wordList.size());
        wordAdapter = new ArrayAdapter<String>(main_glossary_activity.this, R.layout.list_item,R.id.txtview, wordList);

        btn2 = (ImageButton) findViewById(R.id.imageButton2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int wordlistsize=wordList.size();
                System.out.println("test"+wordlistsize);
                for (int i=0;i<WordsListView.getChildCount();i++) {//clear all selections
                    WordsListView.getChildAt(i).setBackgroundResource(R.drawable.list_selector_deselect);
                }

                for (int i=0;i<wordlistsize;i++){
                    if (checkedItems.get(i)==1){
                        removeword(wordList.get(i));
                        wordList.remove(i);
                        wordlistsize--;
                        checkedItems.remove(i);
                        wordAdapter.notifyDataSetChanged();
                        i--;
                        //btn2.setVisibility(View.INVISIBLE);
                        btn2.setEnabled(false);
                    }
                }
                for (int i=0;i<checkedItems.size();i++){//reset all check items
                    checkedItems.set(i,0);
                }
            }
        });

        //btn2.setVisibility(View.INVISIBLE);

        WordsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        WordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("CLICKED "+wordList.get(i));
                if (checkedItems.get(i)==0){
                    checkedItems.set(i,1);
                    view.setBackgroundResource(R.drawable.list_selector_select);
                }else{
                    checkedItems.set(i,0);
                    view.setBackgroundResource(R.drawable.list_selector_deselect);
                }
                System.out.println(i+" "+checkedItems.get(i));
                if (ListhasAny(checkedItems)) {
                    btn2.setEnabled(true);
                    //btn2.setVisibility(View.VISIBLE);
                }else {
                    btn2.setEnabled(false);
                    //btn2.setVisibility(View.INVISIBLE);
                }
            }
        });
Log.i("TEST","TEST");
        WordsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View v,int index, long arg3) {
                final AlertDialog.Builder b = new AlertDialog.Builder(main_glossary_activity.this);
                b.setIcon(android.R.drawable.ic_dialog_alert);
                final int i2=index;
                b.setMessage(wordList.get(i2));
                b.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int i) {

                        Intent intent=new Intent(main_glossary_activity.this,add_activity.class);
                        Log.i("word",wordList.get(i2));
                        intent.putExtra("word",i2+": "+wordList.get(i2)); //to edit
                        startActivityForResult(intent,1040);

                    }
                });
                b.show();
                return true;
            }
        });
        WordsListView.setAdapter(wordAdapter);

        //txtInput = (EditText) findViewById(R.id.editText);
        final ImageButton btn1 = (ImageButton) findViewById(R.id.imageButton);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(main_glossary_activity.this,add_activity.class);
                //intent.putExtra("word","0: test1: def1"); //to edit
                startActivityForResult(intent,1040);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (requestCode==1040){//1040=ADD_ACTIVITY
            if (resultCode== Activity.RESULT_OK){
                String word=data.getData().toString();
                String[] values=word.split("(: )");
                word="";
                for (int i=1;i<values.length;i++){
                    word+=values[i]+": ";
                }
                word=word.substring(0,word.length()-2); //trim :
                System.out.println(values[0]);//index
                int index=Integer.parseInt(values[0]);
                boolean alreadyhave=false;
                if (index<0){
                    //add regular
                    addWord(word);
                    int i=0;
                    Log.println(Log.DEBUG,"word",word);
                    for (i=0;i<wordList.size();++i) {
                        if (wordList.get(i).split(":")[0].compareToIgnoreCase(word.split(":")[0])>0){
                            break;
                        }else if (wordList.get(i).split(":")[0].compareToIgnoreCase(word.split(":")[0])==0) {
                            alreadyhave=true;
                            Log.println(Log.DEBUG,"Already had word",word);
                        }else{
                            Log.println(Log.DEBUG,"wordlist",i+" "+wordList.get(i)+" "+wordList.get(i).compareToIgnoreCase(word));
                        }
                    }
                    if (!alreadyhave) {
                        wordList.add(i, word);
                        checkedItems.add(0);
                        wordAdapter.notifyDataSetChanged();
                        Log.println(Log.DEBUG,"Added word",word);
                    }
                }else{
                    Log.i("EDIT","edit info "+index);
                    int wordlistsize=wordList.size();
                    for (int i=0;i<WordsListView.getChildCount();i++) {//clear all selections
                        WordsListView.getChildAt(i).setBackgroundResource(R.drawable.list_selector_deselect);
                    }
                    //remove
                        removeword(wordList.get(index));
                        wordList.remove(index);
                        wordlistsize--;
                        checkedItems.remove(index);
                        wordAdapter.notifyDataSetChanged();

                    //add
                    addWord(word);
                    int i=0;
                    Log.println(Log.DEBUG,"word",word);
                    for (i=0;i<wordList.size();++i) {
                        if (wordList.get(i).split(":")[0].compareToIgnoreCase(word.split(":")[0])>0){
                            break;
                        }else if (wordList.get(i).split(":")[0].compareToIgnoreCase(word.split(":")[0])==0) {
                            alreadyhave=true;
                            Log.println(Log.DEBUG,"Already had word",word); //should not happen
                        }else{
                            Log.println(Log.DEBUG,"wordlist",i+" "+wordList.get(i)+" "+wordList.get(i).compareToIgnoreCase(word));
                        }
                    }
                    if (!alreadyhave) {
                        wordList.add(i, word);
                        checkedItems.add(0);
                        wordAdapter.notifyDataSetChanged();
                        Log.println(Log.DEBUG,"Added word",word);
                    }
                    //edit index
                }
            }else{
                System.out.println("ERROR");
            }
        }
    };

}
