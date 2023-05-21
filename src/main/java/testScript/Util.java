package testScript;

import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.Camera;
import org.powbot.api.rt4.Component;
import org.powbot.api.rt4.Game;
import org.powbot.api.rt4.Widgets;

import java.util.Collections;

public class Util {
    static Component allBox = Widgets.widget(162).component(5);
    static Component settingsBar = Widgets.widget(601).component(69);
    public static void populateRangeMageList()
    {
        for (int i = 0; i < Vars.RANGE_WEAPONS.length; i++)
        {
            Vars.get().RANGE_WEAPONS1.add(Vars.RANGE_WEAPONS[i]);
        }
        System.out.println("Added Ranged IDs...");
        for (int i = 0; i < Vars.MAGIC_WEAPONS.length; i++)
        {
            Vars.get().MAGE_WEAPONS1.add(Vars.MAGIC_WEAPONS[i]);
        }
        System.out.println("Added Magic IDs...");
        for (int i = 0; i < Vars.LONGRANGEDWEAPONS.length; i++)
        {
            Vars.get().LONGRANGED_WEAPONS.add(Vars.LONGRANGEDWEAPONS[i]);
        }
        System.out.println("Added LongRanged IDs...");
    }
    public static void reportListFill()
    {
        Vars.get().reportList.add("cs lolz");
        Vars.get().reportList.add("cs xd");
        Vars.get().reportList.add("click u");
        Vars.get().reportList.add("lennyyyy");
        Vars.get().reportList.add("gchecka haha");
        Vars.get().reportList.add("g checkaa");
        Vars.get().reportList.add("g checkaaa");
        Vars.get().reportList.add("blaks dad");
        Vars.get().reportList.add("wuu2 lmfao");
    }
    public static void populateAggroList()
    {
        Vars.get().aggroList.add("do not bark");
        Vars.get().aggroList.add("icegalaxy");
        Vars.get().aggroList.add("pastajepan");
        Vars.get().aggroList.add("rufflan");
        //Vars.get().aggroList.add("dam im baked");
        //Vars.get().aggroList.add("swimmer");
    }
    public static void addSpyList()
    {
        Vars.get().spyList.add("do not bark");
        Vars.get().spyList.add("pastajepan");
        Vars.get().spyList.add("rufflan");
        Vars.get().spyList.add("icegalaxy");
        Vars.get().spyList.add("swimmer");
        Vars.get().spyList.add("dam im baked");
    }
    public static void addHandlers()
    {
        Collections.addAll(Vars.get().handlers,"just return");
        Collections.addAll(Vars.get().handlers,"blak45");
        Collections.addAll(Vars.get().handlers,"k rs");
        Collections.addAll(Vars.get().handlers,"pleasebuyckb");
        Collections.addAll(Vars.get().handlers,"not bored");
    }
    public static void addSpecWeps()
    {
        Vars.specDict.put(13271,25);
        Vars.specDict.put(21902,60);
        Vars.specDict.put(26219,25);
    }
    public static void cameraCheck()
    {
        if (Camera.getZoom() > 10 ) {
            testMain.state("Moving zoom");
            Camera.moveZoomSlider(0);
            Condition.wait(() -> Camera.getZoom() < 20,100,50);
        }
    }
    public static void toolClose()
    {
            Game.closeOpenTab();
    }

    public static void chatBoxClose()
    {
        if(allBox.textureId() != 3051) {
            allBox.interact("Switch tab");
        }
    }
    public static void setTimers()
    {
        myConstants.time1 = System.currentTimeMillis();
        myConstants.time2 = System.currentTimeMillis();
        myConstants.time3 = System.currentTimeMillis();
    }
    public static void setAggroTimer()
    {
        myConstants.time4 = System.currentTimeMillis();
    }
    public static void setAggroTimer2()
    {
        myConstants.time5 = System.currentTimeMillis();
    }
    public static String removeNullSpace(String text)
    {
        if (text == null) {
            return null;
        }
        String fixed_text = "";
        for (int i = 0; i < text.length(); i++) {
            final char character = text.charAt(i);
            final int numValue = Character.getNumericValue(character);
            fixed_text += numValue == -1 ? " " : character;
        }
        return fixed_text;
    }

    public static int getRandomSmallAfk()
    {
        return (int)Math.floor(Math.random()*(myConstants.maxSmallAfkTime - myConstants.minSmallAfkTime + 1) + myConstants.minSmallAfkTime);
    }
    public static int getRandomBigAfk()
    {
        return (int)Math.floor(Math.random()*(myConstants.maxLongAfkTime - myConstants.minLongAfkTime + 1) + myConstants.minLongAfkTime);
    }
    public static int getRandomNumber()
    {
        return Random.nextInt(3,6);
    }
}
