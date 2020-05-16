package shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import types.Solid;

public class Rectangle extends Solid {
	public Rectangle(double x, double y, int w, int h) {
		super(x, y, w, h);
		
		points = new double[][]{
			{x, y},
			{x + w, y},
			{x + w, y + h},
			{x, y + h}
		};
		
		roundVerts = new int[]{
				0,
				0,
				0,
				0
		};
		
		center = new double[]{x + w / 2, y + h / 2};
		
		//axis = new double[][]{{1, 0}, {0, 1}};
	}
	
	public void draw(Graphics2D graphics, double ox, double oy) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect((int)x - (int)ox, (int)y - (int)oy, (int)w, (int)h);
	}
}
