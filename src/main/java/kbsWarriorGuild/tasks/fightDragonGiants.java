package kbsWarriorGuild.tasks;

import kbsWarriorGuild.Areas;
import kbsWarriorGuild.Task;
import kbsWarriorGuild.Util;
import kbsWarriorGuild.warriorMain;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;

public class fightDragonGiants extends Task {
    warriorMain main;
    public fightDragonGiants(warriorMain main) {
        super();
        super.name = "Fighting Npc";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Npcs.stream().interactingWithMe().isEmpty()
                && Areas.DRAGON_GIANT_FIGHT_AREA.contains(Players.local())
                && !Players.local().interacting().valid()
                && !Util.needToBank
                && !Util.readyToFightAnimations
                && !Util.needToLoot
                && Util.readyToFightDragonGiants;
    }

    @Override
    public void execute() {
       // Npc cyclops = Npcs.stream().within(Areas.GIANT_FIGHT_AREA).filtered(i -> i.!cyclops.inCombat()).first(); //within(Players.local(), 10).name("Cyclops").first();
        Npc cyclops = Npcs.stream().within(Areas.DRAGON_GIANT_FIGHT_AREA).name("Cyclops").filtered(npc
                -> npc.valid()
                && !npc.inCombat()).nearest().first();
        if(cyclops.valid())
        {
            if(cyclops.inViewport())
            {
                warriorMain.state("Attacking cyclops...");
                if(cyclops.interact("Attack"))
                {
                    Condition.wait(() -> Npcs.stream().interactingWithMe().isNotEmpty(),100,50);
                }
            } else
            {
                warriorMain.state("Walking to cyclops");
                Movement.step(cyclops);
                if(Condition.wait(() -> cyclops.inViewport(),100,50))
                {
                    if(cyclops.interact("Attack"))
                    {
                        warriorMain.state("Attacking cyclops...");
                        Condition.wait(() -> Npcs.stream().interactingWithMe().isNotEmpty(),100,50);
                    }
                }
            }
        }
    }
}
