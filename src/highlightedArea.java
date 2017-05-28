
public class HighlightedArea {

	public Point[] highlightedArea = new Point[main.BOARD_UNITS];

	public HighlightedArea() {
	}

	public boolean rotateShip() {
		Point[] original = highlightedArea.clone();
		for (int i = 1; i < highlightedArea.length; i++) {
			if (highlightedArea[i] != null)
				highlightedArea[i] = highlightedArea[i].rotateFromPoint(highlightedArea[0]);
		}
		for (Point p : highlightedArea) {
			if (!p.canMovePoint(0, 0)) {
				highlightedArea = original;
				return false;
			}
		}
		return true;
	}

	public boolean moveArea(int dx, int dy) {
		for (Point p : highlightedArea) {
			if (p != null && !p.canMovePoint(dx, dy))
				return false;
		}
		for (Point p : highlightedArea) {
			if (p != null)
				p.movePoint(dx, dy);
		}
		return true;
	}

	public void newArea(int size) {
		highlightedArea = new Point[size];
		for (int i = 0; i < size; i++) {
			highlightedArea[i] = new Point(0, i);
		}
	}

	public Point getTopLeft() {
		Point topLeft = highlightedArea[0];
		for (Point p : highlightedArea) {
			if (p != null && (p.getX() < topLeft.getX() || p.getY() < topLeft.getY())) {
				topLeft = p;
			}
		}
		return topLeft;
	}

	public boolean isPoint() {
		if (highlightedArea[1] == null) {
			return true;
		}
		return false;
	}

}
