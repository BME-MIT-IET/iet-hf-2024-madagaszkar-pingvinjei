package junit;

import container.Container;
import container.Pipe;
import container.Pump;
import map.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import player.Direction;
import player.Player;
import player.Type;

import java.util.Arrays;

public class PlayerActionTest {

    Player player;
    Container container1;
    Container container2;
    Container container3;

    @BeforeEach
    public void setUp(){
        player = null;
        container1 = null;
        container2 = null;
        container3 = null;
    }

    @Test
    @DisplayName("Player moves from pipe to pump")
    public void movesFromPipeToPumpTest(){
        player = new Player();
        container1 = new Pipe();
        container2 = new Pump(1);

        container1.getNeighbors().add(container2);
        container2.getNeighbors().add(container1);

        player.setPosition(container1);
        player.Move(container2);

        Assert.assertEquals(player.getPosition(), container2);
    }

    @Test
    @DisplayName("Player moves from pump to pipe")
    public void moveFromPumpToPipeTest(){
        player = new Player();
        container1 = new Pipe();
        container2 = new Pump(1);

        container1.getNeighbors().add(container2);
        container2.getNeighbors().add(container1);

        player.setPosition(container2);
        player.Move(container1);

        Assert.assertEquals(player.getPosition(), container1);
    }

    @Test
    @DisplayName("Player leaks pipe")
    public void playerLeaksPipeTest(){
        player = new Player();
        container1 = new Pipe();

        player.setPosition(container1);
        player.leakPipe();

        Assert.assertTrue(container1.isDamaged());
    }

    @Test
    @DisplayName("Player moves to sticky pipe")
    public void playerMovesToStickyPipeTest(){
        player = new Player();
        container1 = new Pipe();
        container2 = new Pump(1);

        container1.getNeighbors().add(container2);
        container2.getNeighbors().add(container1);

        player.setPosition(container2);
        container1.pipeGetsSticky();

        player.Move(container1);
        Assert.assertTrue(player.isSticky());
    }

    @Test
    @DisplayName("Player tries to move from sticky pipe")
    public void playerMovesFromStickyPipeTest(){
        player = new Player();
        container1 = new Pipe();
        container2 = new Pump(1);

        container1.getNeighbors().add(container2);
        container2.getNeighbors().add(container1);

        player.setPosition(container2);
        container1.pipeGetsSticky();

        player.Move(container1);
        player.Move(container2);

        Assert.assertNotEquals(player.getPosition(), container2);
    }

    @Test
    @DisplayName("Player tries to move to occupied pipe")
    public void playerMovesToOccupiedPipeTest(){
        player = new Player();
        container1 = new Pipe();
        container2 = new Pump(1);

        container1.getNeighbors().add(container2);
        container2.getNeighbors().add(container1);

        player.setPosition(container2);
        container1.getsOccupied();

        player.Move(container1);
        Assert.assertNotEquals(player.getPosition(), container1);
    }

    @Test
    @DisplayName("Player makes pipe sticky")
    public void playerMakesPipeStickyTest(){
        player = new Player();
        container1 = new Pipe();

        player.setPosition(container1);
        player.makePipeSticky();

        Assert.assertTrue(container1.getIsSticky());
    }

    @Test
    @DisplayName("Player moves to slipperey pipe")
    public void playerMovesToSlipperyPipeTest(){
        player = new Player();
        container1 = new Pump(1);
        container2 = new Pipe();
        container3 = new Pump(1);

        container1.getNeighbors().add(container2);
        container2.getNeighbors().add(container1);
        container2.getNeighbors().add(container3);
        container3.getNeighbors().add(container2);

        player.setPosition(container1);
        container2.pipeGetsSlippery();
        player.Move(container2);

        Assert.assertNotEquals(player.getPosition(), container2);
        Assert.assertTrue(Arrays.asList(new Container[]{container1, container3}).contains(player.getPosition()));
    }

    @Test
    @DisplayName("Player adjusts pump input")
    public void playerAdjustsPumpInputTest(){

        Map.makeTestMap();

        player = new Player();
        container1 = Map.getContainerAt(2,0).getContainer(); //Pump
        container2 = Map.getContainerAt(2,1).getContainer(); //Pipe

        player.setPosition(container1);
        player.playerAdjustsPump(Direction.Down, Type.Input);

        Assert.assertEquals(container2, container1.getInput());
    }

    @Test
    @DisplayName("Player adjusts pump output")
    public void playerAdjustsPumpOutputTest(){

        Map.makeTestMap();

        player = new Player();
        container1 = Map.getContainerAt(2,0).getContainer(); //Pump
        container2 = Map.getContainerAt(2,1).getContainer(); //Pipe

        player.setPosition(container1);
        player.playerAdjustsPump(Direction.Down, Type.Output);

        Assert.assertEquals(container2, container1.getOutput());
    }

    @Test
    @DisplayName("Player attaches pump to pipe")
    public void playerAttachesPumpToPipeTest(){
        Map.makeTestMap();

        player = new Player();
        container1 = Map.getContainerAt(2,1).getContainer();
        container2 = new Pump(2);
        player.setCarriedPump((Pump)container2);
        player.setPosition(container1);
        player.attachPump();

        Assert.assertFalse(container1.isLooseEnd());
        Assert.assertTrue(container1.seeifNeighbors(container2));
    }

    @Test
    @DisplayName("Player attaches pipe to pump")
    public void playerAttachesPipeToPumpTest(){
        Map.makeTestMapPipe();

        player = new Player();
        container1 = Map.getContainerAt(2,0).getContainer();
        container2 = new Pipe();

        player.getCarriedPipes().add(container2);
        player.setPosition(container1);
        player.playerAttachesPipe(Direction.Down);
        Assert.assertTrue(container1.seeifNeighbors(container2));
    }
}
