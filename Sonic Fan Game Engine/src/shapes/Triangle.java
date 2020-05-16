package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import function_holders.SAT;
import types.Solid;

public class Triangle extends Solid {
	BufferedImage sprite;
	
	public Triangle(double x, double y, double w, double h) {
		super(x, y, w, h);
		
		points = new double[][]{
			{x, y},
			{x, y + h},
			{x + w, y + h}
		};
		
		roundVerts = new int[]{
				0,
				0,
				0
		};
		
		center = new double[]{x, y + h};
		
		try {sprite = ImageIO.read(getClass().getResourceAsStream("/FullTri.png"));}
		catch(Exception e) {e.printStackTrace();}
	}
	
	public void draw(Graphics2D graphics, double ox, double oy) {
		//graphics.setColor(Color.WHITE);
		//graphics.fillPolygon(new int[]{(int)x, (int)x, (int)x + w}, new int[]{(int)y, (int)y + h, (int)y + h}, 3);
		
		graphics.drawImage(sprite, (int)x - (int)ox, (int)y - (int)oy, (int)w, (int)h, null);
	}
}
