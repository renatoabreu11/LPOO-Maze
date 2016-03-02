package maze.logic;

public class Coordinates {
	private int x;
	private int y;
	
	
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setCoordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Coordinates getCoordinates(){
		Coordinates c = new Coordinates(this.x, this.y);
		return c;
	}
	
	 @Override
	public boolean equals(Object obj){
		if (!(obj instanceof Coordinates))
            return false;
        if (obj == this)
            return true;

        Coordinates rhs = (Coordinates) obj;
        
        return (rhs.x == this.x && rhs.y == this.y);
    }
	
	public Coordinates(Coordinates coord){
		this(coord.x, coord.y);
	}
	
}
