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
                @ScriptConfiguration(name = "StartupMessage" , description = "Please start with 21 free inventory slots...",
                        optionType = OptionType.INFO),
                @ScriptConfiguration(name = "Type Of Armour", description = "What armour do you want to animate?",
                        optionType = OptionType.STRING,defaultValue =
                        "",allowedValues = {"Bronze","Iron","Steel","Black","Mithril","Adamant","Rune"}),
                @ScriptConfiguration(name = "Food List", description = "Type of food?",
                        optionType = OptionType.STRING,defaultValue =
                        "",allowedValues = {"Trout","Salmon","Tuna","Lobster","Bass","Swordfish","Shark","Anglerfish","Manta ray"}),
                @ScriptConfiguration(name = "Prayer", description = "Use Prayer?", optionType = OptionType.BOOLEAN),
        })

public class warriorMain extends AbstractScript {
    private static String currentState = "null";
    private ScriptManager scriptManager;
    private final static String currentIP = "localhost:5555";
    @Override
    public void onStart() {
        super.onStart();
        scriptManager = ScriptManager.INSTANCE;
        Constants.food  = getOption("Food List");
        warriorMain.state("Food to eat is: " + Constants.food);
        Constants.usePrayerPotion = getOption("Prayer");
        warriorMain.state("usePrayer: " + Constants.usePrayerPotion);
        Constants.typeOfArmour = getOption("Type Of Armour");
        if(Constants.usePrayerPotion)
        {
            Vars.get().amountOfPrayerPots = 3;
            Vars.get().taskList.add(new drinkPrayPot(this));
            Vars.get().taskList.add(new usePrayer(this));
        }
        warriorMain.state("amount of prayer potions: " + Vars.get().amountOfPrayerPots);
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
            Vars.get().needToLoot = true;
        }
//        Npc actor = e.getNpc();
//        if(actor.valid()) {
//            System.out.println("animation: " + e.getAnimation());
//        }
    }
    @Subscribe
    public void onInventChange(InventoryChangeEvent evt){
        long currentTokens = Inventory.stream().name(Constants.WARRIOR_GUILD_TOKEN).count(true);
        if (Vars.get().total_tokens != currentTokens) {
            Vars.get().total_tokens = currentTokens;
        }
        if (currentTokens < 120) {
            Vars.get().hasEnoughTokens = false;
        } else if (currentTokens > Constants.tokens_to_fight_giant) {
            Vars.get().hasEnoughTokens = true;
        }
    }
    public void addTasks()
    {
        Vars.get().taskList.add(new eat(this));
        Vars.get().taskList.add(new lootTokens(this));
        Vars.get().taskList.add(new useBank(this));
        Vars.get().taskList.add(new walkToBank(this));
        Vars.get().taskList.add(new walkToAnimatorArea(this));
        Vars.get().taskList.add(new withdrawSupplies(this));
        Vars.get().taskList.add(new useAnimator(this));
        Vars.get().taskList.add(new lootDefenders(this));
        Vars.get().taskList.add(new walkToRuneGiantArea(this));
        Vars.get().taskList.add(new resetDefenderRoom(this));
        Vars.get().taskList.add(new walkToDragonGiantArea(this));
        Vars.get().taskList.add(new attackCyclops(this));
        Vars.get().taskList.add(new checkAutoRetaliate(this));
        Vars.get().taskList.add(new fightAnimation(this));
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
    @Override
    public void poll() {
        boolean isStopping = scriptManager.isStopping();
        for (Task task : Vars.get().taskList) {
            if (isStopping) {
                break;
            }
            if (task.activate()) {
                System.out.println("Performing Task: " + task.name);
                task.execute();
            }
        }
    }
    public void startRequirements()
    {
        Util.checkPlayer();
        Util.handleChosenArmour();
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
                        return String.valueOf(Vars.get().readyToFightAnimations);
                    }
                }).addString("readyToFightRuneGiant: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Vars.get().readyToFightRuneGiants);
                    }
                }).addString("readyToFightDragonGiant: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Vars.get().readyToFightDragonGiants);
                    }
                }).addString("needToLoot: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Vars.get().needToLoot);
                    }
                }).addString("needToBank: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Vars.get().needToBank);
                    }
                }).addString("needToResetRoom: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Vars.get().needToResetRoom);
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
                }).addString("typeOfArmour: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(Constants.typeOfArmour);
                    }
                })
                .build();
        addPaint(paint);
    }
}
