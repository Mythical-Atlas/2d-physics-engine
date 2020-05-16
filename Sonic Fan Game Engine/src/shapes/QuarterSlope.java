package shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import types.Solid;

public class QuarterSlope extends Solid {
	public QuarterSlope(double x, double y, int w, int h) {
		super(x, y, w, h);
		
		points = new double[][]{
			{x, y},
			{x + w, y},
			{x + w, y + h},
			{x, y + h}
		};
		
		roundVerts = new int[]{
				0,
				-1,
				0,
				0
		};
		
		center = new double[]{x, y + h};
		
		radius = w;
		
		//axis = new double[][]{{1, 0}, {0, 1}};
	}
	
	public void draw(Graphics2D graphics) {
		graphics.setColor(Color.WHITE);
		//graphics.fillPolygon(new int[]{(int)x, (int)x, (int)x + w}, new int[]{(int)y, (int)y + h, (int)y + h}, 3);
		//graphics.drawArc((int)x - w, (int)y, w*2, h*2, 0, 90);
	}
}
