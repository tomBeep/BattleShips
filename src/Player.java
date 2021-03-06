import java.awt.Graphics;

public class Player {

	private Board hitBoard;// board where all your hits are recorded/displayed
	private Board myBoard;// board where your ships are planned, and your opponents hits recorded

	public Player() {
		hitBoard = new Board();
		myBoard = new Board();// this will probably need to be changed
	}

	public boolean addShip(Ship s) {
		if (myBoard.overlappingShip(s))
			return false;
		myBoard.addShip(s);
		return true;
	}

	public boolean takeShot(Point p, Player oppositon) {
		if (oppositon.getMyBoard().checkShot(p)) {
			hitBoard.markHit(p, true);
			return true;
		}
		hitBoard.markHit(p, false);
		return false;

	}

	public void drawHitBoard(int x, int y, int sizePerSquare, Graphics g, HighlightedArea h) {
		hitBoard.draw(x, y, sizePerSquare, g, false, h);
	}

	public void drawMyBoard(int x, int y, int sizePerSquare, Graphics g, HighlightedArea h) {
		myBoard.draw(x, y, sizePerSquare, g, true, h);
	}

	public Board getHitBoard() {
		return this.hitBoard;
	}

	public Board getMyBoard() {
		return this.myBoard;
	}

}
