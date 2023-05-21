package testScript.tasks;

import testScript.testMain;
import testScript.*;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Player;
import org.powbot.api.rt4.Players;

public class movePathTest extends Task {
    testMain main;

    public movePathTest(testMain main) {
        super();
        super.name = "ddtask";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Players.local().valid();
    }

    @Override
    public void execute() {
        Player player = Players.stream().interactingWithMe().first();
        if(player.valid())
        {
            System.out.println("spotAnimationCycle is: " + player.spotAnimationCycle());
            System.out.println("timeSinceAnimationChange is: " + player.timeSinceAnimationChange());
            System.out.println("hitsplatCycles is: " + player.hitsplatCycles());
            System.out.println("local().hitsplatCycles is: " + Players.local().hitsplatCycles());
            System.out.println("hashCode is: " + player.hashCode());
            System.out.println("isStaminaActive is: " + player.isStaminaActive());
            System.out.println("timeSinceAnimationChange is: " + Players.local().timeSinceAnimationChange());
            //System.out.println("animationDelay is: " + player.);

        }

    }
    public void drawPlayer()
    {
        if(Players.stream().anyMatch(i -> i.name().equals("parmaham18")))
        {
            testMain.state("Player found!");
            Player player = Players.stream().name("parmaham18").first();
            if (player.valid())
            {
                testMain.state("Player is valid");
                Condition.sleep(1000);
                testMain.state("Player model is: " + player.get_model());
                Condition.sleep(1000);
                testMain.state("Drawing location");
                drawPlayer();
                Condition.sleep(1000);
                if(player.interact("Trade with"))
                {
                    testMain.state("Sending trade to player...");
                }
            }
        }
        Player player = Players.stream().name("parmaham18").first();
        for(int i = 0;i < 100;)
        {
            player.draw();
            i++;
        }
    }
}
