import java.awt.Graphics;

public class main {

	// a unit is a single square on the board
	public static final int BOARD_UNITS = 14;// height/width
	public static final int UNIT_SIZE = 30;
	public static final int NUMBER_OF_SHIPS = 5;
	public static final int[] SHIP_SIZES = new int[] { 5, 4, 4, 3, 3 };

	public static boolean mainGameStarted = false;
	public static boolean hideBoard = false;
	public static String message = "Please swap players, press any key to continue";

	public highlightedArea current;

	GUI gui;
	Player p1 = new Player();
	Player p2 = new Player();
	boolean player1Turn;
	int numberOfShipsAdded = 0;
	boolean addedShipIsVertical = true;

	public void start() {
		p1 = new Player();
		p2 = new Player();
		current = new highlightedArea();
		setupShips();
		
		// Next Steps,
		// prevent two ships being placed ontop of each other
		// implement an AI to play singlePlayer
		// implement 2 players playing over a network

	}

	public void fireShot() {
		boolean hit;
		if (player1Turn) {
			hit = p1.takeShot(current.highlightedArea[0], p2);
			message = hit ? "HIT ------  \n" : "Miss ------  \n";
			message += "(press any key to continue)";
		} else {
			hit = p2.takeShot(current.highlightedArea[0], p1);
			message = hit ? "HIT ------  \n" : "Miss ------  \n";
			message += "(press any key to continue)";
		}
		checkWin();
		player1Turn = !player1Turn;
		current.newShipToAdd(1);
		hideBoard = true;// hides board untill the next key is pressed, this is so two people can play on one computer
	}

	public void setupShips() {
		mainGameStarted = false;
		current = new highlightedArea();
		player1Turn = true;
		numberOfShipsAdded = 0;
		current.newShipToAdd(SHIP_SIZES[numberOfShipsAdded]);
		gui.redraw();
		gui.text.setText(
				"Set your ships, use the arrow keys to move around and enter to confirm placement, use spacebar to rotate");
	}

	public void startMainGame() {
		mainGameStarted = true;
		current = new highlightedArea();
		current.newShipToAdd(1);// doesnt actually add a ship, just provides a pointer which the player can move around
								// to select a shot
		player1Turn = true;
		gui.redraw();
	}

	public main() {
		gui = new GUI(this);
	}

	public void redrawBoard1(Graphics g) {// your board
		if (player1Turn)
			p1.drawMyBoard(10, 10, UNIT_SIZE, g, current);
		else
			p2.drawMyBoard(10, 10, UNIT_SIZE, g, current);
	}

	public void redrawBoard2(Graphics g) {// hit board
		if (player1Turn)
			p1.drawHitBoard(10, 10, UNIT_SIZE, g, current);
		else
			p2.drawHitBoard(10, 10, UNIT_SIZE, g, current);
	}

	public void addShip() {
		if (player1Turn)
			p1.addShip(new Ship(current.getTopLeft(), addedShipIsVertical, SHIP_SIZES[numberOfShipsAdded]));
		else
			p2.addShip(new Ship(current.getTopLeft(), addedShipIsVertical, SHIP_SIZES[numberOfShipsAdded]));
		numberOfShipsAdded++;
		addedShipIsVertical = true;
		if (numberOfShipsAdded == 5) {
			if (!player1Turn) {
				player1Turn = !player1Turn;
				startMainGame();
				hideBoard = true;
				message = "Player 1 starts. (press any key to continue)";
			} else {
				numberOfShipsAdded = 0;
				current.newShipToAdd(SHIP_SIZES[numberOfShipsAdded]);
				player1Turn = !player1Turn;
				hideBoard = true;
				message = "Player 2's turn to place Ships. (press any key to continue)";
			}
			return;
		}
		current.newShipToAdd(SHIP_SIZES[numberOfShipsAdded]);
	}

	public void checkWin() {
		boolean player1Lost = true;
		boolean player2Lost = true;

		firstLoop: for (Ship s : p1.getMyBoard().getShips()) {
			for (Point p : s.getPoints()) {
				if (!p.isHit()) {
					player1Lost = false;
					break firstLoop;
				}
			}
		}
		firstLoop: for (Ship s : p2.getMyBoard().getShips()) {
			for (Point p : s.getPoints()) {
				if (!p.isHit()) {
					player2Lost = false;
					break firstLoop;
				}
			}
		}
		if (player1Lost) {
			System.out.println("Player 1 lost");
			System.exit(0);
		}
		if (player2Lost) {
			System.out.println("Player 2 lost");
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new main();
	}
}
