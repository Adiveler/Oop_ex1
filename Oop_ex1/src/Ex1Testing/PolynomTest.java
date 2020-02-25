package Ex1Testing;

import Ex1.Monom;
import Ex1.Polynom;
import Ex1.Polynom_able;

public class PolynomTest {
	public static void main(String[] args) {
		
		Polynom p1 = new Polynom ("x^2+1");
		Polynom p2 = new Polynom ("2x^3+5x^2-3x");
		p1.add(p2);
		System.out.println(p1);
	}
	
	
	
	
	
	
	private static void test() {
		
		String s = "3+1.4X^3-34x";
		Polynom p = new Polynom (s);
		System.out.println(p);
		
		Polynom_able pc = (Polynom_able)p.copy();
		System.out.println(pc);
		System.out.println(" p = copy p: " + p.equals(pc));
		pc.substract(p);
		System.out.println(pc.isZero());
		
		s = "2x+3x^2";
		Polynom p1 = new Polynom (s);
		
		s = "4x-5x^3";
		Polynom p2 = new Polynom (s);
		
		p1.substract(p2);
		System.out.println(p1);
		System.out.println(p2.derivative());
		
		
	}
	private static void test0() {
		Polynom p1 = new Polynom();
		p1.add(new Monom (1.4, 3));
		p1.add(new Monom (0.6, 3));
		p1.add(new Monom (3.8, 5));
		Polynom p2 = new Polynom();
		p2.add(new Monom (7, 3));
		p1.add(p2);
		System.out.println(p1);
		System.out.println(p1.f(2));
	}
	public static void test1() {
		Polynom p1 = new Polynom();
		String[] monoms = {"1","x","x^2", "0.5x^2"};
		//for(int i=0;i<monoms.length;i++) {
		Monom m = new Monom(monoms[1]);
		p1.add(m);
		double aa = p1.area(0, 1, 0.0001);
		p1.substract(p1);
		System.out.println(p1);
	}
	public static void test2() {
		Polynom p1 = new Polynom(), p2 =  new Polynom();
		/*String[] monoms1 = {"2", "-x","-3.2x^2","4","-1.5x^2"};
		String[] monoms2 = {"5", "1.7x","3.2x^2","-3","-1.5x^2"};*/
		String[] monoms1 = {"x^2","1"};
		String[] monoms2 = {"x"};
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		System.out.println("p1: "+p1);
		System.out.println("p2: "+p2);
		p1.add(p2);
		System.out.println("p1+p2: "+p1);
		p1.multiply(p2);
		System.out.println("(p1+p2)*p2: "+p1);
		String s1 = p1.toString();
		//Polynom_able pp1 = Polynom.parse(s1);
		//System.out.println("from string: "+pp1);
	}
}
