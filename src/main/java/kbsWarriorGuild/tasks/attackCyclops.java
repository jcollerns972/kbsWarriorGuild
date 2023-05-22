package kbsWarriorGuild.tasks;

import kbsWarriorGuild.*;
import org.powbot.api.Area;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;

public class attackCyclops extends Task {
    private warriorMain main;
    public attackCyclops(warriorMain main) {
        super();
        super.name = "Fighting Npc";
        this.main = main;
    }

    @Override
    public boolean activate() {
        if (Vars.get().readyToFightAnimations || Vars.get().needToBank || Vars.get().needToLoot)
            return false;
        if (Areas.DRAGON_GIANT_FIGHT_AREA.contains(Players.local())) {
            return Npcs.stream().interactingWithMe().isEmpty()
                    && !Players.local().interacting().valid()
                    && Vars.get().readyToFightDragonGiants;
        } else if (Areas.RUNE_GIANT_FIGHT_AREA.contains(Players.local())) {
            return Npcs.stream().interactingWithMe().isEmpty()
                    && !Players.local().interacting().valid()
                    && !Vars.get().needToResetRoom
                    && Vars.get().readyToFightRuneGiants;
        }
        return false;
    }

    @Override
    public void execute() {
        if (Areas.DRAGON_GIANT_FIGHT_AREA.contains(Players.local())) {
            attackCyclopsInArea(Areas.DRAGON_GIANT_FIGHT_AREA);
        } else if (Areas.RUNE_GIANT_FIGHT_AREA.contains(Players.local())) {
            attackCyclopsInArea(Areas.RUNE_GIANT_FIGHT_AREA);
        }
    }
    private void attackCyclopsInArea(Area area) {
        Npc cyclops = Npcs.stream().within(area).name("Cyclops").filtered(npc ->
                !npc.interacting().valid() && !npc.inCombat()).nearest().first();
        if (cyclops.valid()) {
            if (cyclops.inViewport()) {
                warriorMain.state("Attacking cyclops...");
                if (cyclops.interact("Attack")) {
                    Condition.wait(() -> Npcs.stream().interactingWithMe().isNotEmpty(), 100, 50);
                }
            } else {
                warriorMain.state("Walking to cyclops");
                Movement.step(cyclops);
                if (Condition.wait(cyclops::inViewport, 100, 50)) {
                    if (cyclops.interact("Attack")) {
                        warriorMain.state("Attacking cyclops...");
                        Condition.wait(() -> Npcs.stream().interactingWithMe().isNotEmpty(), 100, 50);
                    }
                }
            }
        }
    }
}
