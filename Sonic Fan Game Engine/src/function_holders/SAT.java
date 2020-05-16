package function_holders;

import shapes.Circle;
import shapes.Rectangle;
import types.Solid;

public class SAT {
	public static double[][] getAxis(Solid a, Solid b) {
		double[][] aAxis = new double[a.points.length][2];
		double[][] bAxis = new double[b.points.length][2];
		
		for(int i = 0; i < aAxis.length; i++) {
			double[][] edge = getEdge(a.points, i);
			double[] temp = Geometry.normalize(edge[0][0] - edge[1][0], edge[0][1] - edge[1][1]);
			
			aAxis[i] = new double[]{-temp[1], temp[0]};
		}
		for(int i = 0; i < bAxis.length; i++) {
			double[][] edge = getEdge(b.points, i);
			double[] temp = Geometry.normalize(edge[0][0] - edge[1][0], edge[0][1] - edge[1][1]);
			
			bAxis[i] = new double[]{-temp[1], temp[0]};
		}
		
		double[][] axis = new double[aAxis.length + bAxis.length][2];
		for(int ai = 0; ai < aAxis.length; ai++) {axis[ai] = aAxis[ai];}
		for(int bi = 0; bi < bAxis.length; bi++) {axis[bi + aAxis.length] = bAxis[bi];}
		
		double[][] newAxis = removeDuplicateAxis(axis);
		
		return(newAxis);
	}
	
	public static boolean checkAxisEqual(double[] a, double[] b) {
		if(a[0] == b[0] && a[1] == b[1]/* || -a[0] == b[0] && -a[1] == b[1]*/) {return(true);}
		
		return(false);
	}
	
	public static boolean checkLines(double x1, double w1, double x2, double w2) {
		return((x1 >= x2 && x1 < x2 + w2 || x1 + w1 > x2 && x1 + w1 <= x2 + w2) ||
		(x2 >= x1 && x2 < x1 + w1 || x2 + w2 > x1 && x2 + w2 <= x1 + w1));
	}
	
	public static double[][] getEdge(double[][] points, int i) {
		double[][] edge;
		
		int i2 = i + 1;
		if(i2 >= points.length) {i2 = 0;}
			
		edge = new double[][]{points[i], points[i2]};
		
		return(edge);
	}
	
	public static double[][] appendDouble(double[][] array, double[] value) {
		if(array != null) {
			double[][] newArray = new double[array.length + 1][2];
		
			for(int i = 0; i < array.length; i++) {newArray[i] = array[i];}
		
			newArray[array.length] = value;
		
			return(newArray);
		}
		else {return(new double[][]{value});}
	}
	
	public static double[][] removeDuplicateAxis(double[][] axis) {
		double[][] newAxis = null;
		for(int i = 0; i < axis.length; i++) {
			if(newAxis != null) {
				boolean good = true;
				
				for(int i2 = 0; i2 < newAxis.length; i2++) {
					if(checkAxisEqual(newAxis[i2], axis[i])) {
						good = false;
						break;
					}
				}
				
				if(good) {newAxis = appendDouble(newAxis, axis[i]);}
			}
			else {newAxis = appendDouble(newAxis, axis[i]);}
		}
		
		return(newAxis);
	}
	
	public static boolean checkAxis(Solid a, Solid b, double[] axis) {
		double[][] pTemp = Geometry.minMax(a.points, axis);
		double[][] sTemp = Geometry.minMax(b.points, axis);
		
		double[] pMin = pTemp[0];
		double[] pMax = pTemp[1];
		double[] sMin = sTemp[0];
		double[] sMax = sTemp[1];
		
		double[] a1 = Geometry.project(pMin[0], pMin[1], axis);
		double[] a2 = Geometry.project(pMax[0], pMax[1], axis);
		double[] d1 = Geometry.project(sMin[0], sMin[1], axis);
		double[] d2 = Geometry.project(sMax[0], sMax[1], axis);
		
		double[] b1 = Geometry.rotate(a1, new double[]{1, 0});
		double[] b2 = Geometry.rotate(a2, new double[]{1, 0});
		double[] c1 = Geometry.rotate(d1, new double[]{1, 0});
		double[] c2 = Geometry.rotate(d2, new double[]{1, 0});
		
		if(a1[0] < 0) {b1[0] *= -1;}
		if(a2[0] < 0) {b2[0] *= -1;}
		if(d1[0] < 0) {c1[0] *= -1;}
		if(d2[0] < 0) {c2[0] *= -1;}
		
		double bw = b2[0] - b1[0];
		double cw = c2[0] - c1[0];
		
		if(checkLines(b1[0], bw, c1[0], cw)) {return(true);}
		
		return(false);
	}
	
	public static boolean circleAxis(double[] center, double radius, Solid b, double[] axis) {
		double[][] sTemp = Geometry.minMax(b.points, axis);
		
		double[] sMin = sTemp[0];
		double[] sMax = sTemp[1];
		
		double[] a1 = Geometry.project(center[0], center[1], axis);
		double[] d1 = Geometry.project(sMin[0], sMin[1], axis);
		double[] d2 = Geometry.project(sMax[0], sMax[1], axis);
		
		double[] b0 = Geometry.rotate(a1, new double[]{1, 0});
		double[] c1 = Geometry.rotate(d1, new double[]{1, 0});
		double[] c2 = Geometry.rotate(d2, new double[]{1, 0});
		
		if(a1[0] < 0) {b0[0] *= -1;}
		if(d1[0] < 0) {c1[0] *= -1;}
		if(d2[0] < 0) {c2[0] *= -1;}
		
		double[] b1 = new double[]{b0[0] - radius, 0};
		double[] b2 = new double[]{b0[0] + radius, 0};
		
		double bw = b2[0] - b1[0];
		double cw = c2[0] - c1[0];
		
		if(checkLines(b1[0], bw, c1[0], cw)) {return(true);}
		
		return(false);
	}

	public static boolean circleCircleAxis(double[] aCenter, double aRadius, double[] bCenter, double bRadius, double[] axis) {
		double[] a1 = Geometry.project(aCenter[0], aCenter[1], axis);
		double[] d1 = Geometry.project(bCenter[0], bCenter[1], axis);
		
		double[] b0 = Geometry.rotate(a1, new double[]{1, 0});
		double[] c0 = Geometry.rotate(d1, new double[]{1, 0});
		
		if(a1[0] < 0) {b0[0] *= -1;}
		if(d1[0] < 0) {c0[0] *= -1;}
		
		double[] b1 = new double[]{b0[0] - aRadius, 0};
		double[] b2 = new double[]{b0[0] + aRadius, 0};
		
		double[] c1 = new double[]{c0[0] - bRadius, 0};
		double[] c2 = new double[]{c0[0] + bRadius, 0};
		
		double bw = b2[0] - b1[0];
		double cw = c2[0] - c1[0];
		
		if(checkLines(b1[0], bw, c1[0], cw)) {return(true);}
		
		return(false);
	}
	
	public static double[][] getDistance(Solid a, Solid b, double[] axis) {
		double[][] pTemp = Geometry.minMax(a.points, axis);
		double[][] sTemp = Geometry.minMax(b.points, axis);
		
		double[] pMin = pTemp[0];
		double[] pMax = pTemp[1];
		double[] sMin = sTemp[0];
		double[] sMax = sTemp[1];
		
		double[] a1 = Geometry.project(pMin[0], pMin[1], axis);
		double[] a2 = Geometry.project(pMax[0], pMax[1], axis);
		double[] d1 = Geometry.project(sMin[0], sMin[1], axis);
		double[] d2 = Geometry.project(sMax[0], sMax[1], axis);
		
		double[] b1 = Geometry.rotate(a1, new double[]{1, 0});
		double[] b2 = Geometry.rotate(a2, new double[]{1, 0});
		double[] c1 = Geometry.rotate(d1, new double[]{1, 0});
		double[] c2 = Geometry.rotate(d2, new double[]{1, 0});
		
		if(a1[0] < 0) {b1[0] *= -1;}
		if(a2[0] < 0) {b2[0] *= -1;}
		if(d1[0] < 0) {c1[0] *= -1;}
		if(d2[0] < 0) {c2[0] *= -1;}
		
		double distance1 = b1[0] - c2[0];
		double distance2 = b2[0] - c1[0];
		
		//distance1 *= 1.0000001;
		//distance2 *= 1.0000001;
		
		double distance3 = -distance1;
		double distance4 = -distance2;
		
		double[] move1 = new double[]{axis[0] * distance1, axis[1] * distance1};
		double[] move2 = new double[]{axis[0] * distance2, axis[1] * distance2};
		double[] move3 = new double[]{axis[0] * distance3, axis[1] * distance3};
		double[] move4 = new double[]{axis[0] * distance4, axis[1] * distance4};
		
		return(new double[][]{move1, move2, move3, move4});
	}
	
	public static double[][] circleDistance(double[] center, double radius, Solid b, double[] axis) {
		double[][] sTemp = Geometry.minMax(b.points, axis);
		
		double[] sMin = sTemp[0];
		double[] sMax = sTemp[1];
		
		double[] a1 = Geometry.project(center[0], center[1], axis);
		double[] d1 = Geometry.project(sMin[0], sMin[1], axis);
		double[] d2 = Geometry.project(sMax[0], sMax[1], axis);
		
		double[] b0 = Geometry.rotate(a1, new double[]{1, 0});
		double[] c1 = Geometry.rotate(d1, new double[]{1, 0});
		double[] c2 = Geometry.rotate(d2, new double[]{1, 0});
		
		if(a1[0] < 0) {b0[0] *= -1;}
		if(d1[0] < 0) {c1[0] *= -1;}
		if(d2[0] < 0) {c2[0] *= -1;}
		
		double[] b1 = new double[]{b0[0] - radius, 0};
		double[] b2 = new double[]{b0[0] + radius, 0};
		
		double distance1 = b1[0] - c2[0];
		double distance2 = b2[0] - c1[0];
		
		//distance1 *= 1.0000000001;
		//distance2 *= 1.0000000001;
		
		double distance3 = -distance1;
		double distance4 = -distance2;
		
		double[] move1 = new double[]{axis[0] * distance1, axis[1] * distance1};
		double[] move2 = new double[]{axis[0] * distance2, axis[1] * distance2};
		double[] move3 = new double[]{axis[0] * distance3, axis[1] * distance3};
		double[] move4 = new double[]{axis[0] * distance4, axis[1] * distance4};
		
		return(new double[][]{move1, move2, move3, move4});
	}

	public static double[][] circleCircleDistance(double[] aCenter, double aRadius, double[] bCenter, double bRadius, double[] axis) {
		double[] a1 = Geometry.project(aCenter[0], aCenter[1], axis);
		double[] d1 = Geometry.project(bCenter[0], bCenter[1], axis);
		
		double[] b0 = Geometry.rotate(a1, new double[]{1, 0});
		double[] c0 = Geometry.rotate(d1, new double[]{1, 0});
		
		if(a1[0] < 0) {b0[0] *= -1;}
		if(d1[0] < 0) {c0[0] *= -1;}
		
		double[] b1 = new double[]{b0[0] - aRadius, 0};
		double[] b2 = new double[]{b0[0] + aRadius, 0};
		
		double[] c1 = new double[]{c0[0] - bRadius, 0};
		double[] c2 = new double[]{c0[0] + bRadius, 0};
		
		double distance1 = b1[0] - c2[0];
		double distance2 = b2[0] - c1[0];
		
		//distance1 *= 1.00000001;
		//distance2 *= 1.00000001;
		
		double distance3 = -distance1;
		double distance4 = -distance2;
		
		double[] move1 = new double[]{axis[0] * distance1, axis[1] * distance1};
		double[] move2 = new double[]{axis[0] * distance2, axis[1] * distance2};
		double[] move3 = new double[]{axis[0] * distance3, axis[1] * distance3};
		double[] move4 = new double[]{axis[0] * distance4, axis[1] * distance4};
		
		return(new double[][]{move1, move2, move3, move4});
	}
	
	public static int getSmallestIndex(double[] nums) {
		double smallest = 0;
		int index = -1;
		
		for(int i = 0; i < nums.length; i++) {
			if(nums[i] < smallest || index == -1) {
				smallest = nums[i];
				index = i;
			}
		}
		
		return(index);
	}
	
	public static int getSmallestPositiveIndex(double[] nums) {
		double smallest = 0;
		int index = -1;
		
		for(int i = 0; i < nums.length; i++) {
			if(nums[i] < smallest || index == -1) {
				if(nums[i] >= 0) {
					smallest = nums[i];
					index = i;
				}
			}
		}
		
		return(index);
	}
	
	public static double[] getRoundRoundAxis(Solid a, Solid b) {
		double[] axis = Geometry.normalize(
			a.center[0] - b.center[0],
			a.center[1] - b.center[1]
		);

		return(axis);
	}
	
	public static double[] getRoundFlatAxis(double[] center, Solid other) {
		double[] pointDists = new double[other.points.length];
		double[] edgeDists = new double[other.points.length];
		
		for(int i = 0; i < pointDists.length; i++) {
			pointDists[i] = Math.sqrt(Math.pow(other.points[i][0] - center[0], 2) + Math.pow(other.points[i][1] - center[1], 2));
			edgeDists[i] = Geometry.perpendicularDistance(getEdge(other.points, i), center);
		}
		
		int closestPoint = getSmallestPositiveIndex(pointDists);
		int closestEdge = getSmallestPositiveIndex(edgeDists);
		int ce = closestEdge;
		
		if(ce != -1) {
			if(edgeDists[closestEdge] <= pointDists[closestPoint]) {
				double[] axis = Geometry.normalize(
						getEdge(other.points, ce)[0][0] - getEdge(other.points, ce)[1][0],
						getEdge(other.points, ce)[0][1] - getEdge(other.points, ce)[1][1]
				);

				axis = new double[]{-axis[1], axis[0]};
				
                return(axis);
			}
			else {
				double[] axis = Geometry.normalize(other.points[closestPoint][0] - center[0], other.points[closestPoint][1] - center[1]);
				
				return(axis);
			}
		}
		else {
			double[] axis = Geometry.normalize(other.points[closestPoint][0] - center[0], other.points[closestPoint][1] - center[1]);
			
			return(axis);
		}
	}
	
	public static boolean checkCollision(Solid p, Solid s) {
		double[][] axis = getAxis(p, s);
		
		for(int i = 0; i < axis.length; i++) {if(!checkAxis(p, s, axis[i])) {return(false);}}
		
		for(int i = 0; i < s.points.length; i++) {
			if(s.roundVerts[i] != 0)  {
				double[] center = s.center;
				double radius = s.radius;
				double[] roundAxis = getRoundFlatAxis(center, p);
				
				if(s.roundVerts[i] > 0)  {if(!circleAxis(center, radius, p, roundAxis)) {return(false);}}
				else {if(!circleAxis(center, radius, p, roundAxis)) {return(false);}}
			}
		}
		
		for(int i = 0; i < p.points.length; i++) {
			if(p.roundVerts[i] != 0)  {
				double[] center = p.center;
				double radius = p.radius;
				double[] roundAxis = getRoundFlatAxis(center, s);
				
				if(p.roundVerts[i] > 0)  {if(!circleAxis(center, radius, s, roundAxis)) {return(false);}}
				else {if(!circleAxis(center, radius, s, roundAxis)) {return(false);}}
				
				for(int h = 0; h < s.points.length; h++) {
					if(s.roundVerts[h] != 0)  {
						double[] center2 = s.center;
						double radius2 = s.radius;
						double[] roundAxis2 = getRoundRoundAxis(s, p);
						
						if(s.roundVerts[i] > 0)  {if(!circleCircleAxis(center, radius, center2, radius2, roundAxis2)) {return(false);}}
						else {if(!circleCircleAxis(center, radius, center2, radius2, roundAxis2)) {return(false);}}
					}
				}
			}
		}
		
		return(true);
	}
	
	public static double[] collisionResponse(Solid p, Solid s) {
		double[][] axis = getAxis(p, s);
		double[][] movements = null;
		
		for(int i = 0; i < axis.length; i++) {
			if(checkAxis(p, s, axis[i])) {
				double[][] tempMoves = getDistance(p, s, axis[i]);
				
				for(int j = 0; j < tempMoves.length; j++) {
					for(int m = 10; m > 0; m--) {
						double[] superTemp = new double[]{tempMoves[j][0] / m, tempMoves[j][1] / m};
						
						movements = appendDouble(movements, superTemp);
					}
				}
			}
		}
		
		for(int i = 0; i < s.points.length; i++) {
			if(s.roundVerts[i] != 0)  {
				double[] center = s.center;
				double radius = s.radius;
				double[] roundAxis = getRoundFlatAxis(center, p);
				
				if(circleAxis(center, radius, p, roundAxis)) {
					double[][] tempMoves = circleDistance(center, radius, p, roundAxis);
					
					for(int j = 0; j < tempMoves.length; j++) {
						for(int m = 10; m > 0; m--) {
							double[] superTemp = new double[]{tempMoves[j][0] / m, tempMoves[j][1] / m};
							
							movements = appendDouble(movements, superTemp);
						}
					}
				}
			}
		}
		
		for(int i = 0; i < p.points.length; i++) {
			if(p.roundVerts[i] != 0)  {
				double[] center = p.center;
				double radius = p.radius;
				double[] roundAxis = getRoundFlatAxis(center, s);
				
				if(circleAxis(center, radius, s, roundAxis)) {
					double[][] tempMoves = circleDistance(center, radius, s, roundAxis);
					
					for(int j = 0; j < tempMoves.length; j++) {
						for(int m = 10; m > 0; m--) {
							double[] superTemp = new double[]{tempMoves[j][0] / m, tempMoves[j][1] / m};
							
							movements = appendDouble(movements, superTemp);
						}
					}
				}

				for(int h = 0; h < s.points.length; h++) {
					if(s.roundVerts[h] != 0)  {
						double[] center2 = s.center;
						double radius2 = s.radius;
						double[] roundAxis2 = getRoundRoundAxis(s, p);
						
						if(circleCircleAxis(center, radius, center2, radius2, roundAxis2)) {
							double[][] tempMoves = circleCircleDistance(center, radius, center2, radius2, roundAxis2);
							
							for(int j = 0; j < tempMoves.length; j++) {
								for(int m = 1; m > 0; m--) {
									double[] superTemp = new double[]{tempMoves[j][0] / m, tempMoves[j][1] / m};
									
									movements = appendDouble(movements, superTemp);
								}
							}
						}
					}
				}
			}
		}
		
		movements = removeDuplicateAxis(movements);
		
		double smallestDistance = 0;
		int smallestIndex = -1;
		
		for(int i = 0; i < movements.length; i++) {
			double tempDistance = Math.sqrt(Math.pow(movements[i][0], 2) + Math.pow(movements[i][1], 2));
			
			Solid testSolid = null;
			
			if(p instanceof Circle) {testSolid = new Circle(p.x + movements[i][0], p.y + movements[i][1], p.w * 0.999999, p.h * 0.999999);}
			//if(p instanceof Rectangle) {testSolid = new Rectangle(p.x + movements[i][0], p.y + movements[i][1], p.w, p.h);}
			
			//System.out.println("axis being tested = (" + movements[i][0] + ", " + movements[i][1] + ")");
			
			if(!checkCollision(testSolid, s)) {
				if(tempDistance < smallestDistance || smallestIndex == -1) {
					smallestIndex = i;
					smallestDistance = tempDistance;
					
					//System.out.println("AXIS PASSED");
					
					//if(movements[i][0] < 0) {System.out.println("MOVED LEFT");}
				}
			}
		}
		
		//System.out.println();
		
		if(smallestIndex >= 0) {return(movements[smallestIndex]);}
		else {return(new double[]{0, 0});}
	}
}
