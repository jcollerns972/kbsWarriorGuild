package testScript.tasks;

import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import testScript.Task;
import testScript.testMain;

public class withdrawPotionTask extends Task {
    testMain main;
    public withdrawPotionTask(testMain main) {
        super();
        super.name = "Withdrawing Potions";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return Inventory.stream().action("Drink").count() <= 0;
    }

    @Override
    public void execute() {
        int potID = 9739;
        int amountToWithdraw = 10;
        System.out.println("there is nothing to drink in inventory, opening bank...");
        if(Bank.inViewport())
        {
            System.out.println("Opening bank...");
            Condition.wait(Bank::open,50,10);
        }
        System.out.println("Withdrawing pots...");
        if(Bank.withdraw(potID,Bank.Amount.TEN))
        {
            if(Condition.wait(() -> Inventory.stream().id(potID).first().valid(), 100,50))
            {
                System.out.println("Closing bank...");
                if(Bank.close())
                {
                    if(Condition.wait(() -> !Bank.opened(),50,10))
                    {
                        System.out.println("Bank Closed...");
                    }
                }
            }
        }

    }
}
