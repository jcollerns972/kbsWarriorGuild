package kbsWarriorGuild.tasks;

import kbsWarriorGuild.*;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;
import org.powbot.mobile.script.ScriptManager;

public class withdrawSupplies extends Task {
    warriorMain main;
    public withdrawSupplies(warriorMain main) {
        super();
        super.name = "Withdraw Supplies";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Areas.WARRIOR_BANK_AREA.contains(Players.local())
        && Vars.get().needToBank
        && Bank.opened()
        && !Vars.get().needToLoot;
    }

    @Override
    public void execute() {
        if (!Util.checkIfArmourReady()) {
            String[] armourItems = {Constants.CHOSEN_FULL_HELM, Constants.CHOSEN_PLATEBODY, Constants.CHOSEN_PLATELEGS};
            for (String item : armourItems) {
                if (Inventory.stream().name(item).isEmpty()) {
                    if (Bank.stream().name(item).isEmpty()) {
                        warriorMain.state("Bank does not contain required items...");
                        ScriptManager.INSTANCE.stop();
                    } else {
                        Bank.withdraw(item, 1);
                        if (Condition.wait(() -> Inventory.stream().name(item).isNotEmpty(), 100, 50)) {
                            warriorMain.state("Successfully withdrawn " + item + "...");
                        }
                    }
                }
            }
            Util.checkIfReady();
        }
        if (!Util.checkIfFoodReady()) {
            int requiredFoodCount = 10;
            long availableFoodCount = Inventory.stream().name(Constants.food).count(true);
            if (availableFoodCount < requiredFoodCount) {
                long amountToWithdraw = requiredFoodCount - availableFoodCount;
                if (Bank.stream().name(Constants.food).count(true) < amountToWithdraw) {
                    warriorMain.state("Not enough food left... Shutting down.");
                    ScriptManager.INSTANCE.stop();
                } else {
                    warriorMain.state("Withdrawing " + amountToWithdraw + " " + Constants.food);
                    Bank.withdraw(Constants.food, (int) amountToWithdraw);
                }
            }
            Util.checkIfReady();
        }
        if (Constants.usePrayerPotion) {
            warriorMain.state("Withdrawing prayer potions...");

            if (!Util.checkIfPpotReady()) {
                int amountOfPrayerPots = Vars.get().amountOfPrayerPots;

                if (Bank.stream().name(Constants.PRAYER_POTION).count(true) < amountOfPrayerPots) {
                    warriorMain.state("Not enough ppots left... Shutting down.");
                    ScriptManager.INSTANCE.stop();
                } else {
                    warriorMain.state("Withdrawing " + amountOfPrayerPots + " " + Constants.PRAYER_POTION);
                    Bank.withdraw(Constants.PRAYER_POTION, amountOfPrayerPots);

                    if (Condition.wait(() -> Inventory.stream().nameContains("Prayer").action("Drink").count() > 0, 100, 50)) {
                        warriorMain.state("Finished withdrawing ppots...");
                    }
                }
                Util.checkIfReady();
            }
        }
    }
}
