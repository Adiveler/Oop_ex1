package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Polynom;
import Ex1.Polynom_able;
import Ex1.function;

class CFTest {

	@Test
	void testConstructor() {
		function p = new Polynom("x^2");
		ComplexFunction f = new ComplexFunction(p);
		assertEquals("None(+1.0x^2,null)",f.toString());
	}
	@Test
	void testConstructor2() {
		function p1 = new Polynom("x^2");
		function p2 = new Polynom("3x^2");
		ComplexFunction f = new ComplexFunction("plus", p1, p2);
		assertEquals("Plus(+1.0x^2,+3.0x^2)",f.toString());
	}
	@Test
	void testCopy() {
		function p = new Polynom("x^2");
		function f1 = new ComplexFunction(p);
		function f2 = f1.copy();
		assertEquals(f1.toString(),f2.toString());
	}
	@Test
	void testPlus() {
		function p1 = new Polynom("x^2");
		function p2 = new Polynom("3x^2");
		ComplexFunction f = new ComplexFunction(p1);
		f.plus(p2);
		assertEquals("Plus(+3.0x^2,None(+3.0x^2,null))",f.toString());
	}
}
