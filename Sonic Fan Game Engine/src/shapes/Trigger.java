package shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import function_holders.SAT;
import main.Camera;
import objects.Player;
import types.Solid;

public class Trigger {
	public double x, y;
	public int w, h;
	
	public Rectangle hitbox;
	
	public Trigger(double x, double y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		hitbox = new Rectangle(x, y, w, h);
	}
	
	//public boolean checkPlayerCollision() {
		/*if(SAT.checkCollision(Player.solid, hitbox)) {
			
		}*/
	//}
	
	public void draw(Graphics2D graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect((int)x - (int)Camera.ox, (int)y - (int)Camera.oy, (int)w, (int)h);
	}
}
