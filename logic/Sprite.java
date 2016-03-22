package maze.logic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite {

	public ArrayList<BufferedImage> upSprites = new ArrayList<BufferedImage>();
	public ArrayList<BufferedImage> downSprites = new ArrayList<BufferedImage>();
	public ArrayList<BufferedImage> leftSprites = new ArrayList<BufferedImage>();
	public ArrayList<BufferedImage> rightSprites = new ArrayList<BufferedImage>();
	public int alternate;
	public boolean facingUp, facingDown, facingLeft, facingRight;
}
