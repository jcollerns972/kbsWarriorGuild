package kbsWarriorGuild;

import org.powbot.api.Area;
import org.powbot.api.Tile;

public class Areas {
    public static Area DRAGON_GIANT_FIGHT_AREA =  new Area(new Tile(2912, 9973, 0), new Tile(2940, 9957, 0));;
    public static Area RUNE_GIANT_FIGHT_AREA = new Area(
        new Tile(2851, 3555, 2),
        new Tile(2872, 3555, 2),
        new Tile(2875, 3551, 2),
        new Tile(2875, 3538, 2),
        new Tile(2859, 3538, 2),
        new Tile(2859, 3535, 2),
        new Tile(2850, 3535, 2),
        new Tile(2847, 3544, 2),
        new Tile(2838, 3544, 2),
        new Tile(2838, 3555, 2)
); ;

    public static Area WARRIOR_GUILD_AREA = new Area(new Tile(2838, 3555, 0), new Tile(2876, 3534, 0));
    public static Area RUNE_GIANT_WAIT_AREA = new Area(new Tile(2839, 3541, 2), new Tile(2845, 3539, 2));
    public static Area DRAGON_GIANT_WAIT_AREA = new Area(new Tile(2905, 9973, 0), new Tile(2910, 9967, 0));
    public static Area WARRIOR_FIGHT_AREA = new Area(new Tile(2849, 3545, 0), new Tile(2859, 3534, 0));
    public static Area WARRIOR_BANK_AREA = new Area(new Tile(2843, 3545, 0), new Tile(2847, 3537, 0));
    public static Area WARRIOR_ANIMATOR_AREA = new Area(new Tile(2852, 3540, 0), new Tile(2850, 3544, 0));
}
