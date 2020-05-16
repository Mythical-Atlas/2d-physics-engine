package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import states.Test;

public class Debug extends JPanel {
	public static int WIDTH = 240;
	public static JLabel x = new JLabel();
	public static JLabel y = new JLabel();
	public static JLabel a = new JLabel();
	public static JLabel b = new JLabel();
	public static JLabel c = new JLabel();
	public static JLabel d = new JLabel();
	public static JLabel e = new JLabel();
	public static JLabel f = new JLabel();
	public static JLabel g = new JLabel();
	public static JLabel h = new JLabel();
	
	public Debug() {
		setPreferredSize(new Dimension(WIDTH, Loader.HEIGHT * Loader.SCALE));
		//setFocusable(true);
		//requestFocus();
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//x.
		
		add(x);
		add(y);
		add(a);
		add(b);
		add(c);
		add(d);
		add(e);
		add(f);
		add(g);
		add(h);
	}
	
	public static void update() {
		//x.setText("x = " + Test.player.x);
		//y.setText("y = " + Test.player.y);
		
		//System.out.println("x = " + Test.player.x);
		//System.out.println("y = " + Test.player.y);
	}
}
