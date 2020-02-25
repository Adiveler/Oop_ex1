package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import Ex1.Monom;
import Ex1.Polynom;
import Ex1.Polynom_able;
import Ex1.function;

class JunitPolynomTest {
	
	@Test
	void testpoly() {
		Polynom p = new Polynom();
		assertEquals(0,p.f(1));
	}

	@Test
	void testf() {
		Polynom p = new Polynom ("x^2+3");
		assertEquals(28, p.f(5));
	}

	@Test
	void testaddpoly() {
		Polynom p1 = new Polynom ("x^2+3");
		Polynom p2 = new Polynom ("5x^2+4x+6");
		p1.add(p2);
		assertEquals("+9.0x^0+4.0x^1+6.0x^2", p1.toString());
	}
	
	@Test
	void testaddmon() {
		Polynom p = new Polynom ("x^2+3");
		Monom m = new Monom (5,2);
		p.add(m);
		assertEquals("+3.0x^0+6.0x^2", p.toString());
	}
	
	@Test
	void testsub() {
		Polynom p1 = new Polynom ("5x^2+4x+6");
		Polynom p2 = new Polynom ("x^2+3");
		p1.substract(p2);
		assertEquals("+3.0x^0+4.0x^1+4.0x^2", p1.toString());
	}
	
	@Test
	void testmultpoly() {
		Polynom p1 = new Polynom ("5x^2+4x");
		Polynom p2 = new Polynom ("x^2+3");
		p1.multiply(p2);
		assertEquals("+12.0x^1+15.0x^2+4.0x^3+5.0x^4", p1.toString());
	}
	
	@Test
	void testequal() {
		Polynom p1 = new Polynom ("5x^2+4x");
		Polynom p2 = new Polynom ("x^2+3");
		assertEquals(true, p1.equals(p1));
		assertEquals(false, p1.equals(p2));
	}
	
	@Test
	void testiszero() {
		Polynom p1 = new Polynom ("5x^2+4x");
		Polynom p2 = new Polynom ("0x^2");
		assertEquals(false, p1.isZero());
		assertEquals(true, p2.isZero());
	}
	
	@Test
	void testroot() {
		Polynom p = new Polynom ("x^2+x");
		assertEquals(0, p.root(-0.5, 0.5, Monom.EPSILON),0.0001);
	}
	
	@Test
	void testcopy() {
		Polynom p1 = new Polynom ("x^2+x");
		function p2 = p1.copy();
		assertEquals(true, p1.equals(p2));
	}
	
	@Test
	void testderivative() {
		Polynom p1 = new Polynom ("4x^2+3x+5");
		Polynom_able p2 = p1.derivative();
		assertEquals("+3.0x^0+8.0x^1", p2.toString());
	}
	
	@Test
	void testarea() {
		Polynom p = new Polynom ("4x^2+3x+5");
		double x = p.area(0, 1, 0.00001);
		assertEquals(7.834, x, 0.001);
	}
	
	@Test
	void testiterator() {
		Polynom p = new Polynom ("4x^2+3x+5");
		Iterator <Monom> x = p.iteretor();
		int i = 1;
		while (x.hasNext()) {
			Monom m = new Monom (x.next());
			if (i == 1)
				assertEquals("+5.0x^0", m.toString());
			if (i == 2)
				assertEquals("+3.0x^1", m.toString());
			if (i == 3)
				assertEquals("+4.0x^2", m.toString());
			i++;
		}
	}
	
	@Test
	void testmultmon() {
		Polynom p = new Polynom ("5x^2+4x");
		Monom m = new Monom ("-x^2");
		p.multiply(m);
		assertEquals("-4.0x^3-5.0x^4", p.toString());
	}
	
	@Test
	void testtoString() {
		Polynom p = new Polynom ("5x^2+4x+7");
		assertEquals("+7.0x^0+4.0x^1+5.0x^2", p.toString());
	}
	
	@Test
	void testinitFromString() {
		String s = "5x^2+4x+7";
		Polynom p = new Polynom(s);
		assertEquals("+7.0x^0+4.0x^1+5.0x^2", p.toString());
	}
}
