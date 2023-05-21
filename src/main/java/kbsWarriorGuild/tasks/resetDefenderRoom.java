package kbsWarriorGuild.tasks;

import kbsWarriorGuild.*;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;

public class resetDefenderRoom extends Task {
    warriorMain main;
    public resetDefenderRoom(warriorMain main) {
        super();
        super.name = "resetting defenders...";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Util.needToResetRoom
                && Areas.RUNE_GIANT_FIGHT_AREA.contains(Players.local())
                && !Constants.runeCompleted;
    }

    @Override
    public void execute() {
        warriorMain.state("Need to reset room...");
        Movement.walkTo(Areas.RUNE_GIANT_WAIT_AREA.getRandomTile());
        if(Condition.wait(() -> Areas.RUNE_GIANT_WAIT_AREA.contains(Players.local()),100,50))
        {
            warriorMain.state("Successfully reset...");
            Util.needToResetRoom = false;
        }

    }
}
