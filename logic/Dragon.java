package maze.logic;

public class Dragon extends Elements
{
	private boolean isDead;
	private boolean dragonOnTop;
	
	public Dragon(int x, int y)
	{
		super(x, y, 'D');
		isDead = false;
	}
}
