package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import states.Test;

@SuppressWarnings("serial")
public class Loader extends JPanel implements MouseListener, KeyListener, Runnable {
	private Thread thread;
	
	public static boolean fullscreen = false;
	public static boolean gravity = true;
	public static boolean sprites = false;
	
	public static final int FPS = 60;
	public static final int SCALE = 1;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	private static JFrame frame;
	
	public static int realWidth = WIDTH;
	public static int realHeight = HEIGHT;
	private static int xScreen;
	private static int yScreen;
	private static int screenWidth;
	private static int screenHeight;
	
	public static void main(String[] args) {
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		
		screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		xScreen = center.x - (WIDTH * SCALE /*+ Debug.WIDTH*/) / 2;
		yScreen = center.y - HEIGHT * SCALE / 2;
		
		frame = new JFrame("2D Physics Engine");
		
		//frame.setLayout(new BorderLayout());
		
		//frame.add(new Loader(), BorderLayout.LINE_END);
		//frame.add(new Debug(), BorderLayout.LINE_START);
		
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//frame.setUndecorated(true);
		//frame.setBounds(0, 0, screenWidth, screenHeight);
		frame.setBounds(xScreen, yScreen, WIDTH * SCALE/* + Debug.WIDTH*/, HEIGHT * SCALE);
		frame.setContentPane(new Loader());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	public Loader() {
		//setLayout(new BorderLayout());
		//add(new Debug(), BorderLayout.LINE_START);
		
		setPreferredSize(new Dimension(WIDTH * SCALE/* + Debug.WIDTH*/, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		
		addKeyListener(this);
		addMouseListener(this);
		
		thread = new Thread(this);
		thread.start();
	}

	@SuppressWarnings("static-access")
	public void run() {
		BufferedImage image = new BufferedImage(realWidth, realHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D)image.getGraphics();
		
		Test.init();
		
		//setState(0);
		
		while(true) {
			long start = System.nanoTime();
			
			Test.update();
			
			Test.draw(graphics);
			
			//Debug.update();
			
			Graphics graphicsTemp = getGraphics();
			
			graphicsTemp.drawImage(image, 0 /*+ Debug.WIDTH*/, 0, realWidth, realHeight, null);
			graphicsTemp.dispose();
			
			long wait = 1000 / FPS - (System.nanoTime() - start) / 1000000;
			if(wait <= 0) {wait = 0;}
			
			try {thread.sleep(wait);}
			catch(Exception e) {e.printStackTrace();}
		}
	}

	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {Test.keyPressed(key.getKeyCode());}
	public void keyReleased(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE) {System.exit(0);}
		
		if(key.getKeyCode() == KeyEvent.VK_F1) {gravity = !gravity;}
		if(key.getKeyCode() == KeyEvent.VK_F2) {sprites = !sprites;}
		
		/*if(key.getKeyCode() == KeyEvent.VK_F11 && false) {
			frame.dispose();
			
			frame = new JFrame("Rope Swing Game");
			frame.setContentPane(new Loader());
			
			if(!fullscreen) {
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				frame.setUndecorated(true);
				frame.setBounds(0, 0, screenWidth, screenHeight);
				setPreferredSize(new Dimension(screenWidth, screenHeight));
				
				realWidth = screenWidth;
				realHeight = screenHeight;
			}
			else {
				frame.setBounds(xScreen, yScreen, WIDTH * SCALE, HEIGHT * SCALE);
				setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				realWidth = WIDTH;
				realHeight = HEIGHT;
			}
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.pack();
			frame.pack();
			frame.setVisible(true);
			
			fullscreen = !fullscreen;
		}*/
		
		Test.keyReleased(key.getKeyCode());
	}
	
	public void mouseClicked(MouseEvent mouse) {}
	public void mouseEntered(MouseEvent mouse) {}
	public void mouseExited(MouseEvent mouse) {}
	public void mousePressed(MouseEvent mouse) {}
	public void mouseReleased(MouseEvent mouse) {}
}