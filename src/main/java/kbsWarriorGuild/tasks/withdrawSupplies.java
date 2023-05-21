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
        && Util.needToBank
        && Bank.opened()
        && !Util.needToLoot;
    }

    @Override
    public void execute() {
        if(!Util.HasArmour)
        {
            if(!Inventory.stream().name(Constants.CHOSEN_FULL_HELM).first().valid())
            {
               if(!Bank.stream().name(Constants.CHOSEN_FULL_HELM).first().valid())
                {
                    warriorMain.state("Bank does not contain required items...");
                    ScriptManager.INSTANCE.stop();
                } else
               {
                   Bank.withdraw(Constants.CHOSEN_FULL_HELM, 1);
                   if(Condition.wait(()-> Inventory.stream().name(Constants.CHOSEN_FULL_HELM).first().valid(),100,50))
                   {
                       warriorMain.state("Successfully withdrawn Full helm...");
                   }
               }
            }
            if(!Inventory.stream().name(Constants.CHOSEN_PLATEBODY).first().valid())
            {
                if(!Bank.stream().name(Constants.CHOSEN_PLATEBODY).first().valid())
                {
                    warriorMain.state("Bank does not contain required items...");
                    ScriptManager.INSTANCE.stop();
                } else
                {
                    Bank.withdraw(Constants.CHOSEN_PLATEBODY, 1);
                    if(Condition.wait(()-> Inventory.stream().name(Constants.CHOSEN_PLATEBODY).first().valid(),100,50))
                    {
                        warriorMain.state("Successfully withdrawn Platebody...");
                    }
                }
            }
            if(!Inventory.stream().name(Constants.CHOSEN_PLATELEGS).first().valid())
            {
                if(!Bank.stream().name(Constants.CHOSEN_PLATELEGS).first().valid())
                {
                    warriorMain.state("Bank does not contain required items...");
                    ScriptManager.INSTANCE.stop();
                } else
                {
                    Bank.withdraw(Constants.CHOSEN_PLATELEGS, 1);
                    if(Condition.wait(()-> Inventory.stream().name(Constants.CHOSEN_PLATELEGS).first().valid(),100,50))
                    {
                        warriorMain.state("Successfully withdrawn Platelegs...");
                    }
                }
            }
            Util.checkIfReady();
        }
        if(!Util.HasFood)
        {
            if(Inventory.stream().name(Constants.food).first().valid())
            {
                warriorMain.state("Has food... Just not enough... Grabbing the rest");
                long amountOfFood = Inventory.stream().name(Constants.food).count(true);
                long amountToWithdraw = 10 - amountOfFood;
                warriorMain.state("Food left: " + Bank.stream().name(Constants.food).count());
                if(Bank.stream().name(Constants.food).count(true) < amountToWithdraw)
                {
                    warriorMain.state("Not enough food left... Shutting down.");
                    ScriptManager.INSTANCE.stop();
                } else
                {
                    warriorMain.state("Withdrawing " + amountToWithdraw + " " + Constants.food);
                    Bank.withdraw(Constants.food, (int) amountToWithdraw);
                }
            } else
            {
                if(Bank.stream().name(Constants.food).count(true) < 10)
                {
                    warriorMain.state("Not enough food left... Shutting down.");
                    ScriptManager.INSTANCE.stop();
                } else
                {
                    warriorMain.state("Withdrawing 10 " + Constants.food);
                    Bank.withdraw(Constants.food, Bank.Amount.TEN);
                }
            }
            Util.checkIfReady();
        }
        if(Constants.usePrayerPotion)
        {
            warriorMain.state("Withdrawing prayer potions...");
            if(!Util.HasPPot)
            {
                if(Bank.stream().name(Constants.PRAYER_POTION).count(true) < Constants.amountOfPrayerPots)
                {
                    warriorMain.state("Not enough ppots left... Shutting down.");
                    ScriptManager.INSTANCE.stop();
                } else
                {
                    warriorMain.state("Withdrawing " + Constants.amountOfPrayerPots + " " + Constants.PRAYER_POTION);
                    Bank.withdraw(Constants.PRAYER_POTION, Constants.amountOfPrayerPots);
                    if(Condition.wait(()-> Inventory.stream().nameContains("Prayer").action("Drink").count() > 0,100,50))
                    {
                        Util.HasPPot = true;
                    }
                }
                Util.checkIfReady();
            }
        }
    }
}
