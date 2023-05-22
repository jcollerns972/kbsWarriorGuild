package kbsWarriorGuild;

import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;
import org.powbot.mobile.script.ScriptManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Util {
    public static void checkEnoughTokens() {
        warriorMain.state("Checking if we have enough tokens to fight Giants");
        long tokenCount = Inventory.stream().name(Constants.WARRIOR_GUILD_TOKEN).count(true);
        Vars.get().hasEnoughTokens = tokenCount > Vars.get().tokens_to_fight_giant;
        warriorMain.state(Vars.get().hasEnoughTokens ? "We have enough tokens..." : "We do not have enough tokens...");
    }

    public static void checkPlayer() {
        if (Players.local().valid()) {
            warriorMain.state("Player valid!");
        } else {
            warriorMain.state("Player not valid, waiting for player to become valid...");
            if (Condition.wait(() -> Players.local().valid(), 1000, 50)) {
                warriorMain.state("Player is now valid...");
            }
        }
    }

    public static void checkLocation() {
        // location logic when figured out
    }

    public static boolean checkIfArmourReady() {
        return Inventory.stream().name(Constants.CHOSEN_FULL_HELM).isNotEmpty()
                && Inventory.stream().name(Constants.CHOSEN_PLATEBODY).isNotEmpty()
                && Inventory.stream().name(Constants.CHOSEN_PLATELEGS).isNotEmpty();
    }

    public static boolean checkIfFoodReady() {
        if (!Objects.equals(Constants.food, "")) {
            warriorMain.state("Has food... Counting");
            long foodCount = Inventory.stream().name(Constants.food).count();
            if (foodCount <= 2) {
                warriorMain.state("Not enough food...");
                return false;
            } else {
                warriorMain.state("Has enough food...");
                return true;
            }
        } else {
            warriorMain.state("Not enough food...");
            return false;
        }
    }

    public static boolean checkIfPpotReady() {
        System.out.println("Is there enough prayer potions? " + Inventory.stream().nameContains("Prayer").action("Drink").isNotEmpty());
        return Inventory.stream().nameContains("Prayer").action("Drink").isNotEmpty();
    }

    public static void checkDefenderStatus() {
        warriorMain.state("Checking current defender...");
        if (Inventory.stream().nameContains("defender").isNotEmpty()) {
            warriorMain.state("Caching defenders...");
            Constants.bronzeCompleted = Inventory.stream().name(Constants.BRONZE_DEFENDER).isNotEmpty();
            Constants.ironCompleted = Inventory.stream().name(Constants.IRON_DEFENDER).isNotEmpty();
            Constants.steelCompleted = Inventory.stream().name(Constants.STEEL_DEFENDER).isNotEmpty();
            Constants.blackCompleted = Inventory.stream().name(Constants.BLACK_DEFENDER).isNotEmpty();
            Constants.mithCompleted = Inventory.stream().name(Constants.MITHRIL_DEFENDER).isNotEmpty();
            Constants.addyCompleted = Inventory.stream().name(Constants.ADAMANT_DEFENDER).isNotEmpty();
            Constants.runeCompleted = Inventory.stream().name(Constants.RUNE_DEFENDER).isNotEmpty();
            Constants.dragonCompleted = Inventory.stream().name(Constants.DRAGON_DEFENDER).isNotEmpty();

            if (Constants.dragonCompleted) {
                Vars.get().currentDefender = "Dragon";
            } else if (Constants.runeCompleted) {
                Vars.get().currentDefender = "Rune";
            } else if (Constants.addyCompleted) {
                Vars.get().currentDefender = "Adamant";
            } else if (Constants.mithCompleted) {
                Vars.get().currentDefender = "Mithril";
            } else if (Constants.blackCompleted) {
                Vars.get().currentDefender = "Black";
            } else if (Constants.steelCompleted) {
                Vars.get().currentDefender = "Steel";
            } else if (Constants.ironCompleted) {
                Vars.get().currentDefender = "Iron";
            } else if (Constants.bronzeCompleted) {
                Vars.get().currentDefender = "Bronze";
            }
        } else {
            warriorMain.state("We have no defenders...");
            Vars.get().currentDefender = "None";
        }
    }
    public static void checkIfUseFood() {
        if (Objects.equals(Constants.food, "")) {
            System.out.println("User has not selected food...");
            ScriptManager.INSTANCE.stop();
        }
    }

    public static void checkIfUsePrayerPotion() {
        if (Constants.usePrayerPotion && Vars.get().amountOfPrayerPots == 0) {
            System.out.println("User has not selected the amount of potions...");
            ScriptManager.INSTANCE.stop();
        }
    }
    public static void handleChosenArmour() {
        Map<String, String[]> armourMappings = new HashMap<>();
        armourMappings.put("Bronze", new String[]{"Bronze full helm", "Bronze platebody", "Bronze platelegs"});
        armourMappings.put("Iron", new String[]{"Iron full helm", "Iron platebody", "Iron platelegs"});
        armourMappings.put("Steel", new String[]{"Steel full helm", "Steel platebody", "Steel platelegs"});
        armourMappings.put("Mithril", new String[]{"Mithril full helm", "Mithril platebody", "Mithril platelegs"});
        armourMappings.put("Adamant", new String[]{"Adamant full helm", "Adamant platebody", "Adamant platelegs"});
        armourMappings.put("Rune", new String[]{"Rune full helm", "Rune platebody", "Rune platelegs"});

        String[] chosenArmor = armourMappings.get(Constants.typeOfArmour);
        if (chosenArmor != null) {
            Constants.CHOSEN_FULL_HELM = chosenArmor[0];
            Constants.CHOSEN_PLATEBODY = chosenArmor[1];
            Constants.CHOSEN_PLATELEGS = chosenArmor[2];
        } else
        {
            System.out.println("USER HAS NOT CHOSEN AN ARMOUR...");
            ScriptManager.INSTANCE.stop();
        }
    }
    public static void checkIfReady() {
        if (Constants.usePrayerPotion) {
            checkDefenderStatus();
            boolean isArmourReady = checkIfArmourReady();
            boolean isFoodReady = checkIfFoodReady();
            boolean isPotionReady = checkIfPpotReady();

            Vars.get().needToBank = !isFoodReady || !isArmourReady || !isPotionReady;
            Vars.get().readyToFightAnimations = isFoodReady && isArmourReady && isPotionReady && !Vars.get().hasEnoughTokens;
            Vars.get().readyToFightRuneGiants = isFoodReady && isArmourReady && Vars.get().hasEnoughTokens && isPotionReady && !Constants.runeCompleted;
            Vars.get().readyToFightDragonGiants = isFoodReady && isArmourReady && Vars.get().hasEnoughTokens && isPotionReady && Constants.runeCompleted;
        } else {
            checkDefenderStatus();
            boolean isArmourReady = checkIfArmourReady();
            boolean isFoodReady = checkIfFoodReady();

            Vars.get().needToBank = !isFoodReady || !isArmourReady;
            Vars.get().readyToFightAnimations = isFoodReady && isArmourReady && !Vars.get().hasEnoughTokens;
            Vars.get().readyToFightRuneGiants = isFoodReady && isArmourReady && Vars.get().hasEnoughTokens && !Constants.runeCompleted;
            Vars.get().readyToFightDragonGiants = isFoodReady && isArmourReady && Vars.get().hasEnoughTokens && Constants.runeCompleted;
        }
    }
}
