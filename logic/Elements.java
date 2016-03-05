package maze.logic;

public class Elements {
	private Coordinates coord;
	private char name;

	public Elements(Coordinates c, char name) {
		this.coord = c;
		this.name = name;
	}

	public char getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}
	
	public Coordinates getCoordinates(){
		
		return this.coord.getCoordinates();
	}
	
	public void setCoordinates(Coordinates c){
		this.coord = c;
	}
	
}
