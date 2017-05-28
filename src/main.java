import java.awt.Graphics;

public class main {

	// a unit is a single square on the board
	static final int BOARD_UNITS = 14;// height/width
	static final int UNIT_SIZE = 30;
	static final int NUMBER_OF_SHIPS = 5;
	static final int[] SHIP_SIZES = new int[] { 5, 4, 3, 3, 2 };

	public static boolean mainGameStarted = false;
	public static boolean hideBoard = false;
	public static String message = "Please swap players, press any key to continue";

	public HighlightedArea highlightedArea;

	private GUI gui;
	private Player p1 = new Player();
	private Player p2 = new Player();
	private int numberOfShipsAdded = 0;

	boolean player1Turn;
	boolean addedShipIsVertical = true;

	/**
	 * Starts the game
	 */
	public void start() {
		p1 = new Player();
		p2 = new Player();
		highlightedArea = new HighlightedArea();
		setupShips();
	}

	/**
	 * Fires a shot for the player who's turn it is, uses the current highlighted area as the location of the shot
	 */
	public void fireShot() {
		boolean hit;
		if (player1Turn) {
			hit = p1.takeShot(highlightedArea.highlightedArea[0], p2);
			message = hit ? "HIT ------  \n" : "Miss ------  \n";
			message += "(press any key to continue)";
		} else {
			hit = p2.takeShot(highlightedArea.highlightedArea[0], p1);
			message = hit ? "HIT ------  \n" : "Miss ------  \n";
			message += "(press any key to continue)";
		}
		checkWin();
		player1Turn = !player1Turn;
		highlightedArea.newArea(1);
		hideBoard = true;// hides board untill the next key is pressed, this is so two people can play on one computer
	}

	/**
	 * starts the process of setting up ships
	 */
	public void setupShips() {
		mainGameStarted = false;
		highlightedArea = new HighlightedArea();
		player1Turn = true;
		numberOfShipsAdded = 0;
		highlightedArea.newArea(SHIP_SIZES[numberOfShipsAdded]);
		gui.redraw();
		gui.text.setText(
				"Set your ships, use the arrow keys to move around and enter to confirm placement, use spacebar to rotate");
	}

	public void startMainGame() {
		mainGameStarted = true;
		highlightedArea = new HighlightedArea();
		highlightedArea.newArea(1);// doesnt actually add a ship, just provides a pointer which the player can move
									// around
									// to select a shot
		player1Turn = true;
		gui.redraw();
	}

	public main() {
		gui = new GUI(this);
	}

	/**
	 * Attempts to add a ship at the location specified by the current highlighted area
	 */
	public void addShip() {
		boolean addedCorrectly;
		if (player1Turn) {
			addedCorrectly = p1.addShip(
					new Ship(highlightedArea.getTopLeft(), addedShipIsVertical, SHIP_SIZES[numberOfShipsAdded]));
		} else {
			addedCorrectly = p2.addShip(
					new Ship(highlightedArea.getTopLeft(), addedShipIsVertical, SHIP_SIZES[numberOfShipsAdded]));
		}
		if (!addedCorrectly) {// the ship was overlappiong with another ship
			gui.text.setText("Cannot place a ship which overlaps with another ship");
			highlightedArea.newArea(SHIP_SIZES[numberOfShipsAdded]);
			return;
		}

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
				highlightedArea.newArea(SHIP_SIZES[numberOfShipsAdded]);
				player1Turn = !player1Turn;
				hideBoard = true;
				message = "Player 2's turn to place Ships. (press any key to continue)";
			}
			return;
		}
		highlightedArea.newArea(SHIP_SIZES[numberOfShipsAdded]);
	}

	/**
	 * Checks whether the game is won, if the game is won, then it ends the programme.
	 */
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

	/**
	 * Redraws board 1 (The left hand board).
	 * 
	 * @param g
	 */
	public void redrawBoard1(Graphics g) {// your left board
		if (player1Turn)
			p1.drawMyBoard(10, 10, UNIT_SIZE, g, highlightedArea);
		else
			p2.drawMyBoard(10, 10, UNIT_SIZE, g, highlightedArea);
	}

	/**
	 * Redraws board 2 (the right hand board)
	 * 
	 * @param g
	 */
	public void redrawBoard2(Graphics g) {// your right board
		if (player1Turn)
			p1.drawHitBoard(10, 10, UNIT_SIZE, g, highlightedArea);
		else
			p2.drawHitBoard(10, 10, UNIT_SIZE, g, highlightedArea);
	}

	public static void main(String[] args) {
		new main();
	}
}
