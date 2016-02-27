package maze.logic;

import java.util.Random;

public class Dragon extends Elements {
	
	public enum DragonState {
		sleeping, moving, standing, dead
	}
	
	private DragonState state;
	private boolean dragonOnTop;
	private Random seed = new Random(); // e faz sentido a classe dragão ter um atributo do genero random? não sei até que ponto...
	
	public Dragon(int x, int y, DragonState s) {
		super(x, y, 'D');
		dragonOnTop = false;
		state  = s;
	}
	
	//zé carlos, vê o que achas da estruturação desta função
	//pergunta: não seria melhor passar como argumento um objeto maze e fazer o update do dragao aqui? (tal como em hero)
	public void updateDragon(){
		int aux;
		switch (this.state){
		case dead:
			break;
		case sleeping:
			//if 0 then it continues sleeping. If 1 the state change to standing.
			aux = seed.nextInt(2);
			if (aux == 0) {
				break;
			} else {
				this.state = DragonState.standing;
				this.setName('D');
				break;
			}
		case standing:
			//if 0 then it continues sleeping. If aux equals 1 the state stays the same. 
			aux = seed.nextInt(6);
			if (aux == 0) {
				this.state = DragonState.sleeping;
				this.setName('d');
				break;
			} else  if (aux == 1 || aux == 2){
				break;
			} else this.state = DragonState.moving; 
			// if aux is 3 or more, the program jumps to the case moving because there is no break;
			// temos que a probabilidade de voltar a dormir é 1/6, de ficar parado 2/6 e de se mover 3/6, parece-te bem?
		case moving:
			//acho que era melhor o programa verificar as posições de movimento livres e mover-se conforme
			int movement = seed.nextInt(4) +1 ;
			// 1 - east movement
			// 2 - north movement
			// 3 - west movement
			// 4 - south movement
			switch (movement) {
			case 1:
				setX(getX() - 1);
				break;
			case 2:
				setY(getY() - 1);
				break;
			case 3:
				setX(getX() + 1);
				break;
			case 4:
				setY(getY() + 1);
				break;
			}
			this.state = DragonState.standing;
			break;
		default:
			break;
			
		}
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
