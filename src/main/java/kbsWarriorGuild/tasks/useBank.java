package kbsWarriorGuild.tasks;

import kbsWarriorGuild.*;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Players;

public class useBank extends Task {
    warriorMain main;
    public useBank(warriorMain main) {
        super();
        super.name = "Opening bank";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Areas.WARRIOR_BANK_AREA.contains(Players.local())
                && Vars.get().needToBank
                && !Bank.opened()
                && !Vars.get().needToLoot
                && !Vars.get().fightingAnimation;
    }

    @Override
    public void execute() {
        warriorMain.state("Opening bank...");
        Bank.open();
        if(Condition.wait(Bank::opened,100,50))
        {
            warriorMain.state("Bank open...");
        }
    }
}
