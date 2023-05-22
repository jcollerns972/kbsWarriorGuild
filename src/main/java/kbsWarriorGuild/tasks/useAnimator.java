package kbsWarriorGuild.tasks;

import kbsWarriorGuild.*;
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
        return Vars.get().readyToFightAnimations
                && Areas.WARRIOR_FIGHT_AREA.contains(Players.local())
                && !Vars.get().needToBank
                && Npcs.stream().interactingWithMe().isEmpty()
                && !Vars.get().needToLoot
                && !Vars.get().readyToFightRuneGiants;
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
                    //check in combat with animation just incase random event spawns and interacts and fucks everything
                    if(Condition.wait(() -> Npcs.stream().interactingWithMe().list().toString().contains("Animated"),100,100))
                    {
                        warriorMain.state("In combat with armour....");
                        Vars.get().readyToFightAnimations = false;
                    }
                }
            } else
            {
                warriorMain.state("Turning camera to animator...");
                Camera.turnTo(animator);
                Movement.step(animator);
                Condition.wait(animator::inViewport,100,50);
            }
        } else
        {
            warriorMain.state("Animator is not valid... Something has gone wrong... Shutting down.");
            ScriptManager.INSTANCE.stop();
        }

    }
}
