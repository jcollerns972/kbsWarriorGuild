package kbsWarriorGuild.tasks;

import kbsWarriorGuild.Constants;
import kbsWarriorGuild.Task;
import kbsWarriorGuild.warriorMain;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;
import org.powbot.api.rt4.Prayer;

public class usePrayer extends Task {
    warriorMain main;
    public usePrayer(warriorMain main) {
        super();
        super.name = "using melee prayer";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return (Npcs.stream().interactingWithMe().isNotEmpty()
                && !Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE)
                && Prayer.prayerPoints() > 0)
                ||
                (!Players.local().interacting().valid()
                && !Players.local().healthBarVisible()
                && !Npcs.stream().interactingWithMe().isNotEmpty()
                && Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE));
    }

    @Override
    public void execute() {
        if(Npcs.stream().interactingWithMe().isNotEmpty()
                && !Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE)
                && Prayer.prayerPoints() > 0)
        {
            warriorMain.state("Clicking melee prayer...");
            if(!Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE))
            {
                Prayer.prayer(Prayer.Effect.PROTECT_FROM_MELEE, true);
                Condition.wait(() -> Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE), 100,20);
            }
        }
        if(!Players.local().interacting().valid()
                && !Players.local().healthBarVisible()
                && !Npcs.stream().interactingWithMe().isNotEmpty()
                && Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE))
        {
            warriorMain.state("Turning off melee prayer...");
            if(Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE))
            {
                Prayer.prayer(Prayer.Effect.PROTECT_FROM_MELEE, false);
                Condition.wait(() -> !Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE), 100,20);
            }
        }
    }
}
