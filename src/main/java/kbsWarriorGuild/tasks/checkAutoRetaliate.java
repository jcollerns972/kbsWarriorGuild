package kbsWarriorGuild.tasks;

import kbsWarriorGuild.Constants;
import kbsWarriorGuild.Task;
import kbsWarriorGuild.Util;
import kbsWarriorGuild.warriorMain;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class checkAutoRetaliate extends Task {
    warriorMain main;
    public checkAutoRetaliate(warriorMain main) {
        super();
        super.name = "eating";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return !Combat.autoRetaliate();
    }

    @Override
    public void execute() {
        warriorMain.state("Turning auto retaliate on...");
        if(Combat.autoRetaliate(true))
        {
            Condition.wait(Combat::autoRetaliate, 100,50);
            System.out.println("Auto retaliate has been turned on....");
            Game.closeOpenTab();
        }
    }
}
