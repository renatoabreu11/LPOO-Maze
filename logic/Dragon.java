package maze.logic;

import java.util.Random;

public class Dragon extends Elements {
	private boolean isDead;
	private boolean dragonOnTop;

	public Dragon(int x, int y) {
		super(x, y, 'D');
		isDead = false;
		dragonOnTop = false;
	}
	
	public void updateDragon(){
		Random seed = new Random();
		int movement = seed.nextInt(5);
		
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
		
		if(this.dragonOnTop == true){
			setName('F');
		}
		else setName('D');
	}
	
	public void setIsDead(){
		this.isDead = true;
	}
	
	public boolean getIsDead(){
		return this.isDead;
	}

}
