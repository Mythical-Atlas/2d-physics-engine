package compounds;

import java.awt.Graphics2D;

import main.Camera;
import shapes.Triangle;
import types.Solid;

public class Slope {
	public double x, y, w, h;
	
	public Triangle tri1;
	public Triangle tri2;
	public Triangle tri3;
	public Triangle tri4;
	
	public Triangle tri5;
	public Triangle tri6;
	public Triangle tri7;
	
	public Solid[] solids;
	
	public Slope(double x, double y, double w, double h) {
		tri1 = new Triangle(x + w * 0, 	y + h * 0, 	w * 1, h * 8);
		tri2 = new Triangle(x + w * 1,  y + h * 8,  w * 2, h * 8);
		tri3 = new Triangle(x + w * 3,  y + h * 16, w * 4, h * 8);
		tri4 = new Triangle(x + w * 7,  y + h * 24, w * 8, h * 8);
		tri5 = new Triangle(x + w * 15, y + h * 32, w * 8, h * 4);
		tri6 = new Triangle(x + w * 23, y + h * 36, w * 8, h * 2);
		tri7 = new Triangle(x + w * 31, y + h * 38, w * 8, h * 1);
		
		solids = new Solid[]{tri1, tri2, tri3, tri4, tri5, tri6, tri7};
	}
	
	public void draw(Graphics2D graphics) {
		tri1.draw(graphics, Camera.ox, Camera.oy);
		tri2.draw(graphics, Camera.ox, Camera.oy);
		tri3.draw(graphics, Camera.ox, Camera.oy);
		tri4.draw(graphics, Camera.ox, Camera.oy);
		tri5.draw(graphics, Camera.ox, Camera.oy);
		tri6.draw(graphics, Camera.ox, Camera.oy);
		tri7.draw(graphics, Camera.ox, Camera.oy);
	}
}
