package kbsWarriorGuild.tasks;

import kbsWarriorGuild.*;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;
public class fightAnimation extends Task {
    warriorMain main;
    public fightAnimation(warriorMain main) {
        super();
        super.name = "fighting stuck animation";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return Areas.WARRIOR_ANIMATOR_AREA.contains(Players.local())
                && !Players.local().interacting().valid()
                && !Players.local().healthBarVisible()
                && Npcs.stream().interactingWithMe().nameContains("Animated").isNotEmpty()
                && System.currentTimeMillis() - Vars.get().last_time_animated > 3000;
    }

    @Override
    public void execute() {
       warriorMain.state("Npc stuck behind player/object...");
       Npc animation = Npcs.stream().interactingWithMe()
               .nameContains("Animated").first();
       if(animation.interact("Attack"))
       {
           warriorMain.state("Attacking animation...");
           Condition.wait(animation::healthBarVisible, 100,20);
       }
    }
}
