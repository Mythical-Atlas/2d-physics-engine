package shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import types.Solid;

public class QuarterCircle extends Solid {
	public QuarterCircle(double x, double y, int w, int h) {
		super(x, y, w, h);
		
		points = new double[][]{
			{x, y},
			{x + w, y},
			{x + w, y + h},
			{x, y + h}
		};
		
		roundVerts = new int[]{
				0,
				1,
				0,
				0
		};
		
		center = new double[]{x, y + h};
		
		radius = w;
		
		//axis = new double[][]{{1, 0}, {0, 1}};
	}
	
	public void draw(Graphics2D graphics, double ox, double oy) {
		graphics.setColor(Color.WHITE);
		graphics.fillPolygon(new int[]{(int)x - (int)ox, (int)x - (int)ox, (int)x + (int)w - (int)ox}, new int[]{(int)y - (int)oy, (int)y + (int)h - (int)oy, (int)y + (int)h - (int)oy}, 3);
		graphics.drawArc((int)x - (int)w - (int)ox, (int)y - (int)oy, (int)w * 2, (int)h * 2, 0, 90);
	}
}
