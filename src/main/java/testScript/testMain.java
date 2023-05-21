package testScript;

import com.google.common.eventbus.Subscribe;
import org.powbot.api.Color;
import org.powbot.api.event.NpcAnimationChangedEvent;
import org.powbot.api.event.RenderEvent;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Objects;
import org.powbot.mobile.drawing.Rendering;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.AbstractScript;
import org.powbot.api.script.ScriptManifest;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;

import java.util.concurrent.Callable;

@ScriptManifest(
        name = "testBot",
        description = "",
        version = "1.2.4",
        priv = true,
        author = "bloki")

public class testMain extends AbstractScript{
    private final static String currentIP = "192.168.0.34:5555";
    private static String currentState = "null";
    public static void main(String[] args){
        new ScriptUploader().uploadAndStart("testBot",
                "",
                currentIP,
                true,
                true);
        PaintBuilder.newBuilder().build();
        PaintBuilder.newBuilder()
                .withoutDiscordWebhook()
                .y(50)
                .x(55)
                .build();
    }
    @Subscribe
    public void NpcAnimationChange(NpcAnimationChangedEvent e) {

//        if(e.getNpc().equals(Npcs.stream().interactingWithMe().first()) && e.getAnimation() == 4653)
//        {
//            Vars.get().cyclops_killed++;
//        }
//        if(e.getNpc().equals(Npcs.stream().interactingWithMe().first()) && Vars.get().last_attacked_npc != e.getNpc())
//        {
//            System.out.println("Last_attacked was: " + Vars.get().last_attacked_npc);
//            Vars.get().last_attacked_npc = e.getNpc();
//            System.out.println("lastattackednpcstored...: " + e.getNpc());
//        }
//        if(e.getNpc().equals(Npcs.stream().interactingWithMe().first()))
//        {
//            Vars.get().last_attacked_npc = e.getNpc();
//            System.out.println("animation is: " + e.getAnimation());
//        }
//        if(Vars.get().last_attacked_npc.animation() == 4167)
//        {
//            System.out.println("this is true");
//        }
//        if(e.getNpc().equals(Npcs.stream().interactingWithMe().first()) && e.getAnimation() == 4167)
//        {
//            System.out.println("this is true");
//        }
        System.out.println("index: " + e.getNpc().index());
//        Npc actor = e.getNpc();
//        if(actor.valid()) {
//            System.out.println("animation: " + e.getAnimation());
//        }
    }
    @Override
    public void poll() {
        for (Task t : Vars.get().taskList) {
            System.out.println("Checking: " + t);
            if (t.activate()) {
                if(ScriptManager.INSTANCE.isStopping()) {
                    break;
                } else{
                    //System.out.println("Performing Task: " + t.name);
                    t.execute();
                }
            }
            if(ScriptManager.INSTANCE.isStopping()) {
                break;
            }
        }
    }
    @Subscribe
    public void onRender(RenderEvent evt){
        //Graphics g = evt.getGraphics();
        Rendering.setScale(1.0f);
        Rendering.setColor(Color.getGREEN());
        GameObject obj = Objects.stream().id(10355).first();
        if(obj!= GameObject.getNil() && obj.inViewport()){
            obj.draw();
        }
    }
    @Override
    public void onStart()
    {
        addTasks();
        drawPaint();
    }
    public void drawPaint()
    {
        Paint paint = PaintBuilder.newBuilder()
                .x(40)
                .y(180)
                .trackSkill(Skill.Ranged)
                .trackSkill(Skill.Attack)
                .trackSkill(Skill.Strength)
                .trackSkill(Skill.Defence)
                .addString("Last Status: ", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return currentState;
                    }
                })
                .build();
        addPaint(paint);
    }

    public void addTasks()
    {
        //Vars.get().taskList.add(new movePathTest(this));
        //Vars.get().taskList.add(new playerDDTask(this));
        //Vars.get().taskList.add(new withdrawPotionTask(this));
        //Vars.get().taskList.add(new drinkPotionTask(this));
    }
    public static void state(String s){
        currentState = s;
        System.out.println(s);
    }


}
