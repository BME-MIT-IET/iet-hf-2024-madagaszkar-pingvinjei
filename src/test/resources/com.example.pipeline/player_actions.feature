Feature: Player Actions

  Scenario: Player moves to a pump from a pipe
    Given a player
    And a pump
    And a pipe
    When the pump is the pipe's neighbour
    And the pipe is the the pump's neighbour
    And the player's position is a pipe
    Then the player moves to the pump

  Scenario: Player moves to a pipe from a pump
    Given a player
    And a pump
    And a pipe
    When the pump is the pipe's neighbour
    And the pipe is the the pump's neighbour
    And the pipe can be stepped on
    And the player's position is a pump
    Then the player moves to the pipe

  Scenario: Player moves to a slippery pipe from a pump
    Given a player
    And a pump
    And a pipe
    When the pump is the pipe's neighbour
    And the pipe is the the pump's neighbour
    And the pipe can be stepped on
    And the pipe gets slippery
    And the player's position is a pump
    Then the player moves to the pipe
    And the player's position is not the pipe

  Scenario: Player moves to a sticky pipe from a pump
    Given a player
    And a pump
    And a pipe
    When the pump is the pipe's neighbour
    And the pipe is the the pump's neighbour
    And the pipe can be stepped on
    And the pipe gets sticky
    And the player's position is a pump
    Then the player moves to the pipe
    And the player is sticky

  Scenario: Player tries to move to a pump from a pipe while being sticky
    Given a player
    And a pump
    And a pipe
    When the pump is the pipe's neighbour
    And the pipe is the the pump's neighbour
    And the player's position is a pipe
    And the player gets sticky
    Then the player moves to the pump
    And the player's position does not change

  Scenario: Player makes a pipe sticky
    Given a player
    And a pipe
    When the player's position is a pipe
    And the pipe is not sticky
    Then the player makes the pipe sticky
    And the pipe is sticky

  Scenario: Player takes a pump from the cistern
    Given a player
    And a cistern
    And a pump
    When the player's position is the cistern
    And the cistern has a pump
    And the player does not have a pump
    Then the player takes the pump from the cistern
    And the player has a pump
    And the cistern does not have a pump



