package maze.logic;

import java.util.Random;

public class Dragon extends Elements {
	
	public enum DragonState {
		sleeping, moving, standing, dead
	}
	
	private DragonState state;
	private boolean dragonOnTop;
	private String movedTo;
	Random seed = new Random();
	
	public Dragon(Coordinates c, DragonState s) {
		super(c, 'D');
		dragonOnTop = false;
		state  = s;
	}
	
	public Coordinates updateMovement(int dragonMode){
		int aux;
		Coordinates c = this.getCoordinates();	
		
		if (dragonMode == 1)
			return c;
		
		switch (this.state){
		case dead:
			break;
		case sleeping:
			aux = seed.nextInt(2);
			if (aux == 1) {
				this.state = DragonState.standing;
				this.setName('D');
				movedTo = "Standing";
			} else 
				movedTo = "Sleeping";
			break;
			
		case standing:
			if(dragonMode == 2)
				aux = seed.nextInt(5);
			else
				aux = seed.nextInt(6);
			
			if(aux == 0){
				break;
			} else if(aux >= 1 && aux <= 4){
				this.state = DragonState.moving;
			} else {
				this.state = DragonState.sleeping;
				this.setName('d');
				movedTo = "Sleeping";
				break;
			}

		case moving:
			int movement = seed.nextInt(4) +1 ;
			
			switch (movement) {
			case 1:
				c.setX(c.getX() - 1);
				movedTo = "Left";
				break;
			case 2:
				c.setY(c.getY() - 1);
				movedTo = "Up";
				break;
			case 3:
				c.setX(c.getX() + 1);
				movedTo = "Right";
				break;
			case 4:
				c.setY(c.getY() + 1);
				movedTo = "Down";
				break;
			}
			this.state = DragonState.standing;
			break;
		default:
			break;
		}
		return c;
	}
	
	public void setDragonOnTop(boolean flag){
		this.dragonOnTop = flag;

		if (this.dragonOnTop) {
			setName('F');
		} else
			setName('D');
	}

	public void setDragonState(DragonState s) {
		this.state = s;
		
		if (s.equals(DragonState.dead))
			this.setName(' ');
		else if(s.equals(DragonState.sleeping))
			this.setName('d');
	}

	public DragonState getDragonState(){
		return this.state;
	}
	
	public boolean getDragonOnTop()
	{
		return this.dragonOnTop;
	}
	
	public String getMovedTo()
	{
		return this.movedTo;
	}
}
