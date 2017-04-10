import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Board {

	private Ship[] ships;
	private LinkedList<Point> shotsFired = new LinkedList<Point>();
	private boolean hideShips;

	public Board() {
		ships = new Ship[main.NUMBER_OF_SHIPS];
	}

	/**
	 * @param s
	 *            the ship to add
	 * @return true if ship as added successfully, false if max number of ships reached already
	 */
	public boolean addShip(Ship s) {
		for (int i = 0; i < ships.length; i++) {
			if (ships[i] == null) {
				ships[i] = s;
				return true;
			}
		}
		return false;
	}

	/**
	 * @param x
	 *            the top left y position of board
	 * @param y
	 *            the top left y position of board
	 * @param sizePerSquare
	 *            the amount of space each square of the board should take;
	 */
	public void draw(int x, int y, int sizePerSquare, Graphics g) {
		int currentX = x;
		int currentY = y;

		// draw The shots fired
		for (Point p : shotsFired) {
			if (p.isHit())
				g.setColor(Color.red);// hit is red
			else
				g.setColor(Color.blue);// miss is blue
			g.fillRect(x + sizePerSquare * p.getX(), y + sizePerSquare * p.getY(), sizePerSquare, sizePerSquare);
		}

		if (!hideShips) {
			// draw The Ships and ShipPoints and whether they are hit
			for (Ship s : ships) {
				if (s == null)// s should never be null but it might be if not enough ships have been added
					continue;
				for (Point p : s.getPoints()) {
					if (s.isDead())
						g.setColor(Color.cyan);// dead ship is totally cyan
					else if (p.isHit())
						g.setColor(Color.ORANGE);// wounded area of ship is orange
					else
						g.setColor(Color.GRAY);// unwounded area of ship is grey
					g.fillRect(x + sizePerSquare * p.getX(), y + sizePerSquare * p.getY(), sizePerSquare,
							sizePerSquare);
				}
			}
		}

		// draw the base board
		g.setColor(Color.black);
		for (int i = 0; i < main.BOARD_SIZE; i++) {
			for (int j = 0; j < main.BOARD_SIZE; j++) {
				g.drawRect(currentX, currentY, sizePerSquare, sizePerSquare);
				currentX += sizePerSquare;
			}
			currentX = x;
			currentY += sizePerSquare;
		}
	}

	/**
	 * Takes the shot and checks if it hits anything, modifies all on hit variables and records the shot as well
	 * 
	 * @param shot
	 * @return true if a hit, false if a miss
	 */
	public boolean checkShot(Point shot) {
		shotsFired.add(shot);// records Shot
		for (Ship s : ships) {
			if (s == null)// s should never be null but it might be if not enough ships are made
				continue;
			if (s.checkShot(shot)) {
				return true;
			}
		}
		return false;
	}

	public boolean isHideShips() {
		return hideShips;
	}

	public void setHideShips(boolean hideShips) {
		this.hideShips = hideShips;
	}
}
