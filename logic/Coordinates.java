package maze.logic;

public class Coordinates {
	private int x;
	private int y;
	
	/**
	 * Coordinates constructor 1
	 */
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Coordinates constructor 2
	 */
	public Coordinates(Coordinates coord){
		this(coord.x, coord.y);
	}
	
	/**
	 * Returns the 'x' coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the 'y' coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sets the 'x' coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the 'y' coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Sets the 'x' and 'y' coordinates
	 */
	public void setCoordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the coordinates
	 */
	public Coordinates getCoordinates(){
		Coordinates c = new Coordinates(this.x, this.y);
		return c;
	}
	
	/**
	 * Equals function override
	 */
	 @Override
	public boolean equals(Object obj){
		if (!(obj instanceof Coordinates))
            return false;
        if (obj == this)
            return true;

        Coordinates rhs = (Coordinates) obj;
        
        return (rhs.x == this.x && rhs.y == this.y);
    }	
}
