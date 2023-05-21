package kbsWarriorGuild.tasks;

import kbsWarriorGuild.Areas;
import kbsWarriorGuild.Task;
import kbsWarriorGuild.Util;
import kbsWarriorGuild.warriorMain;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.mobile.script.ScriptManager;

public class useAnimator extends Task {
    warriorMain main;
    public useAnimator(warriorMain main) {
        super();
        super.name = "Using animator";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Util.readyToFightAnimations
                && Areas.WARRIOR_FIGHT_AREA.contains(Players.local())
                && !Util.needToBank
                && Npcs.stream().interactingWithMe().isEmpty()
                && !Util.needToLoot
                && !Util.readyToFightRuneGiants;
    }

    @Override
    public void execute() {
        GameObject animator = Objects.stream().name("Magical Animator").first();
        if(animator.valid())
        {
            if(animator.inViewport())
            {
                warriorMain.state("Interacting with animator...");
                if(animator.interact("Animate"))
                {
                    if(Condition.wait(() ->Npcs.stream().interactingWithMe().isNotEmpty(),100,50))
                    {
                        warriorMain.state("In combat with armour....");
                        Util.readyToFightAnimations = false;
                    }
                }
            } else
            {
                warriorMain.state("Turning camera to animator...");
                Camera.turnTo(animator);
                Movement.step(animator);
                Condition.wait(animator::inViewport,100,50);
                //Condition.wait(animator::inViewport,100,50);
            }
        } else
        {
            warriorMain.state("Animator is not valid... Something has gone wrong... Shutting down.");
            ScriptManager.INSTANCE.stop();
        }

    }
}
