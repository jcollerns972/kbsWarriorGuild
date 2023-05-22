package kbsWarriorGuild.tasks;

import kbsWarriorGuild.*;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;

public class walkToDragonGiantArea extends Task {
    warriorMain main;
    public walkToDragonGiantArea(warriorMain main) {
        super();
        super.name = "Walking to giant area";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return !Areas.DRAGON_GIANT_FIGHT_AREA.contains(Players.local())
                && !Vars.get().readyToFightAnimations
                && !Vars.get().needToLoot
                && !Vars.get().needToBank
                && Vars.get().readyToFightDragonGiants;
    }

    @Override
    public void execute() {
        warriorMain.state("Walking to giant Area...");
        Movement.walkTo(Areas.DRAGON_GIANT_FIGHT_AREA.getRandomTile());
    }
}
