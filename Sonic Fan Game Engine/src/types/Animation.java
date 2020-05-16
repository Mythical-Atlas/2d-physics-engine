package types;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Animation {
	private int timer;
	private int frame;
	private int[] durations;
	private int repeatFrame;
	public boolean finished; // only for first cycle
	private BufferedImage[] frames;
	
	public Animation() {}
	
	public Animation(BufferedImage[] frames, int[] durations, int repeatFrame) {
		this.frames = frames;
		this.durations = durations;
		this.repeatFrame = repeatFrame;
		
		if(repeatFrame == -1) {this.repeatFrame = frames.length - 1;}
	}
	
	public void reset() {
		timer = 0;
		frame = 0;
		finished = false;
	}
	
	public void update() {
		timer++;
		if(timer >= durations[frame]) {
			timer = 0;
			frame++;
			
			if(frame >= frames.length) {
				frame = repeatFrame;
				
				finished = true;
			}
		}
		
		/*System.out.println("timer = " + timer);
		System.out.println("frame = " + frame);
		System.out.println("length = " + frames.length);*/
	}
	
	public BufferedImage[] getAnimation(String path, String name) {
		int length = 0;
		
		while(true) {
			try {ImageIO.read(getClass().getClassLoader().getResourceAsStream(path + "\\" + name + length + ".png"));}
			catch(Exception e) {break;}
			
			length++;
		}
		
		System.out.println(length);
		
		BufferedImage[] images = new BufferedImage[length];
		
		try {for(int i = 0; i < length; i++) {images[i] = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path + "\\" + name + i + ".png"));}}
		catch(Exception e) {e.printStackTrace();}
		
		return(images);
	}
	
	public void draw(Graphics2D graphics, int x, int y) {graphics.drawImage (frames[frame], x, y, null);}
	public void draw(Graphics2D graphics, double x, double y) {graphics.drawImage(frames[frame], (int)x, (int)y, null);}
	
	public void draw(Graphics2D graphics, int x, int y, int xScale, int yScale) {graphics.drawImage(frames[frame], x, y, frames[frame].getWidth() * xScale, frames[frame].getHeight() * yScale, null);}
	public void draw(Graphics2D graphics, double x, double y, int xScale, int yScale) {graphics.drawImage(frames[frame], (int)x, (int)y, frames[frame].getWidth() * xScale, frames[frame].getHeight() * yScale, null);}
}