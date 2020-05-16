package types;

public class Solid {
	public double x, y;
	public double w, h;
	
	public double[][] points;
	
	public int[] roundVerts;
	
	public double[] center;
	
	public double radius;
	
	public Solid(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public Solid(double x, double y, double w, double h, int[] roundVerts) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.roundVerts = roundVerts;
		
		//round = false;
	}
	
	/*public Solid(double x, double y, int w, int h, boolean round) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.round = round;
	}*/
}