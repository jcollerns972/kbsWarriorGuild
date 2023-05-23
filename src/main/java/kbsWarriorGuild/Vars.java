package kbsWarriorGuild;

import java.util.ArrayList;

public class Vars {
    public static Vars vars;
    public int amountOfPrayerPots;
    public int last_attacked_npc;
    public int armours_killed;
    public int cyclops_killed;
    public int tokens_gained;
    public long total_tokens;
    public String currentDefender;
    public boolean hasEnoughTokens;
    public boolean needToBank;
    public boolean readyToFightRuneGiants;
    public boolean readyToFightDragonGiants;
    public boolean readyToFightAnimations;
    public boolean needToLoot;
    public boolean needToResetRoom;
    public boolean fightingAnimation;
    public long last_time_animated;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }
    public static void reset() {
        vars = null;
    }
    final public ArrayList<Task> taskList = new ArrayList<>();
}
