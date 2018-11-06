package com.csufjuststudy.ren.juststudy;

import android.content.Context;
import android.content.SharedPreferences;

public class DailyMission {
    private String name;
    private int id;
    private int progress;
    private int progressMax;
    private boolean complete;
    private boolean active;
    private static SharedPreferences sharedPref;

    public DailyMission(String name, int maxP, int id) {
        this.name = name;
        this.id = id;
        this.progress = 0;
        this.progressMax = maxP;
        this.complete = false;
        this.active = false;
    }

    public static void incProgress(Context context, String i) {
        sharedPref = context.getSharedPreferences("dailyMissionsPrefs", Context.MODE_PRIVATE);
        int j = Integer.parseInt(i)-1;
        SharedPreferences.Editor editor = sharedPref.edit();
        int currentProg = sharedPref.getInt(i, 0);
        if (!(currentProg >= DailyMissionsData.getProgressMax()[j])) {
            currentProg++;
        }
        editor.putInt(i, currentProg);
        editor.commit();
        //dailyMissions.get(j).updateProgress(currentProg);
    }

    public void updateProgress(int progress) {
        this.progress = progress;
    }

    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }

    public int getProgress() { return progress; }

    public int getProgressMax() { return progressMax; }

    public boolean getComplete() { return complete; }

    public void setComplete() { this.complete = true; }

    public boolean getActive() { return active; }

    public void setActive() { this.active = true; }
}
