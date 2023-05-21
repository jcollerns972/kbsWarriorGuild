package kbsWarriorGuild.tasks;

import kbsWarriorGuild.Constants;
import kbsWarriorGuild.*;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class fightAnimation extends Task {
    warriorMain main;
    public fightAnimation(warriorMain main) {
        super();
        super.name = "Fighting Npc";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Npcs.stream().interactingWithMe().isNotEmpty()
                && Areas.WARRIOR_FIGHT_AREA.contains(Players.local())
                && !Util.needToBank
                && !Util.readyToFightAnimations
                && !Util.needToLoot;
    }

    @Override
    public void execute() {
        Npc animation = Npcs.stream().interactingWithMe().first();
        Util.fightingAnimation = true;
        if(animation.name().equals("Animated Mithril Armour"))
        {
            warriorMain.state("In combat with armour...");
            Condition.wait(() -> Players.local().healthBarVisible(),100,50);
            if(!Players.local().healthBarVisible())
            {
                animation.interact("Attack");
                Condition.wait(() -> Players.local().healthBarVisible(),100,50);
            }
//            if(animation.healthPercent() == 0 )
//            {
//                Vars.get().armours_killed++;
//                checkHp();
//                Util.needToLoot = true;
//            }
//            if(Players.local().healthPercent() <= 50)
//            {
//             checkHp();
//            }
        }
    }
    public void checkHp()
    {
        warriorMain.state("Low hp... Need to eat");
        if(Players.local().healthPercent() <= 50)
        {
            Item food = Inventory.stream().name(Constants.food).first();
            if(food.valid())
            {
                if(food.interact("Eat"))
                {
                    if(Condition.wait(()-> Players.local().healthPercent() > 50,50,20))
                    {
                        warriorMain.state("Food Eaten...");
                    }
                }
            }
        }
    }
}
