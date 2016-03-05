package maze.logic;

import java.util.Random;

public class Dragon extends Elements {
	
	public enum DragonState {
		sleeping, moving, standing, dead
	}
	
	private DragonState state;
	private boolean dragonOnTop;
	private Random seed = new Random(); // e faz sentido a classe dragão ter um atributo do genero random? não sei até que ponto...
	
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
			} else break;
			
			break;
		case standing:
			if(dragonMode == 2)
				aux = seed.nextInt(5);
			else
				aux = seed.nextInt(6);
			
			//Se 0, o dragão continua no estado "Standing"
			if(aux == 0){
				break;
			} else if(aux >= 1 && aux <= 4){	//Se 1..4, o dragão muda de estado para "Moving"
				this.state = DragonState.moving;
			} else {	//Se 5, o dragão muda de estado para "Sleeping"
				this.state = DragonState.sleeping;
				this.setName('d');
				break;
			}

		case moving:
			int movement = seed.nextInt(4) +1 ;
			// 1 - east movement
			// 2 - north movement
			// 3 - west movement
			// 4 - south movement
			switch (movement) {
			case 1:
				c.setX(c.getX() - 1);
				break;
			case 2:
				c.setY(c.getY() - 1);
				break;
			case 3:
				c.setX(c.getX() + 1);
				break;
			case 4:
				c.setY(c.getY() + 1);
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
	}

	public DragonState getDragonState(){
		return this.state;
	}
	
	public boolean getDragonOnTop()
	{
		return this.dragonOnTop;
	}
}
