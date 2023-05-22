package kbsWarriorGuild.tasks;

import kbsWarriorGuild.*;
import org.powbot.api.Condition;
import org.powbot.api.rt4.GroundItem;
import org.powbot.api.rt4.GroundItems;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;

public class lootTokens extends Task {
    warriorMain main;
    public lootTokens(warriorMain main) {
        super();
        super.name = "looting Items...";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Vars.get().needToLoot
                && Areas.WARRIOR_FIGHT_AREA.contains(Players.local())
                && !Players.local().healthBarVisible()
                && !Players.local().interacting().valid();
    }

    @Override
    public void execute() {
        GroundItem tokens = GroundItems.stream().name(Constants.WARRIOR_GUILD_TOKEN).first();
        warriorMain.state("Looting armours and tokens...");
        lootItem(Constants.CHOSEN_FULL_HELM);
        lootItem(Constants.CHOSEN_PLATEBODY);
        lootItem(Constants.CHOSEN_PLATELEGS);
        if (tokens.valid()) {
            if(tokens.interact("Take"))
            {
                Condition.wait(() -> !tokens.valid(), 100, 50);
                Vars.get().tokens_gained += tokens.stackSize();
            }
        }
        if(!tokens.valid() && Util.checkIfArmourReady())
        {
            warriorMain.state("Looted everything...");
            Util.checkIfReady();
            Vars.get().needToLoot = false;
            Vars.get().fightingAnimation = false;
        }
    }
    private void lootItem(String itemName){
        if (Inventory.stream().name(itemName).isEmpty()) {
            GroundItem item = GroundItems.stream().name(itemName).nearest().first();
            if (item.valid()) {
                if (item.interact("Take")) {
                    Condition.wait(() -> Inventory.stream().name(itemName).isNotEmpty(), 100, 50);
                }
            }
        }
    }
}
