
public class Point {

	private boolean hit;
	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		hit = false;
	}

	public void movePoint(int dx, int dy) {
		x = x + dx;
		y = y + dy;
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

}
