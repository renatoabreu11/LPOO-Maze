package maze.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class SpriteSheetLoader {
	private ArrayList<BufferedImage> sprites;
	private BufferedImage image;
	
	public SpriteSheetLoader(String filename, int verticalSprites, int horizontalSprites, int widthIncrement, int heightIncrement) throws IOException{
		sprites = new ArrayList<BufferedImage>();
		try {
			image = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < verticalSprites; i++) {
	         for(int j = 0; j < horizontalSprites; j++) {
	            sprites.add(image.getSubimage(j*widthIncrement, i*heightIncrement , widthIncrement, heightIncrement));
	         }
	      }
	}
	
	public ArrayList<BufferedImage> getSprites() {
		return sprites;
	}
	
	public void setSprites(ArrayList<BufferedImage> sprites) {
		this.sprites = sprites;
	}
}
