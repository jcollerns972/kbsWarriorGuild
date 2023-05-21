package kbsWarriorGuild.tasks;

import kbsWarriorGuild.*;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;

public class checkTokens extends Task {
    warriorMain main;
    public checkTokens(warriorMain main) {
        super();
        super.name = "checking token amount";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Vars.get().total_tokens != Inventory.stream().name(Constants.WARRIOR_GUILD_TOKEN).count(true);
    }

    @Override
    public void execute() {
        Vars.get().total_tokens = Inventory.stream().name(Constants.WARRIOR_GUILD_TOKEN).count(true);
        Condition.wait(() -> Vars.get().total_tokens
                == Inventory.stream().name(Constants.WARRIOR_GUILD_TOKEN).count(true),100,50);
        if(Inventory.stream().name(Constants.WARRIOR_GUILD_TOKEN).first().valid())
        {
            if(Inventory.stream().name(Constants.WARRIOR_GUILD_TOKEN).count(true) < 50)
            {
                if(Util.hasEnoughTokens){
                    Util.hasEnoughTokens = false;
                }
            }
        }
        if(Inventory.stream().name(Constants.WARRIOR_GUILD_TOKEN).first().valid())
        {
            if(Inventory.stream().name(Constants.WARRIOR_GUILD_TOKEN).count(true) > Vars.get().tokens_to_fight_giant)
            {
                if(!Util.hasEnoughTokens)
                {
                    Util.hasEnoughTokens = true;
                }
            }
        }
    }
}
