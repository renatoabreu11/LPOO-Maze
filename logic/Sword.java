package maze.logic;

public class Sword extends Elements
{
	private boolean isVisible;
	
	public Sword(Coordinates c)
	{
		super(c, 'E');
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