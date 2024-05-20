package junit;

import container.Container;
import container.Pipe;
import container.Pump;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import player.Mechanic;

public class MechanicActionTest {

    Mechanic mechanic;
    Container container1;
    Container container2;

    @BeforeEach
    public void setUp(){
        mechanic = null;
        container1 = null;
        container2 = null;
    }

    @Test
    @DisplayName("Mechanic repairs pipe")
    public void mechanicRepairsPipeTest(){
        mechanic = new Mechanic();
        container1 = new Pipe();

        mechanic.setPosition(container1);
        container1.puncturePipe();
        mechanic.RepairPipe();

        Assert.assertFalse(container1.isDamaged());
    }

    @Test
    @DisplayName("Mechanic repairs pump")
    public void mechanicRepairsPumpTest(){
        mechanic = new Mechanic();
        container1 = new Pump(1);

        mechanic.setPosition(container1);
        container1.damageContainer();
        mechanic.RepairPump();

        Assert.assertFalse(container1.isDamaged());
    }

}
