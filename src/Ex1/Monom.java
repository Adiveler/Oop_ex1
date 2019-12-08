
package Ex1;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	
	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	@Override
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************
	public Monom(String s) {
		s = s.replaceAll("\\s+","");
		boolean ispositive = true;
		if (s.charAt(0)=='-') {
			ispositive = false;
			s = s.substring(1);
		}
		if (s.charAt(0)=='+') {
			s = s.substring(1);
		}
		if (s.charAt(0)=='x')
			s = "1" + s;
		
		if (!s.contains("^")) {
			if (!s.contains("x"))
				s = s+"x^0";
			else
				s = s+"^1";
		}
		String[] mcoefficient = s.split("x\\^");
		double coef = Double.parseDouble(mcoefficient[0]);
		if (!ispositive)
			coef = coef*(-1);
		this.set_coefficient(coef);
		this.set_power(Integer.parseInt(mcoefficient[1]));
		
	}
	
	public void add(Monom m) {
		if (m.get_power() == _power) {
			_coefficient = m.get_coefficient() + _coefficient;
		}
	}
	
	public void multipy(Monom d) {
		_power = d.get_power() + _power;
		_coefficient = d.get_coefficient() * _coefficient;
	}
	
	public String toString() {
		String ans = "";
		if (this._coefficient > 0)
			ans = "+";
		ans = ans+this._coefficient+"x^"+this.get_power();
		return ans;
	}
	// you may (always) add other methods.
	
	public boolean equals(Monom m) {	
		if (comp(_coefficient, m.get_coefficient()) && _power == m.get_power())
			return true;
		return false;
	}
	public static boolean comp (double a, double b ) {
		return comp(a,b,EPSILON);
	}
	public static boolean comp (double a, double b, double eps ) {
		double sub = a - b;
		if (sub <= eps && sub >= -eps)
			return true;
		return false;
	}
	
	public static int comperator(Monom a, Monom b) {
		return (a._power - b._power);
	}
	//****************** Private Methods and Data *****************
	

	private void set_coefficient(double a){
		this._coefficient = a;
	}
	
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	
	
	
	@Override
	public function initFromString(String s) {
		return new Monom(s);
	}
	@Override
	public function copy() {
		return new Monom(this);
	}
	
	
}
