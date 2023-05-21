package kbsWarriorGuild.tasks;

import kbsWarriorGuild.Areas;
import kbsWarriorGuild.Task;
import kbsWarriorGuild.Util;
import kbsWarriorGuild.warriorMain;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;

public class walkToRuneGiantArea extends Task {
    warriorMain main;
    public walkToRuneGiantArea(warriorMain main) {
        super();
        super.name = "Walking to giant area";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return !Areas.RUNE_GIANT_FIGHT_AREA.contains(Players.local())
                && !Util.readyToFightAnimations
                && !Util.needToLoot
                && !Util.needToBank
                && !Util.needToResetRoom
                && Util.readyToFightRuneGiants;
    }

    @Override
    public void execute() {
        warriorMain.state("Walking to giant Area...");
        Movement.walkTo(Areas.RUNE_GIANT_FIGHT_AREA.getRandomTile());
        Condition.wait(() -> Areas.RUNE_GIANT_FIGHT_AREA.contains(Players.local()), 100,50);
    }
}
