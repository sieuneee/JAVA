package cse3040_hw2;

public class Problem10 {

	public static void main(String[] args) {
		Points p1 = new Points(new double[] {1.0, 2.0, 3.0});
		Points p2 = new Points(new double[] {4.0, 5.0, 6.0});
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p1.equals(p2));
		Points p3 = new Points(new double[] {1.0, 4.0, 7.0});
		Points p4 = new Points(new double[] {3.0, 9.0});
		System.out.println(p3);
		System.out.println(p4);
		System.out.println(p3.equals(p4));
		Points p5 = new Points(new double[] {1.0, 2.0});
		Points p6 = null;
		System.out.println(p5);
		System.out.println(p6);
		System.out.println(p5.equals(p6));
	}
	
}

class Points{
	double[] arr=null;
	double val=0;
	Points(double ary[]){
		this.arr = new double[ary.length];
		for(int i=0;i<ary.length;i++) {this.arr[i] = ary[i];}
		for(int i=0;i<this.arr.length;i++) this.val+=this.arr[i];
	}
	boolean equals(Points px) {
		if(px==null) return false;
		if(this.val==px.val) return true;
		else return false;
	}
	@Override
	public String toString() {
		return "[ sum of point: "+this.val+ "]";
	}
}
