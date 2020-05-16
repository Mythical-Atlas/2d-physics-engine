package main;

import objects.Player;

public class Camera {
	public static double ox, oy; // offsets -> used for drawing objects relative to screen
	public static int width, height;
	
	public Camera(int roomWidth, int roomHeight) {
		width = roomWidth;
		height = roomHeight;
	}
	
	public static void update(Player player) {
		ox = player.x - Loader.realWidth / 2;
		oy = player.y - Loader.realHeight / 2;
		
		//if(player.x - Loader.realWidth / 2 < )
		
		if(ox < 0) {ox = 0;}
		if(ox > width - Loader.realWidth) {ox = width - Loader.realWidth;}
		
		if(oy < 0) {oy = 0;}
		if(oy > height - Loader.realHeight) {oy = height - Loader.realHeight;}
		
		//if(player.y <= Loader.realHeight / 2) {oy = 0;}
		//else if(player.y >= height - Loader.realHeight / 2) {oy = height - Loader.realHeight / 2;}
		//else {oy = player.y + Loader.realHeight / 2;}
	}
}
