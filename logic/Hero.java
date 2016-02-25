package maze.logic;

public class Hero extends Elements {
	private boolean wieldingSword;
	private boolean isDead;

	public Hero(int x, int y) {
		super(x, y, 'H');
		wieldingSword = false;
		isDead = false;
	}

	public int inputHandler(String direction) // VER 'Y' E 'X' MANHOSOS
	{
		if (direction.equals("W") || direction.equals("w")) {
			setY(getY() - 1);
			return 1;
		} else if (direction.equals("S") || direction.equals("s")) {
			setY(getY() + 1);
			return 1;
		} else if (direction.equals("A") || direction.equals("a")) {
			setX(getX() - 1);
			return 1;
		} else if (direction.equals("D") || direction.equals("d")) {
			setX(getX() + 1);
			return 1;
		} else
			return -1;
	}
	
	public boolean getWieldingSword()
	{
		return this.wieldingSword;
	}
	
	public void setWieldingSword()
	{
		this.wieldingSword = true;
	}
	
	public void setIsDead()
	{
		this.isDead = true;
	}
}
