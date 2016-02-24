package maze.logic;

public class Elements {
	private int x, y;
	private char name;

	public Elements(int x, int y, char name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public char getName() {
		return name;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setName(char name) {
		this.name = name;
	}
}
