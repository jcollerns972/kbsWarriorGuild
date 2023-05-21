package kbsWarriorGuild.tasks;

import kbsWarriorGuild.Constants;
import kbsWarriorGuild.Task;
import kbsWarriorGuild.Util;
import kbsWarriorGuild.warriorMain;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Combat;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;

public class specialAttack extends Task {
    warriorMain main;
    public specialAttack(warriorMain main) {
        super();
        super.name = "Using special attack";
        this.main = main;
    }

    @Override
    public boolean activate() {
          return Players.local().valid()
                && Players.local().interacting().valid()
                && Players.local().healthPercent() > 0
                && !Combat.specialAttack();
               // && Combat.specialPercentage() >= Vars.specDict.get(Players.local().appearance()[3]);
    }

    @Override
    public void execute() {

    }
}
