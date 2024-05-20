Feature: Mechanic Actions

Scenario: Mechanic repairs a pump
Given a mechanic player
And a pump
When the player's position is a pump
And the pump is damaged
Then the player repairs the pump
And the pump is not damaged

Scenario: Mechanic repairs a pipe
Given a mechanic player
And a pipe
When the player's position is a pipe
And the pipe is damaged
Then the player repairs the pipe
And the pipe is not damaged