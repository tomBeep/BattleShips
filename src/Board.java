import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Board {

	private Ship[] ships;
	private LinkedList<Point> shotsFired = new LinkedList<Point>();

	public Board() {
		ships = new Ship[main.NUMBER_OF_SHIPS];
	}

	/**
	 * Checks whether the ship overlaps with another ship, return true if it does overlap
	 * 
	 * @param s
	 *            ship being added
	 * @return false if the ship does not overlap with another ship
	 */
	public boolean overlappingShip(Ship s) {
		for (int i = 0; i < ships.length; i++) {
			if (ships[i] != null && ships[i].checkOverlap(s)) {
				return true;
			}
		}
		return false;
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
	public void draw(int x, int y, int sizePerSquare, Graphics g, boolean yourBoard, HighlightedArea h) {
		int currentX = x;
		int currentY = y;

		if (h == null)
			return;

		// draw The shots fired
		for (Point p : shotsFired) {
			if (p.isHit())
				g.setColor(Color.red);// hit is red
			else
				g.setColor(Color.black);// miss is black
			g.fillRect(x + sizePerSquare * p.getX(), y + sizePerSquare * p.getY(), sizePerSquare, sizePerSquare);
		}

		// draw The Ships and ShipPoints and whether they are hit
		if (yourBoard) {// only draw on your board
			for (Ship s : ships) {
				if (s == null)// s should never be null but it might be if not enough ships have been added
					continue;
				for (Point p : s.getPoints()) {
					if (s.isDead())
						g.setColor(Color.GRAY);// dead ship is totally grey
					else if (p.isHit())
						g.setColor(Color.ORANGE);// wounded area of ship is orange
					else
						g.setColor(Color.GREEN);// unwounded area of ship is green
					g.fillRect(x + sizePerSquare * p.getX(), y + sizePerSquare * p.getY(), sizePerSquare,
							sizePerSquare);
				}
				g.setColor(Color.RED);
				// draws the outline of each ship
				if (s.vertical) {
					g.drawRect(x + sizePerSquare * s.getPoints()[0].getX() + 2,
							y + sizePerSquare * s.getPoints()[0].getY() + 2, sizePerSquare - 4,
							sizePerSquare * s.getPoints().length - 4);
					g.drawRect(x + sizePerSquare * s.getPoints()[0].getX() + 1,
							y + sizePerSquare * s.getPoints()[0].getY() + 1, sizePerSquare - 2,
							sizePerSquare * s.getPoints().length - 2);
				} else {
					g.drawRect(x + sizePerSquare * s.getPoints()[0].getX() + 2,
							y + sizePerSquare * s.getPoints()[0].getY() + 2, sizePerSquare * s.getPoints().length - 4,
							sizePerSquare - 4);
					g.drawRect(x + sizePerSquare * s.getPoints()[0].getX() + 1,
							y + sizePerSquare * s.getPoints()[0].getY() + 1, sizePerSquare * s.getPoints().length - 2,
							sizePerSquare - 2);
				}
			}
		}

		// draw the base board
		g.setColor(Color.black);
		for (int i = 0; i < main.BOARD_UNITS; i++) {
			for (int j = 0; j < main.BOARD_UNITS; j++) {
				g.drawRect(currentX, currentY, sizePerSquare, sizePerSquare);
				currentX += sizePerSquare;
			}
			currentX = x;
			currentY += sizePerSquare;
		}

		// draw highlighted area
		if ((yourBoard && main.mainGameStarted == false)// if your board and game is in selection phase,
				|| (!yourBoard && main.mainGameStarted == true)) {// else draws it on the other board
			g.setColor(Color.blue);
			for (Point p : h.highlightedArea) {
				if (p != null) {
					// draws a double thickness selection box
					g.drawRect(x + sizePerSquare * p.getX(), y + sizePerSquare * p.getY(), sizePerSquare,
							sizePerSquare);
					g.drawRect(x + sizePerSquare * p.getX() + 1, y + sizePerSquare * p.getY() + 1, sizePerSquare - 2,
							sizePerSquare - 2);
				}
			}
		}

	}

	public Ship[] getShips() {
		return ships;
	}

	public void markHit(Point p, boolean hit) {
		if (hit)
			p.markHit();
		shotsFired.add(p);
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

}
