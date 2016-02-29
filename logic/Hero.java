package maze.logic;

public class Hero extends Elements {
	private boolean wieldingSword;
	private boolean isDead;

	public Hero(int x, int y) {
		super(x, y, 'H');
		wieldingSword = false;
		isDead = false;
	}

	public Coordinates UpdateMovement(String direction)
	{
		Coordinates c = this.getCoordinates();
		if (direction.equals("W") || direction.equals("w")) {
			c.setY(c.getY() - 1);
		} else if (direction.equals("S") || direction.equals("s")) {
			c.setY(c.getY() + 1);
		} else if (direction.equals("A") || direction.equals("a")) {
			c.setX(c.getX() - 1);
		} else if (direction.equals("D") || direction.equals("d")) {
			c.setX(c.getX() + 1);
		} else
			return c;
		
		return c;
	}
	
	public boolean getWieldingSword()
	{
		return this.wieldingSword;
	}
	
	public boolean getIsDead()
	{
		return this.isDead;
	}
	
	public void setWieldingSword()
	{
		this.wieldingSword = true;
		this.setName('A');
	}
	
	public void setIsDead()
	{
		this.isDead = true;
		this.setName(' ');
	}
}
