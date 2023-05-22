package kbsWarriorGuild.tasks;

import kbsWarriorGuild.*;
import org.powbot.api.Condition;
import org.powbot.api.rt4.GroundItem;
import org.powbot.api.rt4.GroundItems;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;

public class lootDefenders extends Task {
    warriorMain main;
    public lootDefenders(warriorMain main) {
        super();
        super.name = "checking for defenders...";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return (Areas.RUNE_GIANT_FIGHT_AREA.contains(Players.local()) || Areas.DRAGON_GIANT_FIGHT_AREA.contains(Players.local()))
                && GroundItems.stream().nameContains("defender").isNotEmpty();
    }
    @Override
    public void execute() {
        Vars.get().needToLoot = true;
        GroundItem defender = GroundItems.stream().nameContains("defender").first();
        warriorMain.state("Looting defender...");
        if (defender.valid()) {
            defender.interact("Take");
            Vars.get().currentDefender = defender.name();
            Condition.wait(() -> !defender.valid(), 100, 50);
            Util.checkDefenderStatus();
        }
        if(!defender.valid()) //check ground for defenders...
        {
            warriorMain.state("Looted everything...");
            Util.checkIfReady();
            Vars.get().needToLoot = false;
            if(!Constants.runeCompleted)
            {
                Vars.get().needToResetRoom = true;
            }
        }
    }
}
