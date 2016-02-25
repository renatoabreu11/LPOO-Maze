package maze.logic;

import java.util.Random;

public class Dragon extends Elements {
	private boolean isDead;
	private boolean dragonOnTop;
	private Random seed = new Random();		//RENATO, SO PARA NAO ESQUECER DE TE DIZER: A INICIALIZACAO DA SEED TEM DE SER INICIALIZADA APENAS UMA VEZ, POIS GASTA BASTANTE PODER DE PROCESSAMENTO :P

	public Dragon(int x, int y) {
		super(x, y, 'D');
		isDead = false;
		dragonOnTop = false;
	}
	
	public void updateDragon(){
		int movement = seed.nextInt(5);
		
		// 0 - isn't moving
		// 1 - east movement
		// 2 - north movement
		// 3 - west movement
		// 4 - south movement
		switch (movement) {
		case 0:
			break;
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
	}
	
	public void setDragonOnTop(boolean flag){
		this.dragonOnTop = flag;

		if (this.dragonOnTop) {
			setName('F');
		} else
			setName('D');
	}

	public void setIsDead() {
		this.isDead = true;
		this.setName(' ');
	}

	public boolean getIsDead(){
		return this.isDead;
	}
}
