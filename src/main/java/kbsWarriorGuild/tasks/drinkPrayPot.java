package kbsWarriorGuild.tasks;

import kbsWarriorGuild.Areas;
import kbsWarriorGuild.Task;
import kbsWarriorGuild.Util;
import kbsWarriorGuild.warriorMain;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class drinkPrayPot extends Task {
    warriorMain main;
    public drinkPrayPot(warriorMain main) {
        super();
        super.name = "drinking prayer potion";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Prayer.prayerPoints() < 12
                && Inventory.stream().nameContains("Prayer potion").isNotEmpty();
    }

    @Override
    public void execute() {
            warriorMain.state("Low prayer... Need to drink");
            Item ppot = Inventory.stream().nameContains("Prayer").action("Drink").first();
            if(ppot.valid())
            {
                if(ppot.interact("Drink"))
                {
                    if(Condition.wait(()-> Prayer.prayerPoints() > 25,50,20))
                    {
                        warriorMain.state("Drank ppot...");
                    }
                    if (Areas.RUNE_GIANT_FIGHT_AREA.contains(Players.local())
                            || Areas.DRAGON_GIANT_FIGHT_AREA.contains(Players.local())) {
                        Util.checkIfReady();
                    }
                }
            }
    }
}
