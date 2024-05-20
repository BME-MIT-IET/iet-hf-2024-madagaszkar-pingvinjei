package junit;

import container.Container;
import controller.Controller;
import io.cucumber.java.it.Ma;
import map.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class ControllerTest {

    Container container1;
    Container container2;

    @Test
    @DisplayName("Water flows from Mountain Spring")
    public void waterFlowsFromMsTest(){
        Map.makeTestMap();

        container1 = Map.getContainerAt(0,0).getContainer();
        int baseWater = container1.mountainSpringQuery();

        Controller.getInstance().waterFlow();
        Controller.getInstance().evaluateCycles();

        Assert.assertNotEquals(baseWater, container1.mountainSpringQuery());
    }

    @Test
    @DisplayName("Water flows into Cistern")
    public void waterFlowsToCisternTest(){
        Map.makeTestMap();

        container1 = Map.getContainerAt(4,0).getContainer();
        int baseWater = container1.queryCistern();

        //10 kör szimulálása
        for(int i = 0; i < 10; i++){
            Controller.getInstance().waterFlow();
            Controller.getInstance().evaluateCycles();
        }

        Assert.assertNotEquals(baseWater, container1.queryCistern());
    }

    @Test
    @DisplayName("Water leaks from damaged pipe")
    public void waterLeaksFromDamagedPipeTest(){
        Map.makeTestMap();

        container1 = Map.getContainerAt(1,0).getContainer();
        int baseWater = Map.getLeakedWater();
        container1.puncturePipe();

        //10 kör szimulálása
        for(int i = 0; i < 10; i++){
            Controller.getInstance().waterFlow();
            Controller.getInstance().evaluateCycles();
        }

        Assert.assertNotEquals(baseWater, Map.getLeakedWater());
    }

    @Test
    @DisplayName("Water won't flow through damaged pump")
    public void waterWontFlowThroughDamagedPumpTest(){
        Map.makeTestMap();
        container1 = Map.getContainerAt(2,0).getContainer();
        container2 = Map.getContainerAt(4,0).getContainer();
        int baseWater = container2.queryCistern();

        container1.damageContainer();

        //10 kör szimulálása
        for(int i = 0; i < 10; i++){
            Controller.getInstance().waterFlow();
            Controller.getInstance().evaluateCycles();
        }

        Assert.assertEquals(baseWater, container2.queryCistern());
    }

}
