package kbsWarriorGuild;

import java.util.ArrayList;

public class Vars {
    public static Vars vars;
    public int last_attacked_npc;
    public int armours_killed;
    public int cyclops_killed;
    public int tokens_gained;
    public long total_tokens;
    public int tokens_to_fight_giant = 750;
    public String currentDefender;
//    public static final Map<Integer,Integer> specDict = new HashMap<>();
//    public static void addSpecWeps()
//    {
//        Vars.specDict.put(27690,50);
//        Vars.specDict.put(13271,25);
//        Vars.specDict.put(21902,60);
//        Vars.specDict.put(26219,25);
//        Vars.specDict.put(12788,50);
//    }

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }
    public static void reset() {
        vars = null;
    }
    final public ArrayList<Task> taskList = new ArrayList<>();
    public int times_died;
}
