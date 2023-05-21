package kbsWarriorGuild;

import com.google.common.eventbus.Subscribe;
import kbsWarriorGuild.tasks.*;
import org.powbot.api.event.InventoryChangeEvent;
import org.powbot.api.event.NpcAnimationChangedEvent;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.SettingsManager;
import org.powbot.mobile.ToggleId;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;

import java.util.concurrent.Callable;
@ScriptManifest(
        name = "kbsWarriorGuild",
        description = "",
        version = "0.0.1",
        priv = true,
        author = "bloki")
@ScriptConfiguration.List(
        {
                @ScriptConfiguration(name = "StartupMessage" , description = "Please start with an empty inventory",
                        optionType = OptionType.INFO),
//                @ScriptConfiguration(name = "MinimizeMessage" , description = "Minimize at the top right to change weapons",
//                        optionType = OptionType.INFO),
                @ScriptConfiguration(name = "Food List", description = "Type of food?",
                        optionType = OptionType.STRING,defaultValue =
                        "",allowedValues = {"","Shark","Trout","Anglerfish","Salmon","addmorelater"}),
                @ScriptConfiguration(name = "Prayer", description = "Use Prayer?", optionType = OptionType.BOOLEAN),
//                @ScriptConfiguration(name = "amountOfPots", description = "How many prayer potions to withdraw?",
//                        optionType = OptionType.INTEGER, visible = false),
//                @ScriptConfiguration(name = "typeOfArmour", description = "What armour do you want to animate??",
//                        OptionType.STRING,defaultValue =
//                        "",allowedValues = {"","Mithril","Iron","Adamant","addmorelater"}),
//                @ScriptConfiguration(name = "amountOfPots", description = "How many prayer potions to withdraw?",
//                        optionType = OptionType.INTEGER, visible = false),
//                @ScriptConfiguration(name = "useSpec", description = "Use Spec?", optionType = OptionType.BOOLEAN),
//                @ScriptConfiguration(name = "whatSpecWep", description = "Click here to select weapons...",
//                        optionType = OptionType.EQUIPMENT, visible = false),
//                @ScriptConfiguration(name = "chosenSpecWep", description = "This is your chosen spec weapon...",
//                        optionType = OptionType.STRING, visible = false),
        })

public class warriorMain extends AbstractScript {
//    @ValueChanged(keyName = "Prayer")
//    public void prayerChanged(Boolean valuePassed) {
//        updateVisibility("amountOfPots", valuePassed);
//    }
//    @ValueChanged(keyName = "useSpec")
//    public void specChanged(Boolean valuePassed) {
//        updateVisibility("whatSpecWep", valuePassed);
//        updateVisibility("chosenSpecWep", valuePassed);
//    }
//    @ValueChanged(keyName = "chosenSpecWep")
//    public void whatSpecWep(HashMap valuePassed) {
//        System.out.println("wepChosen is: " + Equipment.itemAt(Equipment.Slot.MAIN_HAND));
//    }
    private static String currentState = "null";
    private final static String currentIP = "localhost:5555";
    @Override
    public void onStart() {
        super.onStart();
        Constants.food  = getOption("Food List");
        warriorMain.state("Food to eat is: " + Constants.food);
        //Constants.amountOfPrayerPots = getOption("amountOfPots");
        //Constants.typeOfArmour = getOption("typeOfArmour");
        Constants.usePrayerPotion = getOption("Prayer");
        Constants.amountOfPrayerPots = 3;
        warriorMain.state("usePrayer: " + Constants.usePrayerPotion);
//        Constants.useSpec = getOption("useSpec");
//        warriorMain.state("useSpec: " + Constants.useSpec);
//        LinkedHashMap eq = getOption("whatSpecWep");
//        warriorMain.state("LinkedHashMap = " + eq);
//        warriorMain.state("whatSpecWep: " + eq.get(3));
        if(Constants.usePrayerPotion)
        {
            Vars.get().taskList.add(new drinkPrayPot(this));
            Vars.get().taskList.add(new usePrayer(this));
        }
        warriorMain.state("amount of prayer potions: " + Constants.amountOfPrayerPots);
        drawPaint();
        startRequirements();
        addTasks();
    }

    @Subscribe
    public void NpcAnimationChange(NpcAnimationChangedEvent e) {
        if(e.getNpc().equals(Npcs.stream().interactingWithMe().first()) && e.getNpc().index() != Vars.get().last_attacked_npc)
        {
            Vars.get().last_attacked_npc = e.getNpc().index();
            System.out.println("Storing last_attacked");
        }
        if(e.getNpc().index() == Vars.get().last_attacked_npc && e.getAnimation() == 4653)
        {
            Vars.get().cyclops_killed++;
        }
        if(e.getNpc().index() == Vars.get().last_attacked_npc  && e.getAnimation() == 4167)
        {
            Vars.get().armours_killed++;
            Util.needToLoot = true;
        }
//        Npc actor = e.getNpc();
//        if(actor.valid()) {
//            System.out.println("animation: " + e.getAnimation());
//        }
    }
    @Subscribe
    public void onInventChange(InventoryChangeEvent evt){
        if(Vars.get().total_tokens != Inventory.stream().name(Constants.WARRIOR_GUILD_TOKEN).count(true))
        {
            Vars.get().total_tokens = Inventory.stream().name(Constants.WARRIOR_GUILD_TOKEN).count(true);
        }
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
    public void addTasks()
    {
        Vars.get().taskList.add(new eat(this));
        Vars.get().taskList.add(new fightAnimation(this));
        Vars.get().taskList.add(new lootTokens(this));
        Vars.get().taskList.add(new useBank(this));
        Vars.get().taskList.add(new walkToBank(this));
        Vars.get().taskList.add(new walkToAnimatorArea(this));
        Vars.get().taskList.add(new withdrawSupplies(this));
        Vars.get().taskList.add(new useAnimator(this));
        Vars.get().taskList.add(new fightRuneGiants(this));
        Vars.get().taskList.add(new lootDefenders(this));
        Vars.get().taskList.add(new walkToRuneGiantArea(this));
        Vars.get().taskList.add(new resetDefenderRoom(this));
        Vars.get().taskList.add(new walkToDragonGiantArea(this));
        Vars.get().taskList.add(new fightDragonGiants(this));
        Vars.get().taskList.add(new checkAutoRetaliate(this));
    }
    
    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("kbsWarriorGuild",
                "",
                currentIP,
                true,
                false);
        PaintBuilder.newBuilder()
                .withoutDiscordWebhook()
                .y(50)
                .x(55)
                .build();
    }
//    @ValueChanged(keyName = "chosenSpecWep")
//    public void whatSpecWep(LinkedHashMap valuePassed) {
//        Item weapon = Equipment.itemAt(Equipment.Slot.MAIN_HAND);
//        System.out.println("weapon is: " + weapon);
//        System.out.println("valuepassed is: " + valuePassed);
//    }
    @Override
    public void poll() {
        for ( Task t: Vars.get().taskList) {
            if (t.activate()) {
                if(ScriptManager.INSTANCE.isStopping()) {
                    break;
                } else{
                    System.out.println("Performing Task: " + t.name);
                    t.execute();
                }
            }
            if(ScriptManager.INSTANCE.isStopping()) {
                break;
            }
        }
    }
    public void startRequirements()
    {
        Util.checkPlayer();
        //Util.checkLocation();
        Util.checkIfUseFood();
        Util.checkIfUsePrayerPotion();
        Util.checkEnoughTokens();
        Util.checkIfReady();
        SettingsManager.set(ToggleId.UnexpectedIdle,false);
    }
    public static void state(String s){
        currentState = s;
        System.out.println(s);
    }
    public void drawPaint()
    {
        Paint paint = PaintBuilder.newBuilder()
                .x(10)
                .y(10)
//                .trackSkill(Skill.Attack)
//                .trackSkill(Skill.Strength)
//                .trackSkill(Skill.Defence)
//                .trackSkill(Skill.Hitpoints)
                .addString("Status: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return currentState;
                    }
                }).addString("Cyclops killed: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return Integer.toString(Vars.get().cyclops_killed);
                    }
                }).addString("Armours killed: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return Integer.toString(Vars.get().armours_killed);
                    }
                }).addString("Tokens gained: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return Integer.toString(Vars.get().tokens_gained);
                    }
                }).addString("Total tokens: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return Long.toString(Vars.get().total_tokens);
                    }
                }).addString("readyToFightAnimation: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Util.readyToFightAnimations);
                    }
                }).addString("readyToFightGiant: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Util.readyToFightRuneGiants);
                    }
                }).addString("needToLoot: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Util.needToLoot);
                    }
                }).addString("needToBank: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Util.needToBank);
                    }
                }).addString("needToResetRoom: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Util.needToResetRoom);
                    }
                }).addString("Current defender: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return Vars.get().currentDefender;
                    }
                }).addString("useFood: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Constants.useFood);
                    }
                }).addString("usePrayer: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Constants.usePrayerPotion);
                    }
                }).addString("hasBronze: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Constants.bronzeCompleted);
                    }
                }).addString("hasIron: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Constants.ironCompleted);
                    }
                }).addString("hasSteel: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Constants.steelCompleted);
                    }
                }).addString("hasBlack: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Constants.blackCompleted);
                    }
                }).addString("hasMith: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Constants.mithCompleted);
                    }
                }).addString("hasAddy: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Constants.addyCompleted);
                    }
                }).addString("hasRune: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Constants.runeCompleted);
                    }
                }).addString("hasDragon: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Constants.dragonCompleted);
                    }
                })
                .build();
        addPaint(paint);
    }
}
