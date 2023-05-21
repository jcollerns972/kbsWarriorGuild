package kbsWarriorGuild.tasks;

import kbsWarriorGuild.Areas;
import kbsWarriorGuild.Task;
import kbsWarriorGuild.Util;
import kbsWarriorGuild.warriorMain;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;

public class walkToBank extends Task {
    warriorMain main;
    public walkToBank(warriorMain main) {
        super();
        super.name = "Walking to bank";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return !Areas.WARRIOR_BANK_AREA.contains(Players.local())
        && Util.needToBank
        && !Util.needToLoot;
    }

    @Override
    public void execute() {
        warriorMain.state("Walking to bank...");
        Movement.walkTo(Areas.WARRIOR_BANK_AREA.getRandomTile());
        Condition.wait(() -> Areas.WARRIOR_BANK_AREA.contains(Players.local()), 100,50);
    }
}
