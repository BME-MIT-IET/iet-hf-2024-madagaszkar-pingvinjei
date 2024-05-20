package junit;

import container.Container;
import container.Pipe;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import player.Saboteur;

public class SaboteurActionTest {

    Saboteur saboteur;

    Container container1;

    @BeforeEach
    public void setUp(){
        saboteur = null;
        container1 = null;
    }

    @Test
    @DisplayName("Saboteur makes pipe slippery")
    public void makePipeSlipperyTest(){
        saboteur = new Saboteur();
        container1 = new Pipe();

        saboteur.setPosition(container1);
        saboteur.makePipeSlippery();

        Assert.assertTrue(container1.getIsSlippery());
    }

}
