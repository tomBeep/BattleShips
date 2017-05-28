
/**
 * @author Thomas Edwards
 *         Points are 0 to size-1 of the board
 */
public class Point {

	private boolean hit;
	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		hit = false;
	}

	public Point(int x, int y, boolean hit) {
		this.x = x;
		this.y = y;
		this.hit = hit;
	}

	public void movePoint(int dx, int dy) {
		if (!canMovePoint(dx, dy))
			return;

		x = x + dx;
		y = y + dy;
	}

	public Point rotateFromPoint(Point origin) {
		if (this.x == origin.x) {// we are going vertical to horizontal
			return new Point(origin.x + this.y - origin.y, origin.y);
		} else {// we are going horizontal to vertical
			return new Point(origin.x, origin.y + this.x - origin.x);
		}
	}

	public boolean canMovePoint(int dx, int dy) {
		if (x + dx < 0 || x + dx >= main.BOARD_UNITS)
			return false;
		if (y + dy < 0 || y + dy >= main.BOARD_UNITS)
			return false;
		return true;
	}

	public Point getNewPointFromThisPoint(int dx, int dy) {
		return new Point(x + dx, y + dy);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isHit() {
		return hit;
	}

	public void markHit() {
		hit = true;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Point))
			return false;
		Point p = (Point) other;
		return (p.x == this.x && p.y == this.y);
	}

}
