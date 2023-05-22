package kbsWarriorGuild.tasks;

import kbsWarriorGuild.*;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;

public class walkToAnimatorArea extends Task {
    warriorMain main;
    public walkToAnimatorArea(warriorMain main) {
        super();
        super.name = "Walking to animator area";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return !Areas.WARRIOR_FIGHT_AREA.contains(Players.local())
                && Vars.get().readyToFightAnimations
                && !Vars.get().needToLoot
                && !Vars.get().needToBank
                && !Vars.get().readyToFightRuneGiants;
    }

    @Override
    public void execute() {
        warriorMain.state("Walking to Fight Area...");
        Movement.walkTo(Areas.WARRIOR_ANIMATOR_AREA.getRandomTile());
    }
}
