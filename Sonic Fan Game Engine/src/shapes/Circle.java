package shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import types.Solid;

public class Circle extends Solid {
	public Circle(double x, double y, double w, double h) {
		super(x, y, w, h);
		
		points = new double[][]{
			{x, y},
			{x + w, y},
			{x + w, y + h},
			{x, y + h}
		};
		
		roundVerts = new int[]{1, 1, 1, 1};
		radius = w / 2;
		center = new double[]{x + w / 2, y + h / 2};
	}
	
	public void draw(Graphics2D graphics, double ox, double oy) {
		graphics.setColor(Color.WHITE);
		graphics.fillOval((int)x - (int)ox, (int)y - (int)oy, (int)w, (int)h);
	}
}
