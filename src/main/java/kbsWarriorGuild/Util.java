package kbsWarriorGuild;

import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;
import org.powbot.mobile.script.ScriptManager;

import java.util.Objects;

public class Util {

    public static boolean needToBank;
    public static boolean readyToFightAnimations;
    public static boolean readyToFightRuneGiants;
    public static boolean readyToFightDragonGiants;
    public static boolean needToLoot;
    public static boolean HasArmour;
    public static boolean HasFood;
    public static boolean HasPPot;
    public static boolean hasEnoughTokens;
    public static boolean checkForDefenders;
    public static boolean needToResetRoom;
    public static boolean fightingAnimation;

    public static void handSlot()
    {
        return ;
    }
    public static void shieldSlot()
    {
        return ;
    }
    public static void checkEnoughTokens()
    {
        warriorMain.state("Checking if we have enough tokens to fight Giants");
        if(Inventory.stream().name(Constants.WARRIOR_GUILD_TOKEN).first().valid())
        {
            if(Inventory.stream().name(Constants.WARRIOR_GUILD_TOKEN).first().stackSize() > Vars.get().tokens_to_fight_giant)
            {
                warriorMain.state("We have enough tokens...");
                hasEnoughTokens = true;
            } else
            {
                warriorMain.state("We do not have enough tokens...");
                hasEnoughTokens = false;
            }
        }
    }
    public static void checkPlayer()
    {
        if(Players.local().valid())
        {
            warriorMain.state("Player valid!");
        } else
        {
            warriorMain.state("Player not valid, waiting for player to become valid...");
            if(Condition.wait(() -> Players.local().valid(),1000,50))
            {
                warriorMain.state("Player is now valid...");
            }
        }
    }

    public static void checkLocation() {
//        if(Areas.WARRIOR_GUILD_AREA.contains(Players.local()))
//        {
//            warriorMain.state("Area check valid...");
//        } else
//        {
//            warriorMain.state("Player not in correct area... shutting down!");
//            ScriptManager.INSTANCE.stop();
//        }
    }

//    public static void checkIfUseFood() {
//        if(!Objects.equals(Constants.food, ""))
//        {
//            Constants.useFood = true;
//        }
//    }

//    public static void checkIfUsePrayerPotion() {
//        if(Constants.amountOfPrayerPots != null)
//        {
//            Constants.usePrayerPotion = true;
//        }
//    }
    public static void checkIfArmourReady()
    {
        if(Inventory.stream().name(Constants.CHOSEN_FULL_HELM).first().valid()
                && Inventory.stream().name(Constants.CHOSEN_PLATEBODY).first().valid()
                && Inventory.stream().name(Constants.CHOSEN_PLATELEGS).first().valid())
        {
            warriorMain.state("Has All Armour...");
            HasArmour = true;
        } else
        {
            warriorMain.state("Does not have all Armour");
            HasArmour = false;
        }
    }
    public static void checkIfFoodReady(){
        if(Inventory.stream().name(Constants.food).first().valid())
        {
            warriorMain.state("Has food... Counting");
            if(Inventory.stream().name(Constants.food).count() <= 2)
            {
                warriorMain.state("Not enough food...");
                HasFood = false;
            } else
            {
                warriorMain.state("Has enough food...");
                HasFood = true;
            }
        } else
        {
            warriorMain.state("Not enough food...");
            HasFood = false;
        }
    }
    public static void checkIfPpotReady(){
        if(Inventory.stream().nameContains("Prayer").action("Drink").count() > 0)
        {
            warriorMain.state("Has Prayer pots...");
            HasPPot = true;

        } else
        {
            warriorMain.state("No Prayer pots...");
            HasPPot = false;
        }
    }
    public static void checkDefenderStatus()
    {
        warriorMain.state("Checking current defender...");
        if(Inventory.stream().nameContains("defender").first().valid())
        {
            warriorMain.state("Caching defenders...");
            if(Inventory.stream().name(Constants.BRONZE_DEFENDER).first().valid())
            {
                Constants.bronzeCompleted = true;
                Vars.get().currentDefender = "Bronze";
            }
            if(Inventory.stream().name(Constants.IRON_DEFENDER).first().valid())
            {
                Constants.bronzeCompleted = true;
                Constants.ironCompleted = true;
                Vars.get().currentDefender = "Iron";
            }
            if(Inventory.stream().name(Constants.STEEL_DEFENDER).first().valid())
            {
                Constants.bronzeCompleted = true;
                Constants.ironCompleted = true;
                Constants.steelCompleted = true;
                Vars.get().currentDefender = "Steel";
            }
            if(Inventory.stream().name(Constants.BLACK_DEFENDER).first().valid())
            {
                Constants.bronzeCompleted = true;
                Constants.ironCompleted = true;
                Constants.steelCompleted = true;
                Constants.blackCompleted = true;
                Vars.get().currentDefender = "Black";
            }
            if(Inventory.stream().name(Constants.MITHRIL_DEFENDER).first().valid())
            {
                Constants.bronzeCompleted = true;
                Constants.ironCompleted = true;
                Constants.steelCompleted = true;
                Constants.mithCompleted = true;
                Constants.blackCompleted = true;
                Vars.get().currentDefender = "Mithril";
            }
            if(Inventory.stream().name(Constants.ADAMANT_DEFENDER).first().valid())
            {
                Constants.bronzeCompleted = true;
                Constants.ironCompleted = true;
                Constants.steelCompleted = true;
                Constants.blackCompleted = true;
                Constants.mithCompleted = true;
                Constants.addyCompleted = true;
                Vars.get().currentDefender = "Adamant";
            }
            if(Inventory.stream().name(Constants.RUNE_DEFENDER).first().valid())
            {
                Constants.bronzeCompleted = true;
                Constants.ironCompleted = true;
                Constants.steelCompleted = true;
                Constants.blackCompleted = true;
                Constants.mithCompleted = true;
                Constants.addyCompleted = true;
                Constants.runeCompleted = true;
                Vars.get().currentDefender = "Rune";
            }
            if(Inventory.stream().name(Constants.DRAGON_DEFENDER).first().valid())
            {
                Constants.bronzeCompleted = true;
                Constants.ironCompleted = true;
                Constants.steelCompleted = true;
                Constants.blackCompleted = true;
                Constants.mithCompleted = true;
                Constants.addyCompleted = true;
                Constants.runeCompleted = true;
                Constants.dragonCompleted = true;
                Vars.get().currentDefender = "Dragon";
            }
        } else
        {
            warriorMain.state("We have no defenders...");
            Vars.get().currentDefender = "None";
        }
    }
    public static void checkIfReady() {
        if(Constants.usePrayerPotion)
        {
            checkDefenderStatus();
            checkIfArmourReady();
            checkIfFoodReady();
            checkIfPpotReady();
            if (!HasFood || !HasArmour || !HasPPot)
            {
                needToBank = true;
                readyToFightRuneGiants = false;
                readyToFightAnimations = false;
                readyToFightDragonGiants = false;
            }
            if(HasFood && HasArmour && !hasEnoughTokens && HasPPot)
            {
                needToBank = false;
                readyToFightAnimations = true;
                readyToFightRuneGiants = false;
                readyToFightDragonGiants = false;
            }
            if(HasFood && HasArmour && hasEnoughTokens && HasPPot && !Constants.runeCompleted)
            {
                needToBank = false;
                readyToFightRuneGiants = true;
                readyToFightDragonGiants = false;
                readyToFightAnimations = false;
            }
            if(HasFood && HasArmour && hasEnoughTokens && HasPPot && Constants.runeCompleted)
            {
                needToBank = false;
                readyToFightRuneGiants = false;
                readyToFightDragonGiants = true;
                readyToFightAnimations = false;
            }
        }
        if(!Constants.usePrayerPotion)
        {
            checkDefenderStatus();
            checkIfArmourReady();
            checkIfFoodReady();
            if (!HasFood || !HasArmour)
            {
                needToBank = true;
                readyToFightRuneGiants = false;
                readyToFightAnimations = false;
                readyToFightDragonGiants = false;
            }
            if(HasFood && HasArmour && !hasEnoughTokens && Constants.runeCompleted)
            {
                needToBank = false;
                readyToFightAnimations = true;
                readyToFightRuneGiants = false;
                readyToFightDragonGiants = false;
            }
            if(HasFood && HasArmour && hasEnoughTokens && !Constants.runeCompleted)
            {
                needToBank = false;
                readyToFightRuneGiants = true;
                readyToFightAnimations = false;
                readyToFightDragonGiants = false;
            }
            if(HasFood && HasArmour && hasEnoughTokens && Constants.runeCompleted)
            {
                needToBank = false;
                readyToFightRuneGiants = false;
                readyToFightAnimations = false;
                readyToFightDragonGiants = true;
            }
        }
    }

    public static void checkIfUseFood() {
        if(Objects.equals(Constants.food, ""))
        {
            System.out.println("User has not selected food...");
            ScriptManager.INSTANCE.stop();
        }
    }

    public static void checkIfUsePrayerPotion() {
        if(Constants.usePrayerPotion && Constants.amountOfPrayerPots == 0)
        {
            System.out.println("User has not selected amount of potions...");
            ScriptManager.INSTANCE.stop();
        }
    }
}
