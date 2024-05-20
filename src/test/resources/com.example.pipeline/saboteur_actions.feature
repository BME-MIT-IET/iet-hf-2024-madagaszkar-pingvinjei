Feature: Saboteur Actions

  Scenario: Saboteur makes a pipe slippery
    Given a saboteur player
    And a pipe
    When the player's position is a pipe
    And the pipe is not slippery
    Then the player makes the pipe slippery
    And the pipe is slippery