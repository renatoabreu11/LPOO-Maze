package maze.logic;

public class Elements {
	private Coordinates coord;
	private char name;

	/**
	 * Elements constructor
	 */
	public Elements(Coordinates c, char name) {
		this.coord = c;
		this.name = name;
	}

	/**
	 * Returns the name of the element.
	 */
	public char getName() {
		return name;
	}

	/**
	 * Sets the name of the element.
	 */
	public void setName(char name) {
		this.name = name;
	}
	
	/**
	 * Returns the coordinates of the element.
	 */
	public Coordinates getCoordinates(){
		
		return this.coord.getCoordinates();
	}
	
	/**
	 * Sets the coordinates of the element.
	 */
	public void setCoordinates(Coordinates c){
		this.coord = c;
	}
	
}