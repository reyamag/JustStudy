package com.csufjuststudy.ren.juststudy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DailyMissions extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private final int dm_total = 3;
    //private int [] completeCounter = {0, 0, 0};
    public ArrayList<Integer> completeCounter = new ArrayList<>();

    public ArrayList<DailyMission> dailyMissions = new ArrayList<>();
    public Queue<DailyMission> dmContainer = new LinkedList<>();

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailymissions_main);
        sharedPref = getSharedPreferences("dailyMissionsPrefs", Context.MODE_PRIVATE);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        initMissions();
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int position = viewHolder.getAdapterPosition();
                mAdapter.notifyItemRemoved(position);
                completeCounter.remove(position);
                completeCounter.add(0);
                resetMission(Integer.toString(position+1));
                dailyMissions.remove(position);
                //completeCounter[position] = 0;
            }
            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
                int position = holder.getAdapterPosition();
                return completeCounter.get(position) == 0 ? 0 : super.getSwipeDirs(recyclerView, holder);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(getApplicationContext(), dailyMissions);
        mRecyclerView.setAdapter(mAdapter);

        /*
        Button button = (Button) findViewById(R.id.button_inc);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View hello) {
                for(int i = 0; i < dm_total; i++) {
                    incProgress(Integer.toString(i+1));
                    //clearProgress();
                }
            }
        });
        */
    }

    private void clearProgress() {
        SharedPreferences.Editor editor = sharedPref.edit();
        for(int i = 0; i < dm_total; i++) {
            editor.putInt(Integer.toString(i+1), 0);
            editor.commit();
        }
    }

    private void incProgress(String i) {
        int j = Integer.parseInt(i)-1;
        SharedPreferences.Editor editor = sharedPref.edit();
        int currentProg = sharedPref.getInt(i, 0);
        if (!(currentProg >= dailyMissions.get(j).getProgressMax())) {
            currentProg++;
        }
        editor.putInt(i, currentProg);
        editor.commit();
        //dailyMissions.get(j).updateProgress(currentProg);
    }

    private void checkComplete(String i) {
        int j = Integer.parseInt(i)-1;
        SharedPreferences.Editor editor = sharedPref.edit();
        int currentProg = sharedPref.getInt(i, 0);
        if (currentProg >= dailyMissions.get(j).getProgressMax()) {
            completeCounter.set(j, 1);
            dailyMissions.get(j).setComplete();
        }
    }

    private void resetMission(String i) {
        int j = Integer.parseInt(i)-1;
        dailyMissions.get(j).updateProgress(0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(i, 0);
        editor.commit();
    }

    private void initMissions() {
        for(int i = 0; i < dm_total; i++) {
            completeCounter.add(0);
        }
        String[] nameList = DailyMissionsData.getNames();
        int[] pMaxList = DailyMissionsData.getProgressMax();
        for(int i = 0 ; i < 5; i++) {
            dmContainer.add(new DailyMission(nameList[i], pMaxList[i], i+1));
        }
        while (dailyMissions.size() != dm_total) {
            dailyMissions.add(dmContainer.remove());
        }
        for(int i = 0; i < dm_total; i++) {
            int currentProg = sharedPref.getInt(Integer.toString(i+1), 0);
            dailyMissions.get(i).updateProgress(currentProg);
            checkComplete(Integer.toString(i+1));
        }
    }
}


