package testScript.tasks;

import testScript.Task;
import testScript.testMain;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Player;
import org.powbot.api.rt4.Players;

public class playerDDTask extends Task {

    testMain main;

    public playerDDTask(testMain main) {
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
       // Player player = (Player) Players.local().interacting();
//        System.out.println(player.healthPercent() + "");
//        System.out.println(player.healthBarVisible() + "");
        //Npc banker = Npcs.stream().name("Banker").first();
//        if(banker.valid())
//        {
//            banker.interact("Bank");
//            Condition.sleep(2000);
//        }
    drawPlayer();
    }
    public void drawPlayer()
    {
        Tile tile = new Tile(3225,3220);
        if(Players.stream().anyMatch(i -> i.name().equals("Blak73")))
        {
            testMain.state("Player found!");
            Player player = Players.stream().name("Blak73").first();
            if (player.valid())
            {
                testMain.state("Player is valid");
                Condition.sleep(1000);
                testMain.state("Player model is not null...");
                //System.out.println("height is: " + );
                testMain.state("Drawing location");
                player.draw();
                Condition.sleep(1000);
//                if(player.interact("Trade with"))
//                {
//                    testMain.state("Sending trade to player...");
//                }
                //System.out.println("trying trade with null");
                //Players.stream().filtered(p->p.model()!=null).at(player).first().interact("Trade", "Blak73");
            }

        }
        Player player = Players.stream().name("Blak73").first();
        for(int i = 0;i < 100;)
        {
            player.draw();
            i++;
        }
    }
}
