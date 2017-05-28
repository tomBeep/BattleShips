
public class Ship {

	private Point[] shipPoints;
	boolean vertical;

	public Ship(Point topLeftCorner, boolean vertical, int size) {
		shipPoints = new Point[size];
		this.vertical = vertical;
		for (int i = 0; i < size; i++) {
			shipPoints[i] = topLeftCorner;
			if (vertical)
				topLeftCorner = topLeftCorner.getNewPointFromThisPoint(0, 1);// moveDown
			else
				topLeftCorner = topLeftCorner.getNewPointFromThisPoint(1, 0);// moveRight
		}
	}

	public boolean isDead() {
		for (Point p : shipPoints) {
			if (!p.isHit())// if a point is not hit
				return false;
		}
		return true;
	}

	/**
	 * Checks a single shot, if the shot was a hit, then it is marked as a hit and we return true
	 * 
	 * @param shot
	 * @return
	 */
	public boolean checkShot(Point shot) {
		for (Point p : shipPoints) {
			if (p.getX() == shot.getX() && p.getY() == shot.getY()) {
				p.markHit();
				shot.markHit();// shot is recorded as a hit
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks a single shot, if the shot was a hit, then it is marked as a hit and we return true
	 * 
	 * @param shot
	 * @return true if this ship has a point which overlaps with the new Ship false, if there is no overlap
	 */
	public boolean checkOverlap(Ship newShip) {
		for (Point p1 : newShip.shipPoints)
			for (Point p2 : shipPoints) {
				if (p1.equals(p2)) {
					return true;
				}
			}
		return false;
	}

	public Point[] getPoints() {
		return shipPoints;
	}

}
