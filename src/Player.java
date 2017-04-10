import java.awt.Graphics;

public class Player {

	private Board hitBoard;// board where all your hits are recorded/displayed
	private Board myBoard;// board where your ships are planned, and your opponents hits recorded

	public Player() {
		hitBoard = new Board();
		myBoard = new Board();// this will probably need to be changed
	}

	public void addShip(Ship s) {
		hitBoard.addShip(s);// needs to be changed to my board not hit board
	}

	public void takeShot(Point p) {
		hitBoard.checkShot(p);
	}

	public void draw(int x, int y, int sizePerSquare, Graphics g) {
		hitBoard.draw(x, y, sizePerSquare, g);
	}

}
