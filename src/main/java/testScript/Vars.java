package testScript;

import org.powbot.api.rt4.Npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Vars {

    public static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }
    final public ArrayList<Task> taskList = new ArrayList<>();
    public ArrayList<String> aggroList = new ArrayList<>();
    public ArrayList<String> reportList = new ArrayList<>();
    public ArrayList<String> spyList = new ArrayList<>();
    public static void reset() {
        vars = null;
    }
    public ArrayList<String> handlers = new ArrayList<>();
    public int times_died;
    public int times_dragged;
    public int players_killed;
    public int praySwitch = -1;
    public int setQuickPrayer = -1;
    public  Npc last_attacked_npc;
    public ArrayList<Integer> RANGE_WEAPONS1 = new ArrayList<>();
    public ArrayList<Integer> MAGE_WEAPONS1 = new ArrayList<>();
    public ArrayList<Integer> LONGRANGED_WEAPONS = new ArrayList<>();
    public static final int[] RANGE_WEAPONS = new int[] {
            9185, 11785, 12926, 19478,
            19481, 8880, 861, 12788,
            4734, 4934, 4935, 4936,
            4937, 859, 4212, 4214,
            4215, 4216, 4217, 4218,
            4219, 4220, 4221, 4222,
            4223, 10156, 11235, 11748,
            11749, 11750, 11751, 11752,
            11753, 11754, 11755, 11756,
            11757, 11758, 12424, 12757,
            12759, 12761, 12763, 12768,
            20997, 21012, 6742,21902,26237,
            26374,25865,25867,25869,25884,25886,
            25888,25890,25892,25894,25896,27187,
            25862,853,841,843,849,837,767,26486
    };
    public static final int[] MELEE_WEAPONS = new int[] {
            4755, 4982, 4983, 4984, 4985,
            11802, 11804, 11806, 11808,
            4587, 20000, 20002,
            1215, 1231, 5680, 5698,
            4151, 12773, 12774, 13265,
            13267, 13269, 13271, 12006,
            4726, 4910, 4911, 4912,
            4913, 4718, 4886, 4887,
            4888, 4889, 13263, 4747,
            4958, 4959, 4960, 4961,
            6528, 11824, 11889, 3204,
            1434, 1305, 13652, 20601,
            13576, 4153, 3176, 1249,
            1263, 5716, 5730, 10887,
            10148, 10146, 10147,
            20368,10581,11838,21003,20727,20374,
            11902,20727,35,21009,11791,1333
    };
    public static final int[] MAGIC_WEAPONS = new int[] {
            10146, 10147, 20431,
            20739, 20736, 20733, 20730,
            1381, 1383, 1385, 1387,
            1393, 1395, 1397, 1399,
            1409, 2415, 2416, 2417,
            3053, 3054, 4710,
            4865, 4864, 4863, 4862,
            6562, 6563, 11787, 11789,
            11998, 12000, 12795, 12796,
            12798, 20604, 21006, 12899,
            24424,24422,22370,22368,22370
    };
    public static final int[] LONGRANGEDWEAPONS = new int[]{
            10146, 10147, 20431,
            20739, 20736, 20733, 20730,
            1381, 1383, 1385, 1387,
            1393, 1395, 1397, 1399,
            1409, 2415, 2416, 2417,
            3053, 3054, 4710,
            4865, 4864, 4863, 4862,
            6562, 6563, 11787, 11789,
            11998, 12000, 12795, 12796,
            12798, 20604, 21006, 12899,
            24424,24422,22370,22368,22370,
            10146, 10147, 20431,
            20739, 20736, 20733, 20730,
            1381, 1383, 1385, 1387,
            1393, 1395, 1397, 1399,
            1409, 2415, 2416, 2417,
            3053, 3054, 4710,
            4865, 4864, 4863, 4862,
            6562, 6563, 11787, 11789,
            11998, 12000, 12795, 12796,
            12798, 20604, 21006, 12899,
            24424,24422,22370,22368,22370,
            12904,12902
    };

        public static final int[] ARMOURS = new int[] {
                1123,1127,1113,2615,2623,20421,23209,23212,23215,23218,23221,3481,20149
        };
    public static final String[] gaychecka_names = new String[]
            {
                    "g checka","gchecka haha","gchecka xd",
                    "g checkaaa","set pace309","sho0k","hahaaahaaaah",
                    "lzlzizlzllzi","schmr","schooling","blaks dad",
                    "gmg393848484","dont slack82","cscscs min"
                    ,"blak48","fat shag","gay tornado"

            };
    public static final String[] leonard_names = new String[]
            {
                    "non stopping", "forced you","facedesked","screenstarer","click u"
            };

    public static final int[][] SPECIAL_WEAPONS = new int[][] {
            {13271,25},{21902,60},{26219,25}
    };
    public static final int[] potNames = new int[]{
            9739,9741,9743,9745
    };
    public static final Map<Integer,Integer> specDict = new HashMap<>();

    public static final int[] animation_ids = new int[] {
            1162,-1,1156,1576,4177,420,811,7855,1167
    };
    public static final int[] mage_anims = new int[] {
            1576,1162,1167,7855,811
    };
}
