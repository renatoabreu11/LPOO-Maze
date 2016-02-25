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
	
	public void setIsWielded()
	{
		this.isWielded = true;
		this.setName(' ');
	}
	
	public void setDragonOnTop(boolean dragonOnTop)
	{
		this.dragonOnTop = dragonOnTop;
		
		if(this.dragonOnTop)
			this.setName(' ');
		else
			this.setName('E');
	}
}
