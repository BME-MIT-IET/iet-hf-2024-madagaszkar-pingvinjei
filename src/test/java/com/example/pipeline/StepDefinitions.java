package com.example.pipeline;

import container.Cistern;
import container.Container;
import container.Pipe;
import container.Pump;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import player.Mechanic;
import player.Player;
import player.Saboteur;

public class StepDefinitions {
    private Player player;
    private Container container1; //Used for init pipe/cistern
    private Container container2; //Used for init pump


    /**
     * @Given: Példányok létrehozása
     * @When: Inicializálás / bool check
     * @Then: Action végrehajtása / bool check
     */
    @Given("a player")
    public void a_player() {
        player =new Player();
    }
    @Given("a mechanic player")
    public void a_mechanic_player() {
        player=new Mechanic();
    }
    @Given("a pump")
    public void a_pump() {
        container2 =new Pump(4);
    }
    @Given("a pipe")
    public void a_pipe() {
        container1 =new Pipe();
    }
    @Given("a saboteur player")
    public void a_saboteur_player() {
        player=new Saboteur();
    }

    @Given("a cistern")
    public void a_cistern() {
        container1=new Cistern();
    }

    @When("the pump is the pipe's neighbour")
    public void the_pump_is_the_pipe_s_neighbour() {
        container2.getNeighbors().add(container1);
    }
    @When("the pipe is the the pump's neighbour")
    public void the_pipe_is_the_the_pump_s_neighbour() {
        container1.getNeighbors().add(container2);
    }
    @When("the player's position is a pipe")
    public void the_player_s_position_is_a_pipe() {
        player.setPosition(container1);
    }
    @When("the player's position is a pump")
    public void the_player_s_position_is_a_pump() {
        player.setPosition(container2);
    }
    @When("the pump is damaged")
    public void the_pump_is_damaged() {
        container2.damageContainer();
    }
    @When("the pipe is damaged")
    public void the_pipe_is_damaged() {
        container1.damageContainer();
    }
    @When("the pipe is not sticky")
    public void the_pipe_is_not_sticky() {
        Assert.assertFalse(container1.getIsSticky());
    }
    @When("the pipe is not slippery")
    public void the_pipe_is_not_slippery() {
        Assert.assertFalse(container1.getIsSlippery());
    }
    @When("the pipe gets slippery")
    public void the_pipe_gets_slippery() {
        container1.pipeGetsSlippery();
    }

    @When("the pipe can be stepped on")
    public void the_pipe_can_be_stepped_on() {
        Assert.assertTrue(container1.steppable());
    }
    @When("the pipe gets sticky")
    public void the_pipe_gets_sticky() {
        container1.pipeGetsSticky();
    }
    @When("the player gets sticky")
    public void the_player_gets_sticky() {
        player.getReallySticky();
    }
    @When("the player's position is the cistern")
    public void the_player_s_position_is_the_cistern() {
        player.setPosition(container1);
    }
    @When("the cistern has a pump")
    public void the_cistern_has_a_pump() {
        container1.setFreePump((Pump) container2);
    }
    @When("the player does not have a pump")
    public void the_player_does_not_have_a_pump() {
        Assert.assertNull(player.getCarriedPump());
    }
    @Then("the player takes the pump from the cistern")
    public void the_player_takes_the_pump_from_the_cistern() {
        player.takePump();
    }
    @Then("the player has a pump")
    public void the_player_has_a_pump() {
        Assert.assertNotNull(player.getCarriedPump());
        Assert.assertSame(player.getCarriedPump(), container2);
    }
    @Then("the cistern does not have a pump")
    public void the_cistern_does_not_have_a_pump() {
        Assert.assertFalse(container1.hasPump());
    }

    @Then("the player's position does not change")
    public void the_player_s_position_does_not_change() {
        Assert.assertSame(player.getPosition(), container1);

    }

    @Then("the player's position is not the pipe")
    public void the_player_s_position_is_not_the_pipe() {
        Assert.assertNotSame(player.getPosition(), container1);

    }

    @Then("the player makes the pipe slippery")
    public void the_player_makes_the_pipe_slippery() {
        player.makePipeSlippery();
    }
    @Then("the pipe is slippery")
    public void the_pipe_is_slippery() {
        Assert.assertTrue(container1.getIsSlippery());
    }


    @Then("the player is sticky")
    public void the_player_is_sticky() {
        Assert.assertTrue(player.isSticky());
    }
    @Then("the player makes the pipe sticky")
    public void the_player_makes_the_pipe_sticky() {
        player.makePipeSticky();
    }
    @Then("the pipe is sticky")
    public void the_pipe_is_sticky() {
        Assert.assertTrue(container1.getIsSticky());
    }

    @Then("the player moves to the pipe")
    public void the_player_moves_to_the_pipe() {
        player.Move(container1);
    }
    @Then("the player repairs the pump")
    public void the_player_repairs_the_pump() {
        player.RepairPump();
    }


    @Then("the player repairs the pipe")
    public void the_player_repairs_the_pipe() {
        player.RepairPipe();
    }

    @Then("the pump is not damaged")
    public void the_the_pump_should_be_repaired() {
        Assert.assertFalse(container2.isDamaged());
    }

    @Then("the pipe is not damaged")
    public void the_the_pipe_should_be_repaired() {
        Assert.assertFalse(container1.isDamaged());
    }

    @Then("the player moves to the pump")
    public void the_player_moves_to_the_pump() {
        player.Move(container2);
    }

}
