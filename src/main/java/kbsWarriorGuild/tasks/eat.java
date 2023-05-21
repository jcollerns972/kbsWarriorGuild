package kbsWarriorGuild.tasks;

import kbsWarriorGuild.Constants;
import kbsWarriorGuild.Task;
import kbsWarriorGuild.Util;
import kbsWarriorGuild.warriorMain;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class eat extends Task {
    warriorMain main;
    public eat(warriorMain main) {
        super();
        super.name = "eating";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return  Players.local().healthBarVisible()
                && Players.local().healthPercent() <= 50;
    }

    @Override
    public void execute() {
            warriorMain.state("Low hp... Need to eat");
            Item food = Inventory.stream().name(Constants.food).first();
            if(food.valid())
            {
                if(food.interact("Eat"))
                {
                    if(Condition.wait(()-> Players.local().healthPercent() > 50,50,20))
                    {
                        warriorMain.state("Food Eaten...");
                        if(Npcs.stream().interactingWithMe().isNotEmpty())
                        {
                            Npc npc = Npcs.stream().interactingWithMe().first();
                            npc.interact("Attack");
                        }
                    }
                    Util.checkIfReady();
                }
            }
    }
}
