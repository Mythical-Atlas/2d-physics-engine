package states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JFrame;

import compounds.Slope;
import main.Camera;
import main.Loader;
import objects.Player;
import shapes.Circle;
import shapes.QuarterCircle;
import shapes.QuarterSlope;
import shapes.Rectangle;
import shapes.Triangle;
import types.Solid;

public class Test {
	public static Player player = new Player(500, 700);
	
	public static Slope slope = new Slope(16, 341, 16, 16);
	public static Slope slope2 = new Slope(1524, 1364, 16, 16);
	
	public static Rectangle rect = new Rectangle(0, 0, 16, 1000);
	public static Rectangle rect2 = new Rectangle(16, 964, 1124, 16);
	public static Rectangle rect3 = new Rectangle(1524, 1364 + 624, 2000, 16);
	
	public static Circle circle = new Circle(500, 600, 64, 64);
	
	public static QuarterCircle qc = new QuarterCircle(1124, 964, 400, 400);
	
	public static void init() {
		new Camera(5000, 3000);
	}
	
	public static void update() {
		Solid[] solids = new Solid[]{rect, rect2, circle, qc, rect3};
		solids = mergeLists(solids, slope.solids);
		solids = mergeLists(solids, slope2.solids);
		
		player.update(solids);
		
		Camera.update(player);
	}
	
	public static void draw(Graphics2D graphics) {
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, Loader.realWidth, Loader.realHeight);
		
		slope.draw(graphics);
		slope2.draw(graphics);
		
		rect.draw(graphics, Camera.ox, Camera.oy);
		rect2.draw(graphics, Camera.ox, Camera.oy);
		rect3.draw(graphics, Camera.ox, Camera.oy);
		
		qc.draw(graphics, Camera.ox, Camera.oy);
		circle.draw(graphics, Camera.ox, Camera.oy);
			
		player.draw(graphics, Camera.ox, Camera.oy);
		
		//for(int i = 0; i < tiles.length; i++) {tiles[i].draw(Player.x, Player.y, Player.w, Player.h, graphics, cam.x, cam.y);}
	}
	
	public static Solid[] mergeLists(Solid[] list1, Solid[] list2) {
		Solid[] newList = new Solid[list1.length + list2.length];
		
		for(int i1 = 0; i1 < list1.length; i1++) {newList[i1] = list1[i1];}
		for(int i2 = 0; i2 < list2.length; i2++) {newList[i2 + list1.length] = list2[i2];}
		
		return(newList);
	}
	
	public static void keyPressed(int key) {Player.keyPressed(key);}
	public static void keyReleased(int key) {Player.keyReleased(key);}
}