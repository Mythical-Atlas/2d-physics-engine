package function_holders;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.Camera;
import main.Loader;
import types.Animation;

public class Artist {
	public static void drawFrame(Graphics2D graphics, Animation anim, double x, double y, double cx, double cy, int xScale, int yScale, double angle) {
		AffineTransform backup = graphics.getTransform();
	    AffineTransform a = AffineTransform.getRotateInstance(angle, cx - Camera.ox, cy - Camera.oy);
	    
	    graphics.setTransform(a);
	    
    	anim.draw(graphics, x - Camera.ox, y - Camera.oy, xScale, yScale);
		
		graphics.setTransform(backup);
	}
}
