
public class Ship {

	private Point[] shipPoints;

	public Ship(Point topLeftCorner, boolean vertical, int size) {
		shipPoints = new Point[size];
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
	
	public Point [] getPoints(){
		return shipPoints;
	}

}
