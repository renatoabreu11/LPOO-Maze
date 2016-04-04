package maze.logic;

public class Hero extends Elements {
	private boolean wieldingSword;
	private boolean isDead;

	/**
	 * Hero constructor
	 */
	public Hero(Coordinates c) {
		super(c, 'H');
		wieldingSword = false;
		isDead = false;
	}

	/**
	 * Updates the hero movement based on the given direction.
	 */
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
		}
		
		return c;
	}
	
	/**
	 * Returns the wieldingSword variable (checks if the hero is holding the sword)
	 */
	public boolean getWieldingSword()
	{
		return this.wieldingSword;
	}
	
	/**
	 * Checks if hero is dead.
	 */
	public boolean getIsDead()
	{
		return this.isDead;
	}
	
	/**
	 * Set wieldingSword to true (hero grabs the sword).
	 */
	public void setWieldingSword()
	{
		this.wieldingSword = true;
		this.setName('A');
	}
	
	/**
	 * Set isDead to true (hero dies).
	 */
	public void setIsDead()
	{
		this.isDead = true;
		this.setName(' ');
	}
}
