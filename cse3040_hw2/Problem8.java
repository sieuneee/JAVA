package cse3040_hw2;

public class Problem8 {
	
	public static void main(String[] args) {
		Shape[] arr = { new Circle(5.0), new Square(4.0),
				new Rectangle(3.0, 4.0), new Square(5.0)};
		System.out.println("Total area of the shapes is: " + sumArea(arr));
	}

	static double sumArea(Shape[] arr) {
		double res=0;
		for(Shape s : arr) res += s.calcArea();
		return res;
	}
	
	public interface Shape{
		double calcArea();
	}
	
	static class Circle implements Shape{
		private double r;
		Circle(double r){ this.r = r; }
		@Override public double calcArea() {return Math.PI*r*r;}
	}
	
	static class Square implements Shape{
		private double x;
		Square(double x) { this.x = x;}
		@Override public double calcArea() {return x*x;}
	}
	
	static class Rectangle implements Shape{
		private double x, y;
		Rectangle(double x, double y){
			this.x = x;
			this.y = y;
		}
		@Override public double calcArea() { return x*y; }
	}

}
