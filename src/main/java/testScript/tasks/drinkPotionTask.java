package testScript.tasks;

import com.google.errorprone.annotations.Var;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;
import testScript.Task;
import testScript.testMain;

public class drinkPotionTask extends Task {
    testMain main;
    public drinkPotionTask(testMain main) {
        super();
        super.name = "Drinking Potion";
        this.main = main;
    }
    @Override
    public boolean activate() {
       return Players.local().valid();
    }

    @Override
    public void execute() {
        Npc man = Npcs.stream().name("Man").nearest().first();
        if(man.valid())
        {
            long currTime = System.currentTimeMillis();
            if(man.interact("Attack", true))
            {
                System.out.println("number is: " + (System.currentTimeMillis() - currTime));

            }
        }

    }
}
