import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Ai Homework #1 - TicTacToe
 * @version 1.0
 * @author Austin Daigle
 * @since 9/1/2021
 *
 * This program generates a standard 
 * (or expanded) game of tic tac toe between 
 * two players. 
 */
public class ticTacToeInterface {
	//create game-state variables
	static boolean isWinnerPresent = false;
	static int amountOfTakenSpaces = 0;
	static long startTime = 0;
	//how many minutes the expanded game timer limits
	static int maxTimerMinutes = 3;
	
	/*
	 * Primary Interfaces
	 */
	
	//Main method that starts the primary user interface.
	public static void main(String[] args) {
		startInterface();
	}
	
	/**
	 * This method returns the string output 
	 * from the scanner input prompt
	 * @return This is the string output from the scanner.
	 */
	public static String getScannerInput()
	{
		//Create Scanner object
		Scanner userInput = new Scanner(System.in);
		//Print terminal user input cursor
		System.out.print("> ");
		//Create the String variable input and set that to the Scanner object
		String input = userInput.nextLine();
		input = input.replace(" ", "");
		//Clear the Scanner object value
		userInput = null;
		return input;
	}
	
	/**
	 * This method prints out the main user prompt 
	 * and generates the user input prompts for 
	 * starting the selected version of tictactoe.
	 */
	public static void startInterface() {
		System.out.println("------------------ Tic Tac Toe 1.0v -----------------------"
				+ "\nEnter A to play TicTacToe in normal mode, "
				+ "\nEnter B to play TicTacToe in expanded 5x5 mode."
				+ "\nHelp?: You can also get help at any time while playing "
				+ "\n\tby entering \"help\""
				+ "\n[NOTE]: You can quit at any time by entering \"quit\""
				+ "\n-----------------------------------------------------------"
				+ "\nPlease Enter a game mode:");
		//variable to store the mode selection from the scanner
		String modeInput = getScannerInput();
		//user input is "A" start the game in standard mode.
		if(modeInput.equalsIgnoreCase("A")) {
			standardGame();
		}
		//user input is "B" start the game in expanded mode.
		else if(modeInput.equalsIgnoreCase("B")) {
			expandedGame();
		}
		//user input is "quit" then terminate all processes.
		else if(modeInput.equalsIgnoreCase("quit")) {
			System.out.println("[Process] Closing Program");
			System.exit(0);
		}
		//all other unrecognized inputs are classified as error inputs.
		else {
			System.out.println("[Error] input not recognized");
			startInterface();
		}
	}
	
	/**
	 * This method is design to create the basic 
	 * starting interface for the standard version
	 * of the tictactoe game.
	 */
	public static void standardGame() {
		System.out.println("Loading Tic Tac Toe in 3x3 mode...");
		System.out.println("Player A: X");
		System.out.println("Player B: O");
		//create game board
		char[][] tictactoe = new char[3][3];
		//clear all variables.
		amountOfTakenSpaces = 0;
		isWinnerPresent = false;
		char currentPlayerTurn = 'X';
		//start the standard game routine
		standardGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
	}
	
	/**
	 * This method is designed to recurse upon itself 
	 * until the ending criteria are met: run out of spaces (tie)
	 * or a winner is found.
	 * 
	 * This method functions by processing if the input for the 
	 * selected space is valid and also if the current play 
	 * complete the end-case requirements
	 * @param tictactoe This is the game board at is current state
	 * @param amountOfTakenSpaces This is the amount of taken spaces on the board
	 * @param currentPlayerTurn This is the current char representation of who is
	 * currently playing.
	 */
	public static void standardGameSubroutine(char[][] tictactoe, 
		int amountOfTakenSpaces, char currentPlayerTurn) {
		//If a winner has been identified.
		if(amountOfTakenSpaces <= 9 && isWinnerPresent) {
			//Print the final game as it has been won
			printGame(tictactoe);
			//print out the winning message with respect to the inverted player char
			if(currentPlayerTurn == 'X') {
				System.out.println("Player B has Won!");
			}
			else {
				System.out.println("Play A has Won!");
			}
			System.out.println("GAME OVER");
		}
		/*
		 * If the amount of taken spaces has reached the limit and there is not winner
		 * then declare a tie. 
		 */
		else if(amountOfTakenSpaces == 9 && !isWinnerPresent) {
			printGame(tictactoe);
			System.out.println("GAME OVER: TIE");
		}
 		else {
 			//print game for the turn
			printGame(tictactoe);
			//print out the prompt
			if(currentPlayerTurn == 'X') {
				System.out.println("Player A Input: ");
			} 
			else {
				System.out.println("Player B Input: ");
			}
			//store the prompt input to playerSelection
			String playerSelection = getScannerInput();
			//If the input is "quit" then close the program
			if(playerSelection.equalsIgnoreCase("quit")) {
				System.out.println("[Process] Closing Program");
				System.exit(0);
			}
			/* If the input is "help" then printout the 
			 * correct form of the help screen to the game session.
			 */
			if(playerSelection.equalsIgnoreCase("Help")) {
				System.out.println("[Process] Print Help screen...");
				//print help screen
				printHelp(true);
				//reprint the game screen with no changes.
				standardGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
			}
			
			try { 
				//try to convert the string to an int
				int playerSelectionInt = Integer.parseInt(playerSelection);
				//if selection is between 0 and 8
				if(playerSelectionInt > -1 && playerSelectionInt < 9) {
					//if the selection is between 0 and 2
					if(playerSelectionInt >= 0 && playerSelectionInt <= 2) {
						//if the current play does not already been used
						if(!(tictactoe[0][playerSelectionInt] == 'X') && !(tictactoe[0][playerSelectionInt] == 'O')) {
						 	//if the current player is player a
							if(currentPlayerTurn == 'X') {
								//set the current play on the game board to X
						 		tictactoe[0][playerSelectionInt] = 'X';
						 		//update the amount of used spaces
						 		amountOfTakenSpaces++;
						 		//check to see if the current play has resulted in the current player winning.
						 		isWinnerPresent = hasWon(0,playerSelectionInt,tictactoe,'X');
						 		//update the current game call with the new game state as the opposite player
						 		standardGameSubroutine(tictactoe,amountOfTakenSpaces,'O');
						 	}
							//if the current play is player b
						 	else {
						 		//set the current play on the game board to O
						 		tictactoe[0][playerSelectionInt] = 'O';
						 		//update the amount of used spaces 
						 		amountOfTakenSpaces++;
						 		//check to see if the current play has resulted in the current player winning.
						 		isWinnerPresent = hasWon(0,playerSelectionInt,tictactoe,'O');
						 		//update the current game call with the new game state as the opposite player
						 		standardGameSubroutine(tictactoe,amountOfTakenSpaces,'X');
						 	}
						}
						else {
							//print out an error message for a unusable input
							System.out.println("[Error] Space already taken.");
							//reprint the game interface with no changes.
							standardGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
						}
					}
					
					//if the input is between 3 and 5 
					if(playerSelectionInt >= 3 && playerSelectionInt <= 5) {
						//if the space is not occupied
						if(!(tictactoe[1][playerSelectionInt-3] == 'X') && !(tictactoe[1][playerSelectionInt-3] == 'O')) {
						 	//if the player is X
							if(currentPlayerTurn == 'X') {
								//update the char at the given point to X
						 		tictactoe[1][playerSelectionInt-3] = 'X';
						 		//update the amount of used spaces
						 		amountOfTakenSpaces++;
						 		//check for a winning game-state for the current player
						 		isWinnerPresent = hasWon(1,playerSelectionInt-3,tictactoe,'X');
						 		//update the game call 
						 		standardGameSubroutine(tictactoe,amountOfTakenSpaces,'O');
						 	}
						 	else {
						 		//if the player is O
						 		tictactoe[1][playerSelectionInt-3] = 'O';
						 		//update the char at the given point to O
						 		amountOfTakenSpaces++;
						 		//update the amount of used spaces
						 		isWinnerPresent = hasWon(1,playerSelectionInt-3,tictactoe,'O');
						 		//check for a winning game-state for the current player
						 		standardGameSubroutine(tictactoe,amountOfTakenSpaces,'X');
						 		//update the game call
						 	}
						}
						else {
							//if the space has already been taken then print a error message
							System.out.println("[Error] Space already taken.");
							//call the game with no changes to the game-state.
							standardGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
						}
					}
					//if the input is between 6 and 8
					if(playerSelectionInt >= 6 && playerSelectionInt <= 8) {
						//if the current selected space is empty
						if(!(tictactoe[2][playerSelectionInt-6] == 'X') 
								&& !(tictactoe[2][playerSelectionInt-6] == 'O')) {
						 	//if the current player is X
							if(currentPlayerTurn == 'X') {
								//update the char at the given point to X
						 		tictactoe[2][playerSelectionInt-6] = 'X';
						 		//update the amount of used spaces used in game
						 		amountOfTakenSpaces++;
						 		//check for a winning state for the current player
						 		isWinnerPresent = hasWon(2,playerSelectionInt-6,tictactoe,'X');
						 		//call the game with the player changed over
						 		standardGameSubroutine(tictactoe,amountOfTakenSpaces,'O');
						 	}
							//If the currenet player is O
						 	else {
						 		//update the char at the given point to O
						 		tictactoe[2][playerSelectionInt-6] = 'O';
						 		//update the amount of used spaces
						 		amountOfTakenSpaces++;
						 		//check for a winning state for the current player
						 		isWinnerPresent = hasWon(2,playerSelectionInt-6,tictactoe,'O');
						 		//call the game with the player changed over
						 		standardGameSubroutine(tictactoe,amountOfTakenSpaces,'X');
						 	}
						}
						else {
							//If the given space has already been taken then printout an error
							System.out.println("[Error] Space already taken.");
							//call the game with no changes.
							standardGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
						}

					}
				}
				else {
					//printout a errr message if that space has already been used on the game board
					System.out.println("[Error] Input out of range of game.");
					//call the game with no changes
					standardGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
				}
			}
			//If the input is incorrect
			catch (NumberFormatException e) {
				//print out an error message				
				System.out.println("[Error] Incorrect Input");
				//call the game with no changes 
				standardGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
			}
		}
		
	}
	
	/**
	 * This method call the subroutine 
	 * for the expanded 5x5 version 
	 * of the tic tac toe game.
	 */
	public static void expandedGame() {
		System.out.println("Loading Tic Tac Toe in expanded 5x5 mode");
		System.out.println("Player A: X");
		System.out.println("Player B: O");
		//create the expanded game board
		char[][] tictactoe = new char[5][5];
		//set all general player variables to zero
		amountOfTakenSpaces = 0;
		isWinnerPresent = false;
		//set the first player to X
		char currentPlayerTurn = 'X';
		//call the game
		
		//create and update timer routine
		
		System.out.println("[Timer] This game features of "+maxTimerMinutes+"\n"
				+ " minute timer, if you exceed the "+maxTimerMinutes+" minutes\n"
				+ " the game will return any last inputs with a game over message\n"
				+ " indicating that you are out of time.");
		startTime = System.nanoTime();
        expandedGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);

	}
	
	/**
	 * This method is designed to recurse upon itself 
	 * until the ending criteria are met: run out of spaces (tie)
	 * or a winner is found.
	 * 
	 * This method functions by processing if the input for the 
	 * selected space is valid and also if the current play 
	 * complete the end-case requirements
	 * If difference between this method and the stardardGameSubroutine()
	 * is that this method has been scaled up to account for the presents 
	 * of twenty five spaces.
	 * 
	 * [NOTE] This method features a 2 minute timer.
	 * @param tictactoe
	 * @param amountOfTakenSpaces
	 * @param currentPlayerTurn
	 */
	public static void expandedGameSubroutine(char[][] tictactoe, 
			int amountOfTakenSpaces, char currentPlayerTurn) {
		//if the amount of elasped time is over 3 minutes (
		if(TimeUnit.SECONDS.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS) > (maxTimerMinutes * 60)) {
			System.out.println("[Timer] Out of Time...");
			System.out.println("GAME OVER: No one won...");
			//if there is no more time then terminate the game.
			System.exit(0);
		}
		
		//If a winner has been found
		if(amountOfTakenSpaces <= 25 && isWinnerPresent) {
			//print the final game board
			printGame(tictactoe);
			//print which player has won according to the results
			if(currentPlayerTurn == 'X') {
				System.out.println("Player B has Won!");
			}
			else {
				System.out.println("Play A has Won!");
			}
			//print game over
			System.out.println("GAME OVER");
		}
		//If the max amount of taken spaces has been reach with no winner then declare a tie
		else if(amountOfTakenSpaces == 25 && !isWinnerPresent) {
			//print out the game and game over prompt
			printGame(tictactoe);
			System.out.println("GAME OVER: TIE");
		}
		//allow the game to be played 
		else {
			//printout the game board
			printGame(tictactoe);
			//if the current player is X
			if(currentPlayerTurn == 'X') {
				//printout the player B input with respect to inversion
				System.out.println("Player B Input: ");
			} 
			else {
				//printout the player A input with respect to inversion
				System.out.println("Player A Input: ");
			}
			//store the scanner input prompt to the variable playerSelection
			String playerSelection = getScannerInput();
			//if the input is "quit" then terminate the process.
			if(playerSelection.equalsIgnoreCase("quit")) {
				System.out.println("[Process] Closing Program");
				System.exit(0);
			}
			//If the input is "help" then terminate the process.
			if(playerSelection.equalsIgnoreCase("Help")) {
				System.out.println("[Process] Print Help screen...");
				//print the expanded the game board printout 
				printHelp(false);
				//call the game with no changes
				expandedGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
			}
			
			try { 
				//try to convert the string to an int
				int playerSelectionInt = Integer.parseInt(playerSelection);
				//if the input is between 0 and 24
				if(playerSelectionInt > -1 && playerSelectionInt < 25) {
					//if the input is between 0 and 4
					if(playerSelectionInt >= 0 && playerSelectionInt <= 4) {
						//check to see if the current space is empty
						if(!(tictactoe[0][playerSelectionInt] == 'X') && !(tictactoe[0][playerSelectionInt] == 'O')) {
						 	//if the current player is X
							if(currentPlayerTurn == 'X') {
								//set the char at the given location on the game board.
						 		tictactoe[0][playerSelectionInt] = 'X';
						 		//update the amount of taken spaces
						 		amountOfTakenSpaces++;
						 		//check if the current player is a winner
						 		isWinnerPresent = hasWon(0,playerSelectionInt,tictactoe,'X');
						 		//call the game with the next player
						 		expandedGameSubroutine(tictactoe,amountOfTakenSpaces,'O');
						 	}
							//If the player is O
						 	else {
						 		//Set the char at the location to O
						 		tictactoe[0][playerSelectionInt] = 'O';
						 		//update the amount of taken spaces
						 		amountOfTakenSpaces++;
						 		//check if the current player is a winner
						 		isWinnerPresent = hasWon(0,playerSelectionInt,tictactoe,'O');
						 		//call the game with the next player
						 		expandedGameSubroutine(tictactoe,amountOfTakenSpaces,'X');
						 	}
						}
						else {
							//print error message
							System.out.println("[Error] Space already taken.");
							//call game with no changes
							expandedGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
						}	
					}
					//If the input is between 5 and 9
					if(playerSelectionInt >= 5 && playerSelectionInt <= 9) {
						//if the space has not been used
						if(!(tictactoe[1][playerSelectionInt-5] == 'X') && !(tictactoe[1][playerSelectionInt-5] == 'O')) {
						 	//if the current player is X
							if(currentPlayerTurn == 'X') {
								//set the char at the location to X
						 		tictactoe[1][playerSelectionInt-5] = 'X';
						 		//update the amount of taken spaces
						 		amountOfTakenSpaces++;
						 		//check if a winner is present from the last move
						 		isWinnerPresent = hasWon(1,playerSelectionInt-5,tictactoe,'X');
						 		//call the game with the updated next player.
						 		expandedGameSubroutine(tictactoe,amountOfTakenSpaces,'O');
						 	}
							//if the current player is O
						 	else {
						 		//set the char at the location to O
						 		tictactoe[1][playerSelectionInt-5] = 'O';
						 		//update the amount of taken spaces
						 		amountOfTakenSpaces++;
						 		//check if a winner is present from the last move
						 		isWinnerPresent = hasWon(1,playerSelectionInt-5,tictactoe,'O');
						 		//call the game with the updated next player
						 		expandedGameSubroutine(tictactoe,amountOfTakenSpaces,'X');
						 	}
						}
						else {
							//print given error message for a space that has already been taken
							System.out.println("[Error] Space already taken.");
							//call the game with no updated conditions.
							expandedGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
						}	
					}
					
					//If the input is between 10 and 14
					if(playerSelectionInt >= 10 && playerSelectionInt <= 14) {
						//check if the space has not already been taken.
						if(!(tictactoe[2][playerSelectionInt-10] == 'X') && !(tictactoe[2][playerSelectionInt-10] == 'O')) {
						 	//if the current player is X
							if(currentPlayerTurn == 'X') {
								//update the char at the location to X
								tictactoe[2][playerSelectionInt-10] = 'X';
								//update the amount of taken spaces.
						 		amountOfTakenSpaces++;
						 		//check to see if the current player is a winner.
						 		isWinnerPresent = hasWon(2,playerSelectionInt-10,tictactoe,'X');
						 		//call the game with the updated next player.
						 		expandedGameSubroutine(tictactoe,amountOfTakenSpaces,'O');
						 	}
						 	else {
						 		//update the char at the location to O
						 		tictactoe[2][playerSelectionInt-10] = 'O';
						 		//update the amount of taken spaces
						 		amountOfTakenSpaces++;
						 		//check to see if the current player is a winner
						 		isWinnerPresent = hasWon(2,playerSelectionInt-10,tictactoe,'O');
						 		//call the game with the updated next player
						 		expandedGameSubroutine(tictactoe,amountOfTakenSpaces,'X');
						 	}
						}
						else {
							//print out the error message
							System.out.println("[Error] Space already taken.");
							//call the game with no updated conditions.
							expandedGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
						}	
					}
					
					//If the input is between 15 and 19
					if(playerSelectionInt >= 15 && playerSelectionInt <= 19) {
						//check if the selected char space is empty
						if(!(tictactoe[3][playerSelectionInt-15] == 'X') && !(tictactoe[3][playerSelectionInt-15] == 'O')) {
						 	//check if the current player is X
							if(currentPlayerTurn == 'X') {
								//update char at the location to X
						 		tictactoe[3][playerSelectionInt-15] = 'X';
						 		//update the amount of taken spaces
						 		amountOfTakenSpaces++;
						 		//check to see if the current player has won.
						 		isWinnerPresent = hasWon(3,playerSelectionInt-15,tictactoe,'X');
						 		//call the game with the updated next player details
						 		expandedGameSubroutine(tictactoe,amountOfTakenSpaces,'O');
						 	}
						 	else {
						 		//update char at the location to O
						 		tictactoe[3][playerSelectionInt-15] = 'O';
						 		//update the amount of taken spaces 
						 		amountOfTakenSpaces++;
						 		//check to see if the current player has won
						 		isWinnerPresent = hasWon(3,playerSelectionInt-15,tictactoe,'O');
						 		//call the game with the updated next player details
						 		expandedGameSubroutine(tictactoe,amountOfTakenSpaces,'X');
						 	}
						}
						else {
							//printout the error message
							System.out.println("[Error] Space already taken.");
							//call the game with no updated conditions
							expandedGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
						}	
					}
					
					//check to see if the input is between 20 and 24
					if(playerSelectionInt >= 20 && playerSelectionInt <= 24) {
						//check to see if the selected char is empty
						if(!(tictactoe[4][playerSelectionInt-20] == 'X') && !(tictactoe[4][playerSelectionInt-20] == 'O')) {
						 	//if the current player is X
							if(currentPlayerTurn == 'X') {
								//update the char at the current location to X
						 		tictactoe[4][playerSelectionInt-20] = 'X';
						 		//update the amount of taken spaces
						 		amountOfTakenSpaces++;
						 		//check to see if the current player has won
						 		isWinnerPresent = hasWon(4,playerSelectionInt-20,tictactoe,'X');
						 		//call the game with the updated next player
						 		expandedGameSubroutine(tictactoe,amountOfTakenSpaces,'O');
						 	}
						 	else {
						 		//update the char at the current location to O
						 		tictactoe[4][playerSelectionInt-20] = 'O';
						 		//update the amount of taken spaces
						 		amountOfTakenSpaces++;
						 		//check to see if the current player has won
						 		isWinnerPresent = hasWon(4,playerSelectionInt-20,tictactoe,'O');
						 		//call the game with the updated next player
						 		expandedGameSubroutine(tictactoe,amountOfTakenSpaces,'X');
						 	}
						}
						else {
							//printout the error message
							System.out.println("[Error] Space already taken.");
							//call the game with no updated conditions
							expandedGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
						}	
					}
				}
				else {
					//printout the error message
					System.out.println("[Error] Input out of range of game.");
					//call the game with no updated conditions
					expandedGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
				}
			}
			catch (NumberFormatException e) {
				//printout the error message
				System.out.println("[Error] Incorrect Input");
				//call the game with no updated conditions
				expandedGameSubroutine(tictactoe,amountOfTakenSpaces,currentPlayerTurn);
			}
		}
	}
	
	/*
	 * Game Subroutines
	 */

	/**
	 * This method analyzes the current play position
	 * using the row and column parameters to detemermine 
	 * if their apart of any diagonal, horizontal, or 
	 * vertical matches. This method returns a true
	 * if any winning matches are found
	 * @param row This is the row variable for the given move
	 * @param column This is the column variable for the given move
	 * @param tictactoe This is the game board char array
	 * @param player This is the char representing the current player
	 * @return returns a boolean indicating a win
	 */
	public static boolean hasWon(int row, int column, char[][] tictactoe, char player) {
		
		boolean result = false;
		
		//Check if the current position is apart of the primary diagonal
		if(isPartOfPrimaryDiagonal(row,column,tictactoe)) {
			//check to see if the current line has a match
			if(lineHasMatch(getPrimaryDiagonal(tictactoe), player)) {
				result = true;
			}
		}
		//Check if the current position is apart of the secondary diagonal
		if(isPartOfSecondaryDiagonal(row,column,tictactoe)) {
			//check to see if the current line has a match
			if(lineHasMatch(getSecondaryDiagonal(tictactoe), player)) {
				result = true;
			}
		}
		
		//check for a vertical match
		if(lineHasMatch(getHorizontal(row,tictactoe),player)) {
			result = true;
		}
		//check for a horizontal match
		if(lineHasMatch(getVertical(column,tictactoe),player)) {
			result = true;
		}
		
		if(result == true) {
			return true;
		}
		return false;
		
	}

	/**
	 * This method returns a boolean if the current location
	 * from the row and column is apart of a primary diagonal.
	 * @param row This is the row variable for the given move
	 * @param column This is the column variable for the given move
	 * @param tictactoe This is the game board char array
	 * @return returns a boolean that indicates if the location is apart of a primary diagonal.
	 */
	public static boolean isPartOfPrimaryDiagonal(int row, int column, char[][] tictactoe) {
		//create the variables for iterating through the game matrix
		int diagonalRow = 0;
		int diagonalColumn = 0;
		//check through all primary diagonals in the matrix and compare that to the parameters.
		for(int i = 0; i < tictactoe.length; i++) {
			if(diagonalRow == row && diagonalColumn == column) {
				return true;
			}
			diagonalRow++;
			diagonalColumn++;
		}
		return false;
	}
	
	/**
	 * This method returns a boolean if the current location
	 * from the row and column is apart of a secondary diagonal.
	 * @param row This is the row variable for the given move
	 * @param column This is the column variable for the given move
	 * @param tictactoe This is the game board char array
	 * @return returns a boolean that indicates if the location is apart of a secondary diagonal.
	 */
	public static boolean isPartOfSecondaryDiagonal(int row, int column, char[][] tictactoe) {
		//create the variables for iterating through the game matrix
		int diagonalRow = tictactoe.length-1;
		int diagonalColumn = 0;
		//check through all secondary diagonals in the matrix and compare that to the parameters.
		for(int i = 0; i < tictactoe.length; i++) {
			if(diagonalRow == row && diagonalColumn == column) {
				return true;
			}
			diagonalRow--;
			diagonalColumn++;
		}
		return false;
	}

	/**
	 * This method returns a char array of the secondary diagonal
	 * of the tictactoe matrix
	 * @param tictactoe This is the game board array
	 * @return returns the secondary diagonal array.
	 */
	public static char[] getSecondaryDiagonal(char[][] tictactoe) {
		//create a char array with the length of tictactoe
		char[] diagonal = new char[tictactoe.length];
		//create stater variables.
		int row = 0;
		int column = tictactoe.length-1;
		//for every secondary diagonal in the matrix tictactoe
		for(int i = 0; i < tictactoe.length; i++) {
			diagonal[i] = tictactoe[row][column];
			row++;
			column--;
		}
		//return the char array.
		return diagonal;
	}
	
	/**
	 * This method returns a char array of the primary diagonal
	 * of the tictactoe matrix
	 * @param tictactoe This is the game board array
	 * @return returns the primary diagonal array.
	 */
	public static char[] getPrimaryDiagonal(char[][] tictactoe) {
		//create a char array with the length of tictactoe
		char[] diagonal = new char[tictactoe.length];
		//create stater variables.
		int row = 0;
		int column = 0;
		//for every primary diagonal in the matrix tictactoe
		for(int i = 0; i < tictactoe.length; i++) {
			diagonal[i] = tictactoe[row][column];
			row++;
			column++;
		}
		//return char array
		return diagonal;
	}

	/**
	 * This method returns a char array of a horizontal line
	 * from the given row
	 * @param row This is the row number that is used to return 
	 * the horizontal line.
	 * @param tictactoe This is the game matrix where givens are 
	 * being returned from
	 * @return This is the returned horizontal line that is return
	 * from the row.
	 */
	public static char[] getHorizontal(int row, char[][] tictactoe) {
		//return the char array from the tictactoe matrix at the row.
		char[] horizontal = tictactoe[row];
		return horizontal;
	}
	
	/**
	 * This method returns a char array of a horizontal line
	 * from the given row
	 * @param row This is the row number that is used to return 
	 * the horizontal line.
	 * @param tictactoe This is the game matrix where givens are 
	 * being returned from
	 * @return This is the returned horizontal line that is return
	 * from the row.
	 */
	public static char[] getVertical(int column, char[][] tictactoe) {
		char[] vertical = new char[tictactoe.length];
		//for every vertical element in the matrix tictactoe
		for(int i = 0; i < tictactoe.length; i++) {
			vertical[i] = tictactoe[i][column];
		}
		//return the char[] array
		return vertical;
	}

	/**
	 * This method returns a boolean if the given line 
	 * has only matched char to the given char variable.
	 * @param gameLine This is the char array that is being checked
	 * @param player This is the char that is used to find the full match
	 * @return This method returns a boolean for the status of a line match
	 */
	public static boolean lineHasMatch(char[] gameLine, char player) {
		//for every element in gameLine check to see if there are any non-matching chars
		for(int i = 0; i < gameLine.length; i++) {
			if(!(gameLine[i] == player)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method checks if the given char array has any 
	 * chars from an opposing player.
	 * @param gameLine This is the char array that is search through
	 * @param player This is the char that is being compared to and 
	 * inverted against to tell if opposing moves have been made on 
	 * the given char array.
	 * @return This method returns a boolean indicating the status of 
	 * the array analysis.
	 */
	public static boolean lineHasOpposition(char[] gameLine, char player) {
		//check to see if player who is X has any O player marks on the array.
		if(player == 'X') {
			//for every items in the char array
			for(int i = 0; i < gameLine.length; i++) {
				//if the element has O
				if(gameLine[i] == 'O') {
					return true;
				}
			}
		}
		//check to see if player who is C has any X player marks on the array.
		if(player == 'O') {
			//for every items in the char array
			for(int i = 0; i < gameLine.length; i++) {
				//if the element has X
				if(gameLine[i] == 'X') {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method printout a numerical refference screen
	 * to help those who are confused about the command
	 * line interface.
	 * @param normalGameMode When true, this returns a 
	 * printout for the standard tictactoe mode, when false,
	 * this returns a printout for the expanded tictactoe printout.
	 */
	public static void printHelp(boolean normalGameMode) {
						   
		int[][] smallGame = {{0,1,2},{3,4,5},{6,7,8}};
		int[][] largeGame = {{0,1,2,3,4},
							 {5,6,7,8,9},
							 {10,11,12,13,14},
							 {15,16,17,18,19},
							 {20,21,22,23,24}};
		
		
		System.out.println("==================== Tic Tac Toe Help ======================="
				+ "\nTo play: When it is your turn, enter the corresponding\n"
				+ "number that corresponds to the location on the game board.\n"
				+ "If both players tie in-game, then the game will terminate\n"
				+ "with a statement indicating that a tie between players\n"
				+ "has been reached. If a player were to win, then the\n"
				+ "game will terminate and the final score will be displayed.\n"
				+ "Use the game printoff below as a input refference for\n"
				+ "how to enter your game moves.\n"
				+ "-------------------------------------------------------------\n");
			if(normalGameMode) {
				System.out.println("Game Board Refference for normal 3x3 mode:");
				printGame(smallGame);
			}
			else {
				System.out.println("\"Game Board Refference for expanded 5x5 mode:");
				printGame(largeGame);
			}
			System.out.println("=============================================================\n");	
	}
	
	/**
	 * This method prints out the whole game interface
	 * @param game This is the char array matrix that is printed out
	 * This method only take the char array version of the interface.
	 */
	public static void printGame(char[][] game) {
		
		//create border using the given amount of entries found
		String printBorder = "";
		if(game.length == 1) {
			printBorder = "-----";
		}
		else {
			printBorder = printBorder + "-----";
			for(int i = 0; i < game.length-1; i++) {
				printBorder = printBorder + "----";
			}
		}
		//print out the matrix with respect to the size and shape of the interface.
		System.out.println(printBorder);
		for(int column = 0; column < game.length; column++) {
			for(int row = 0; row < game[column].length; row++) {
				if(row == 0) {
					System.out.print("| "+game[column][row]+" |");
				}
				else {
					System.out.print(" "+game[column][row]+" |");
				}
			}
			System.out.println("\n"+printBorder);
		}

	}

	/**
	 * This method prints out the whole game interface
	 * @param game This is the int array matrix that is printed out
	 * This method only take the int array version of the interface.
	 */
	public static void printGame(int[][] game) {
		
		//create border using the given amount of entries found
		String printBorder = "";
		if(game.length == 1) {
			printBorder = "-----";
		}
		else {
			printBorder = printBorder + "-----";
			for(int i = 0; i < game.length-1; i++) {
				printBorder = printBorder + "----";
			}
		}
		//print out the matrix with respect to the size and shape of the interface.
		System.out.println(printBorder);
		for(int column = 0; column < game.length; column++) {
			for(int row = 0; row < game[column].length; row++) {
				if(row == 0) {
					System.out.print("| "+game[column][row]+" |");
				}
				else {
					System.out.print(" "+game[column][row]+" |");
				}
			}
			System.out.println("\n"+printBorder);
		}

	}

}
