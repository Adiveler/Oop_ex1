package Ex1;

import java.util.StringTokenizer;

import javax.sql.rowset.spi.TransactionalWriter;

import org.junit.internal.Throwables;

public class ComplexFunction implements complex_function {
	function f1,f2;
	Operation op; 
	public ComplexFunction(function f) {
		f1 = f;
		f2 = null;
		op = Operation.None;
	}
	public ComplexFunction(String op_ ,function f1_, function f2_ ) {
		f1 = f1_;
		f2 = f2_;
		op_ = op_.toLowerCase();
		if (f2_ == null) op = Operation.None;
		else if (op_.equals("plus")) op = Operation.Plus;
		else if (op_.equals("mul")) op = Operation.Times;
		else if (op_.equals("div")) op = Operation.Divid;
		else if (op_.equals("max")) op = Operation.Max;
		else if (op_.equals("min")) op = Operation.Min;
		else if (op_.equals("comp")) op = Operation.Comp;
		else op = Operation.Error;
	}
	/** Add to this complex_function the f1 complex_function
	 * 
	 * @param f1 the complex_function which will be added to this complex_function.
	 */
	public void plus(function f1) {
		this.f1 = f1;
		this.f2 = this.copy();
		op = Operation.Plus;
	}
	@Override
	public double f(double x) {
		ComplexFunction a = new ComplexFunction("plus", this.f1, this.f2);
		double sum = a.f1.f(x) + a.f2.f(x);
		return sum;
	}
	@Override
	public function initFromString(String s) {
		ComplexFunction cf;
		s = s.replaceAll("\\s+","");//delete spaces
		if (s.charAt(0) == '('){
			Polynom p = new Polynom (s.substring(1, s.length()-2));
			cf = new ComplexFunction(p);
			cf.setOp("");
		}
		else {
			String [] func = s.split("(");
			Polynom p = new Polynom (func[1].substring(0, func.length-2));
			cf = new ComplexFunction(p);
			cf.setOp(func[0]);
		}
		return cf;
	}
	@Override
	public function copy() {
		ComplexFunction f = new ComplexFunction(op.toString(), f1, f2);
		return f;
	}
	@Override
	public void mul(function f1) {
//		ComplexFunction tmp = f2;
//		while (tmp.f2 != null) {
//			tmp = tmp.f2;
//		}
//		tmp.f2 = new ComplexFunction(f1);
//		
//		tmp.op = Operation.Times;
//		
		this.f1 = f1;
		this.f2 = this.copy();
		op = Operation.Times;
	}
	@Override
	public void div(function f1) {
		this.f1 = f1;
		this.f2 = this.copy();
		op = Operation.Divid;
		
	}
	@Override
	public void max(function f1) {
		this.f1 = f1;
		this.f2 = this.copy();
		op = Operation.Max;
		
	}
	@Override
	public void min(function f1) {
		this.f1 = f1;
		this.f2 = this.copy();
		op = Operation.Min;
		
	}
	@Override
	public void comp(function f1) {
		this.f1 = f1;
		this.f2 = this.copy();
		op = Operation.Comp;
		
	}
	@Override
	public function left() {
		return f1;
	}
	@Override
	public function right() {
		while (f2 != null) {
			ComplexFunction f = new ComplexFunction(f2);
			return f.right();
		}
		return f1;
	}
	@Override
	public Operation getOp() {
		return op;
	}
	public void setOp(String s) {
		if (s.isEmpty()) op = Operation.None;
		else if (s.equals("plus")) op = Operation.Plus;
		else if (s.equals("mul")) op = Operation.Times;
		else if (s.equals("div")) op = Operation.Divid;
		else if (s.equals("max")) op = Operation.Max;
		else if (s.equals("min")) op = Operation.Min;
		else if (s.equals("comp")) op = Operation.Comp;
		else op = Operation.Error;
	}
	@Override
	public String toString() {
		return op+"("+f1+","+f2+")";
	}
	
}
