import java.awt.Graphics;

public class main {

	public static final int BOARD_SIZE = 10;
	public static final int NUMBER_OF_SHIPS = 5;

	GUI gui;
	Player p1 = new Player();
	Player p2 = new Player();

	public void start() {
		p1.addShip(new Ship(new Point(0, 0), true, 4));
		p1.takeShot(new Point(4, 4));
		p1.takeShot(new Point(9, 2));
		p1.takeShot(new Point(2, 5));
		p1.takeShot(new Point(0, 1));

		// Next Steps,
		// implement the different stages of the game
		// Check for a win condition
		// implement mouse selection and textbox(keyboard) selection
		// implement a way for two players to play on 1 computer
		// implement an AI to play singlePlayer
		// implement scaling graphics
		// implement 2 players playing over a network

	}

	public main() {
		gui = new GUI(this);
	}

	public void redrawBoard1(Graphics g) {
		p1.draw(10, 10, 30, g);
		gui.redraw();// makes gui repaint board
	}

	public void redrawBoard2(Graphics g) {
		p2.draw(10, 10, 30, g);
		gui.redraw();// makes gui repaint board
	}

	public static void main(String[] args) {
		new main();
	}
}
