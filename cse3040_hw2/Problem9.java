package cse3040_hw2;

public class Problem9 {
	public static void main(String[] args) {
		Point p1 = new Point(new double[] {1.0, 2.0, 3.0});
		Point p2 = new Point(new double[] {4.0, 5.0, 6.0});
		System.out.println("Euclidean Distance: " + EuclideanDistance.getDist(p1, p2));
		System.out.println("Manhattan Distance: " + ManhattanDistance.getDist(p1, p2));
		Point p3 = new Point(new double[] {1.0, 2.0, 3.0});
		Point p4 = new Point(new double[] {4.0, 5.0});
		System.out.println("Euclidean Distance: " + EuclideanDistance.getDist(p3, p4));
		System.out.println("Manhattan Distance: " + ManhattanDistance.getDist(p3, p4));
	}
}
class EuclideanDistance{
	static double getDist(Point p1, Point p2) {
		if(p1.arr.length!=p2.arr.length) {return -1;}
		else {
			double res=0;
			for(int i=0;i<p1.arr.length;i++) {
				res += Math.pow(p1.arr[i]-p2.arr[i], 2);
			}
			res = Math.sqrt(res);
			return res;
		}
	}
}
class ManhattanDistance{
	static double getDist(Point p1, Point p2) {
		if(p1.arr.length!=p2.arr.length) {return -1;}
		else {
			double res=0;
			for(int i=0;i<p1.arr.length;i++) {
				res += Math.abs(p1.arr[i]-p2.arr[i]);
			}
			return res;
		}
	}
}
class Point{
	double[] arr;
	Point(double ary[]){
		this.arr = new double[ary.length];
		for(int i=0;i<ary.length;i++) {this.arr[i] = ary[i];}
	}
}
