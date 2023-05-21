package kbsWarriorGuild.tasks;

import kbsWarriorGuild.Task;
import kbsWarriorGuild.warriorMain;

public class closeBank extends Task {
    warriorMain main;
    public closeBank(warriorMain main) {
        super();
        super.name = "";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return false;
    }

    @Override
    public void execute() {

    }
}
