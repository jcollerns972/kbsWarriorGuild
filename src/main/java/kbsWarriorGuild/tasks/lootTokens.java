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
        return Util.needToLoot
                && Areas.WARRIOR_FIGHT_AREA.contains(Players.local())
                && !Players.local().healthBarVisible()
                && !Players.local().interacting().valid();
    }

    @Override
    public void execute() {
        GroundItem tokens = GroundItems.stream().name(Constants.WARRIOR_GUILD_TOKEN).first();
        warriorMain.state("Looting armours and tokens...");
        if (!Inventory.stream().name(Constants.CHOSEN_FULL_HELM).first().valid()) {
            GroundItem helm = GroundItems.stream().name(Constants.CHOSEN_FULL_HELM).first();
            if (helm.valid()) {
                helm.interact("Take");
                Condition.wait(() -> Inventory.stream().name(Constants.CHOSEN_FULL_HELM).first().valid(), 100, 50);
            }
        }
        if (!Inventory.stream().name(Constants.CHOSEN_PLATEBODY).first().valid()) {
            GroundItem body = GroundItems.stream().name(Constants.CHOSEN_PLATEBODY).first();
            if (body.valid()) {
                body.interact("Take");
                Condition.wait(() -> Inventory.stream().name(Constants.CHOSEN_PLATEBODY).first().valid(), 100, 50);
            }
        }
        if (!Inventory.stream().name(Constants.CHOSEN_PLATELEGS).first().valid()) {
            GroundItem legs = GroundItems.stream().name(Constants.CHOSEN_PLATELEGS).first();
            if (legs.valid()) {
                legs.interact("Take");
                Condition.wait(() -> Inventory.stream().name(Constants.CHOSEN_PLATELEGS).first().valid(), 100, 50);
            }
        }
        if (tokens.valid()) {
            tokens.interact("Take");
            Vars.get().tokens_gained += tokens.stackSize();
            Condition.wait(() -> !tokens.valid(), 100, 50);
        }
        Util.checkIfArmourReady();

        if(!tokens.valid() && Util.HasArmour)
        {
            warriorMain.state("Looted everything...");
            Util.checkIfReady();
            Util.needToLoot = false;
            Util.fightingAnimation = false;
        }

    }
}
