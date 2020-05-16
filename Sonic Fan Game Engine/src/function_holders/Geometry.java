package function_holders;

public class Geometry {
	public static double stepTowards(double current, double target, double interval) {
		double out = current;
		
		if(current < target) {
			if(current + interval >= target) {out = target;}
			else {out += interval;}
		}
		if(current > target) {
			if(current - interval <= target) {out = target;}
			else {out -= interval;}
		}
		
		return(out);
	}
	
	public static double[] rotate(double[] point, double[] axis) { // kinda trash rn, should be a vector not an angle
		double dist = Math.sqrt(point[0] * point[0] + point[1] * point[1]);
		
		double x = dist * axis[0];
		double y = dist * axis[1];
		
		return(new double[]{x, y});
	}
	
	public static double[][] minMax(double[][] points, double[] axis) {
		double[][] attempts = new double[points.length][2];
		
		double maxDist = 0;
		double minDist = 0;
		int maxIndex = -1;
		int minIndex = -1;
		
		for(int i = 0; i < attempts.length; i++) {
			attempts[i] = project(points[i][0], points[i][1], axis);
			
			double dist = rotate(attempts[i], new double[]{1, 0})[0];
			if(attempts[i][0] < 0) {dist *= -1;}
			
			if(dist < minDist || minIndex == -1) {
				minDist = dist;
				minIndex = i;
			}
			
			if(dist > maxDist || maxIndex == -1) {
				maxDist = dist;
				maxIndex = i;
			}
		}
		
		double[] min = points[minIndex];
		double[] max = points[maxIndex];
		
		return(new double[][]{min, max});
	}
	
	public static double[] perProduct(double[] oldVec) {
		return(new double[]{-oldVec[1], oldVec[0]});
	}
	
	public static double[] normalize(double x, double y) {
		double length = Math.sqrt(x * x + y * y);
		
		x /= length;
		y /= length;
		
		return(new double[]{x, y});
	}
	
	public static double dotProduct(double x1, double y1, double x2, double y2) {return(x1 * x2 + y1 * y2);}
	
	public static double[] project(double x1, double y1, double[] unit) {
		double x2 = unit[0];
		double y2 = unit[1];
		double dp = dotProduct(x1, y1, x2, y2);
		
		return(new double[]{dp * x2, dp * y2});
	}
	
	public static double[] getAltitude(double[][] e, double[] p, double distance) {
		double[] axis = normalize(e[1][0] - e[0][0], e[1][1] - e[0][1]);
		
		axis = new double[]{-axis[1], axis[0]};
		
		double[] p2 = new double[]{p[0] + axis[0] * distance, p[1] + axis[1] * distance};
		
		//System.out.println("p2 = (" + p2[0] + ", " + p2[1] + ")");
		
		return(p2);
	}
	
	public static double perpendicularDistance(double[][] e, double[] p) {
		double[] a = e[0];
		double[] b = e[1];
		double[] c = p;
		
		double distance = Math.abs((b[1] - a[1]) * c[0] - (b[0] - a[0]) * c[1] + b[0] * a[1] - b[1] * a[0]) / Math.sqrt(Math.pow(b[1] - a[1], 2) + Math.pow(b[0] - a[0], 2));
		
		// check if point is perpendicular to line
		
		double[] p2 = getAltitude(e, p, distance);
		double[] p3 = getAltitude(e, p, -distance);
		
		double dist1 = Math.sqrt(Math.pow(p2[1] - a[1], 2) + Math.pow(p2[0] - a[0], 2));
		double dist2 = Math.sqrt(Math.pow(p2[1] - b[1], 2) + Math.pow(p2[0] - b[0], 2));
		
		double dist3 = Math.sqrt(Math.pow(a[1] - b[1], 2) + Math.pow(a[0] - b[0], 2));
		
		double dist4 = Math.sqrt(Math.pow(p3[1] - a[1], 2) + Math.pow(p3[0] - a[0], 2));
		double dist5 = Math.sqrt(Math.pow(p3[1] - b[1], 2) + Math.pow(p3[0] - b[0], 2));
		
		//System.out.println("distance = " + distance);
		//System.out.println("dist1 = " + dist1);
		//System.out.println("dist2 = " + dist2);
		
		if(dist3 >= dist1 + dist2 || dist3 >= dist4 + dist5) {
			return(distance);
		}
		
		//double d1 =  Math.sqrt(Math.pow(c[1] - p2[1], 2) + Math.pow(c[0] - p2[0], 2));
		
		//if(distance <= d1) {return(distance);}
		else {return(-1);}
	}
}
