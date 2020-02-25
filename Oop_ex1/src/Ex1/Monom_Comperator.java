package Ex1;

import java.util.Collections;
import java.util.Comparator;

import Ex1.Polynom.Sortbypower;

public class Monom_Comperator implements Comparator<Monom> {

	public Monom_Comperator() {
		;}
	public int compare(Monom o1, Monom o2) {
		int dp = o2.get_power() - o1.get_power();
		return dp;
	}
	
	public Polynom arrange (Monom o1, Monom o2) {
		Polynom p = new Polynom();
		p.add(o1);
		p.add(o2);
		return p;
		
	}
	// ******** add your code below *********
	
}
