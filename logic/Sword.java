package maze.logic;

public class Sword extends Elements
{
	private boolean isWielded;
	private boolean dragonOnTop;
	
	public Sword(int x, int y)
	{
		super(x, y, 'E');
		isWielded = false;
		dragonOnTop = false;
	}
}
