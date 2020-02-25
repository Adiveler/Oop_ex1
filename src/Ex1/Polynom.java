package Ex1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;


/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{

	private ArrayList<Monom> monoms = new ArrayList<Monom>();
	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		;
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		s = s.replaceAll("\\s+","");
		s = s.toLowerCase(); // Change 'X' to 'x'
		Polynom p = inBracks(s);
		//System.out.println("in constructor: "+p);
		add(p);
	}
	//this function focus inside the brackets
	/**
	 * get String without "()" characters
	 * @return Polynom
	 */
	private Polynom inBracks (String s) {
		s = s + "+0";
		Polynom p = new Polynom();
		int init = 0;
		for (int i = 1; i < s.length(); i++) {
			if (s.charAt(i) == '+' || s.charAt(i) == '-') {
				String ms = s.substring(init, i);
				Monom m = new Monom (ms);
				init = i;
				p.add(m);
			}
		}
		return p;
	}
	/**
	 * replace x with a number and calculate the polynom
	 * @return number
	 */
	@Override
	public double f(double x) {
		double ans = 0;
		for (int i = 0; i < monoms.size(); i++) {
			ans = ans + monoms.get(i).f(x);
		}
		return ans;
	}

	
	/**
	 * Add p1 to this Polynom
	 * @param p1
	 */
	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> iter = p1.iteretor();
		while (iter.hasNext())
			add(iter.next());
	}

	/**
	 * Add m1 to this Polynom
	 * @param m1 Monom
	 */
	@Override
	public void add(Monom m1) {
		for (int i = 0; i < monoms.size(); i++) {
			if (monoms.get(i).get_power() == m1.get_power()) {//if we have 2 monoms with the same power (like 3x^4 + 6x^4)
				int power = m1.get_power();
				double coefficient = m1.get_coefficient() + monoms.get(i).get_coefficient();
				monoms.remove(i);
				Monom m2 = new Monom (coefficient, power);
				monoms.add(m2);
				Collections.sort(monoms, new Sortbypower());
				return;
			}
		}
		Monom copy = new Monom (m1);
		monoms.add(copy);
		

		Collections.sort(monoms, new Sortbypower());

	}
	/**
	 * Subtract p1 from this Polynom
	 * @param p1
	 */
	@Override
	public void substract(Polynom_able p2) {
		Polynom_able p1 = new Polynom (p2.toString());
		Iterator<Monom> iter = p1.iteretor();
		while (iter.hasNext()) {
			Monom m = new Monom (iter.next());
			m.multipy(Monom.MINUS1);
			add(m);
		}
	}
	/**
	 * Multiply this Polynom by p1
	 * @param p1
	 */
	@Override
	public void multiply(Polynom_able p1) {
		Iterator<Monom> iter = p1.iteretor();
		ArrayList<Polynom_able> poly = new ArrayList<Polynom_able>();
		while (iter.hasNext()) {
			Polynom_able tmp = (Polynom_able)this.copy();
			Monom m = new Monom (iter.next());
			tmp.multiply(m);
			poly.add(tmp);			
		}
		monoms.clear();
		for (Polynom_able polynom_able_ : poly) {
			add(polynom_able_);
		}
	}
	/**
	 * Test if this Polynom is logically equals to p1.
	 * @param p1
	 * @return true if this polynom represents the same function as p1
	 */
	@Override
	public boolean equals(Object obj) {
		Polynom p1 = (Polynom) obj;
		boolean eq = true;
		Iterator<Monom> iter_1 = p1.iteretor();
		Iterator<Monom> iter_this = this.iteretor();
		
		while (iter_1.hasNext() && iter_this.hasNext()) {
			Monom m1 = iter_1.next();
			Monom mThis =iter_this.next();
			
			if(!m1.equals(mThis))
				eq = false;
			
		}
		if(iter_1.hasNext() || iter_this.hasNext())
			eq = false;
		return eq;
	}
	/**
	 * Test if this is the Zero Polynom
	 * @return
	 */
	@Override
	public boolean isZero() {
		boolean zero = true;
		for (int i = 0; i < monoms.size(); i++) {
			if (monoms.get(i).get_coefficient() != 0)
				zero = false;
		}
		return zero;
	}
	/**
	 * Compute a value x' (x0<=x'<=x1) for with |f(x')| < eps
	 * assuming (f(x0)*f(x1)<=0, else should throws runtimeException 
	 * computes f(x') such that:
	 * 	(i) x0<=x'<=x1 && 
	 * 	(ii) |f(x')|<eps
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps>0 (positive) representing the epsilon range the solution should be within.
	 * @return an approximated value (root) for this (cont.) function 
	 */
	@Override
	public double root(double x0, double x1, double eps) {
		if (f(x0)*f(x1) > 0)
			throw new RuntimeException();
		double x = (x0 + x1)/2;
		if (Monom.comp(f(x), 0, eps))
			return x;
		if (f(x0)*f(x) > 0)
			return root(x,x1,eps);
		else
			return root(x0,x,eps);
	}
	/**
	 * create a deep copy of this Polynom
	 * @return 
	 */
	@Override
	public function copy() {
		Polynom p = new Polynom (this.toString());
		return p;
	}
	/**
	 * Compute a new Polynom which is the derivative of this Polynom
	 * @return
	 */
	@Override
	public Polynom_able derivative() {
		Polynom_able p = new Polynom();
		for (Monom monom : monoms) {
			p.add(monom.derivative());
		}
		return p;
	}
	/**
	 * Compute a Riman's integral from x0 to x1 in eps steps. 
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps positive step value
	 * @return the approximated area above X-axis below this function bounded in the range of [x0,x1]
	 */
	@Override
	public double area(double x0, double x1, double eps) {
		double localarea = 0;
		for (double x = x0; x <= x1; x += eps) {
			if (f(x) > 0)
				localarea += eps*f(x); 
		}
		return localarea;
	}
	/**
	 * @return an Iterator (of Monoms) over this Polynom
	 * @return
	 */
	@Override
	public Iterator<Monom> iteretor() {
		return monoms.iterator();
	}
	/**
	 * Multiply this Polynom by Monom m1
	 * @param m1
	 */
	@Override
	public void multiply(Monom m1) {
		for (Monom monom : monoms) {
			monom.multipy(m1);
		}
	}
	/**
	 * get a String of the polynom
	 * @return String
	 */
	@Override
	public String toString() {
		String out = "";
		for (Monom m : monoms) {
			out = out + m.toString();
		}
		return out;
	}
	
	/**
	 * compare powers
	 * @author Adi
	 *
	 */
	class Sortbypower implements Comparator<Monom> 
	{ 
	    // Used for sorting in ascending order of 
	    // roll name 
	    public int compare(Monom a, Monom b) 
	    { 
	        return Monom.comperator(a, b); 
	    } 
	}

	
	
	@Override
	public function initFromString(String s) {
		return new Polynom(s);
	} 
	
	
	
}
