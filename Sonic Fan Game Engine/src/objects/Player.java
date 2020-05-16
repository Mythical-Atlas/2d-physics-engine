package objects;

import static java.awt.event.KeyEvent.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;

import function_holders.Artist;
import function_holders.Geometry;
import function_holders.SAT;
import main.Loader;
import shapes.Circle;
import shapes.Rectangle;
import shapes.Triangle;
import types.Animation;
import types.Solid;

public class Player {
	public double x, y, dx, dy, gs;
	
	double gravity;
	
	double[] groundAxis = new double[]{1, 0};
	
	int w = 64;
	int h = 64;
	
	static boolean upKey;
	static boolean downKey;
	static boolean leftKey;
	static boolean rightKey;
	static boolean shift;
	
	double angle = 0;
	
	boolean collide;
	boolean ground;
	
	double[] tempAxis = new double[]{1, 0};
	
	Solid colMask;
	
	Animation[] animations = null;
	
	int direction = 1;
	
	int anim = 0;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		
		try {
			animations = new Animation[3];
			int[] speeds;
			
			speeds= new int[]{6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 12, 6, 6, 6, 12, 8};
			animations[0] = new Animation(new Animation().getAnimation("sonic_sprites", "idle"), speeds, 0);
			
			speeds = new int[]{6, 6, 6, 6, 6, 6, 6, 6};
			animations[1] = new Animation(new Animation().getAnimation("sonic_sprites", "run"), speeds, 0);
			
			speeds = new int[]{3, 3, 3};
			animations[2] = new Animation(new Animation().getAnimation("sonic_sprites", "fall"), speeds, 0);
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	public Solid getHitbox() {
		double[] axis = new double[]{0, 0};
		double[] posDis = Geometry.perProduct(groundAxis);
		posDis[0] *= w * 0.2;
		posDis[1] *= h * 0.2;
		
		return(new Circle(x + posDis[0] + w * 0.1, y + posDis[1] + h * 0.1, w * 0.8, h * 0.8));
	}
	
	public void update(Solid[] solids) {
		ground = false;
		double[] axis = new double[]{0, 0};
		double[] posDis = Geometry.perProduct(groundAxis);
		posDis[0] *= w * 0.2;
		posDis[1] *= h * 0.2;
		
		colMask = new Circle(x + posDis[0] + w * 0.1, y + posDis[1] + h * 0.1, w * 0.8, h * 0.8);
		
		for(int i = 0; i < solids.length; i++) {if(SAT.checkCollision(colMask, solids[i])) {ground = true;}}
		
		double gs = 0;
		
		if(!shift) {
			if(upKey) {dy -= 0.5;}
			if(downKey) {dy += 0.5;}
			
			if(leftKey) {
				gs = -0.5;
				
				direction = -1;
			}
			if(rightKey) {
				gs = 0.5;
				
				direction = 1;
			}
		}
		else {
			if(upKey) {dy -= 1;}
			if(downKey) {dy += 1;}
			
			if(leftKey) {
				gs = -1;
				
				direction = -1;
			}
			if(rightKey) {
				gs = 1;
				
				direction = 1;
			}
		}
		
		dx += gs * groundAxis[0];
		dy += gs * groundAxis[1];
		
		if(leftKey && rightKey || !leftKey && !rightKey) {
			if(ground) {
				double[] tempSpeed = Geometry.project(dx, dy, groundAxis);
				
				dx -= tempSpeed[0];
				dy -= tempSpeed[1];
				
				dx += Geometry.stepTowards(tempSpeed[0], 0, 0.25);
				dy += Geometry.stepTowards(tempSpeed[1], 0, 0.25);
			}
		}
		
		if(Loader.gravity) {dy += 0.5;}
		
		x += dx;
		y += dy;
		
		collide = false;
		axis = new double[]{0, 0};
		colMask = new Circle(x, y, w, h);
		
		for(int i = 0; i < solids.length; i++) {
			if(SAT.checkCollision(colMask, solids[i])) {
				double[] tempVector = SAT.collisionResponse(colMask, solids[i]);
				axis = Geometry.normalize(tempVector[0], tempVector[1]);
				
				x += tempVector[0];
				y += tempVector[1];
				
				collide = true;
				
				if(ground) {
					groundAxis = Geometry.perProduct(axis);
					tempAxis = groundAxis;
				}
				
				angle = Math.atan(Geometry.perProduct(axis)[1] / Geometry.perProduct(axis)[0]);
				if(groundAxis[1] == 1) {angle *= -1;}
				
				double[] newVec = Geometry.project(dx, dy, Geometry.perProduct(axis));
				
				dx = newVec[0];
				dy = newVec[1];
			}
		}
		
		if(!ground) {
			tempAxis = new double[]{Geometry.stepTowards(tempAxis[0], 1, 0.05), Geometry.stepTowards(tempAxis[1], 0, 0.05)};
			
			//groundAxis = new double[]{1, 0};
			groundAxis = tempAxis;
			
			angle = Math.atan(tempAxis[1] / tempAxis[0]);
		}
		
		if(dx >= -0.125 && dx <= 0.125) {dx = 0;}
		if(dy >= -0.125 && dy <= 0.125) {dy = 0;}
	}
	
	public void draw(Graphics2D graphics, double ox, double oy) {
		if(ground) {
			if(dx == 0 && dy == 0) {
				if(anim != 0) {
					anim = 0;
				
					animations[0].reset();
				}
			}
			if(dx != 0 || dy != 0) {
				if(anim != 1) {
					anim = 1;
					
					animations[1].reset();
				}
			}
		}
		
		if(!ground) {
			if(anim != 2) {
				anim = 2;
				
				animations[2].reset();
			}
		}
		
		double[] posDis = Geometry.perProduct(groundAxis);
		posDis[0] *= w * 0.2;
		posDis[1] *= h * 0.2;
		
		graphics.setColor(Color.GREEN);
		graphics.fillOval((int)x - (int)ox + (int)(w * 0.1) + (int)posDis[0], (int)y - (int)oy + (int)(w * 0.1) + (int)posDis[1], (int)(h * 0.8), (int)(h * 0.8));
		
		graphics.setColor(Color.RED);
		graphics.fillOval((int)x - (int)ox, (int)y - (int)oy, w, h);
		
	    if(Loader.sprites) {
	    	if(direction == -1) {Artist.drawFrame(graphics, animations[anim], x - 32, y - 48 + 2 - 16, x + 32, y + 32, 2, 2, angle);}
			if(direction == 1) {Artist.drawFrame(graphics, animations[anim], x + 96, y - 48 + 2 - 16, x + 32, y + 32, -2, 2, angle);}
	    }
		
		animations[anim].update();
	}
	
	public static void keyPressed(int key) {
		switch(key) {
			case(VK_W): upKey = true; break;
			case(VK_S): downKey = true; break;
			case(VK_A): leftKey = true; break;
			case(VK_D): rightKey = true; break;
			case(VK_SHIFT): shift = true; break;
		}
	}
	public static void keyReleased(int key) {
		switch(key) {
			case(VK_W): upKey = false; break;
			case(VK_S): downKey = false; break;
			case(VK_A): leftKey = false; break;
			case(VK_D): rightKey = false; break;
			case(VK_SHIFT): shift = false; break;
		}
	}
}
