package com.csufjuststudy.ren.juststudy;

public final class DailyMissionsData {
    public static String[] getNames() {
        return new String [] {
                "Complete 1 Quiz",
                "Answer 5 questions correctly on the quiz",
                "Complete 2 Timed Matchmaking Games",
                "Add 2 glossary terms",
                "Add a flashcard"
        };
    }
    public static int[] getProgressMax() {
        return new int[] {
                1,
                5,
                2,
                2,
                1
        };
    }
}
