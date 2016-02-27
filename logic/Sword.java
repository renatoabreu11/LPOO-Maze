package maze.logic;

public class Sword extends Elements
{
	private boolean isVisible;
	
	public Sword(int x, int y)
	{
		super(x, y, 'E');
		isVisible = true;
	}
	
	public void setIsVisible(boolean b) {
		this.isVisible = b;
		
		if(this.isVisible){
			this.setName('E');
		} else this.setName(' ');
	}
	
	public boolean getIsVisible() {
		return this.isVisible;
	}
}
