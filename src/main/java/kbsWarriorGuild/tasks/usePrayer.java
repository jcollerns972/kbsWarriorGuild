package kbsWarriorGuild.tasks;

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
        boolean interactingWithNpc = Npcs.stream().interactingWithMe().isNotEmpty();
        boolean meleePrayerActive = Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE);
        boolean hasPrayerPoints = Prayer.prayerPoints() > 0;
        boolean playerNotInteracting = !Players.local().interacting().valid();
        boolean healthBarNotVisible = !Players.local().healthBarVisible();

        return (interactingWithNpc && !meleePrayerActive && hasPrayerPoints)
                || (playerNotInteracting && healthBarNotVisible && !interactingWithNpc && Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE));
    }

    @Override
    public void execute() {
        boolean interactingWithNpc = Npcs.stream().interactingWithMe().isNotEmpty();
        boolean meleePrayerActive = Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE);
        boolean playerNotInteracting = !Players.local().interacting().valid();
        boolean healthBarNotVisible = !Players.local().healthBarVisible();
        if (interactingWithNpc && !meleePrayerActive && Prayer.prayerPoints() > 0) {
            warriorMain.state("Clicking melee prayer...");
            if (!Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE)) {
                Prayer.prayer(Prayer.Effect.PROTECT_FROM_MELEE, true);
                Condition.wait(() -> Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE), 100, 20);
            }
        }
        if (playerNotInteracting && healthBarNotVisible && !interactingWithNpc && Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE)) {
            warriorMain.state("Turning off melee prayer...");
            if (Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE)) {
                Prayer.prayer(Prayer.Effect.PROTECT_FROM_MELEE, false);
                Condition.wait(() -> !Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE), 100, 20);
            }
        }
    }
}
