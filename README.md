# Tic-Tac-Toe-Game
This is a tic tac toe game written in java as a command line game. This game allows for two players with 3x3 and 5x5 game modes. This program does not have any formal AI or automation system, just a standard game "engine." This was written in 2021.

## Demonstration Video:
[Video](https://youtu.be/UgAB2-wpcM0)

## How to use:
Download the [source code](https://github.com/Austin-Daigle/Tic-Tac-Toe-Game/blob/main/ticTacToeInterface.java), compile, and execute and a command line interface will appear with game command prompts.

## How it works: 
The formulation strategies between standard and the extended versions of tic tac toe in my implementations are functionally based on the design implementation strategy. The algorithms behind the game mechanics are based on scalable modifications of the general design. The program starts with an interface prompt that validates the user inputs for each input. If an input is incorrect, the system will print out an error message and recreate the user input prompt to allow the selection of the game mode. The user will select a game mode between quitting (closing the program), the standard mode (3x3 game grid), or the extended mode (5x5 game grid). 
Upon selection, the game “engine” will start for either mode, and the user interface system will alternate between user turns and correct for user input errors. 

The game “engine” works by checking the current status of the game matrix and the current player char and then if the space has not been taken or not. If the space has not been taken, the routine will update the char at the given point at the matrix and then call a series of check methods to detect and validate a possible player win.

The strategy behind the win validation methods is that for each successful move that has just been made, then check if the current play position is either a primary (descending diagonal from the upper-left to bottom right) or secondary diagonal (ascending diagonal from the lower-left to upper-right) and if either of those factors are true then the routines will check the current position for a diagonal win move. For all moves, vertical and horizontal wins are checked for each successful move to check for a player win. If a player win is detected, the game will terminate, and a game over printout will be generated. If the game is in extended mode, the game may terminate if the timer runs out of time. For all game modes, if both players tie, then the game terminates.

## Same Game Screenshot:

![image](https://user-images.githubusercontent.com/100094056/193495680-d281d74f-9af4-4d91-a280-c1e4d1780502.png)
