package maze.logic;

public class Sword extends Elements
{
	private boolean isVisible;
	
	/**
	 * Sword constructor
	 */
	public Sword(Coordinates c)
	{
		super(c, 'E');
		isVisible = true;
	}
	
	/**
	 * Sets the sword visibility to the given flag
	 */
	public void setIsVisible(boolean flag) {
		this.isVisible = flag;
		
		if(this.isVisible){
			this.setName('E');
		} else this.setName(' ');
	}
	
	/**
	 * Returns isVisible (checks if sword is visible).
	 */
	public boolean getIsVisible() {
		return this.isVisible;
	}
}