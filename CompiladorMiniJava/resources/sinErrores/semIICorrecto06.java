///[SinErrores]
// 

class A {
	
	int num;
	C obj;
	Object anotherObj;
	
	public A() {
		obj = new C();
	}
	
	void test() {
		num = getNumber();
		num = getObject().getNumber();
		num = getObject().x;
		obj.metodo();
	}
	
	void testConformidad() {
		var b = new B();
		obj.conformidad(b, b, obj);
	}
	
	int getNumber() {
		return 5;
	}
	
	B getObject() {
		return new B();
	}
	
}

class B extends A {
	
	int x;
	
	public B() {
		x = this.getNumber();
		num = x;
	}
	
	int getNumber() {
		return 10;
	}
	
	void testVariables(int i, String s) {
		x = i + 2;
		var str = s;
		var bool = str == null;
	}
	
}

class C {
	
	int a;
	
	static void metodoEstatico() {
	}
	
	void metodo() {
		
	}
	
	void conformidad(A a, B b, C c) {
		
	}

	
}

class TestTipos {
	
	int i;
	char c;
	boolean t;
	boolean f;
	Object o;
	String s;
	A a;
	
	void test() {
		i = 10;
		c = 'd';
		t = true;
		f = false;
		o = new Object();
		s = new String();
		s = (("Hola"));
		a = new A();
	}
	
	void expUnarias() {
		var x = 2;
		var y = +3;
		var z = -3;
		x = y + 3 - z;
		var w = !false;
	}
	
	void expBinarias() {
		var x = 2 + 3;
		var y = 2 * 3;
		var z = x / y;
		z = x % y;
		var w = true || false;
		var v = w && true;
		v = x > y;
		v = x >= y;
		v = x < y;
		v = x <= y;
	}
	
	void testSentencias() {
		;
		i = 10;
		i += 2;
		i -= 3;
		t = f;
		a.num = 10;
	}
	
	void testIf() {
		if (t) {
			expBinarias();
		}
		else {
			expUnarias();
		}
		
		if (f) {
			testSentencias();
		}
	}
	
	void testWhile() {
		while (t && f) {
			testIf();
			break;
		}
	}
	
	void testSwitch() {
		switch (i) {
		case 1:
		case 2:
		{
			testWhile();
			break;
		}
		case 3:
			return;
		case 4:
			testIf();
		default:
			testSentencias();
		}
	}
	
	Object testReturn() {
		return new Object();
	}
	
	A testA() {
		var objA = new A();
		
		return objA;
	}
	
	int getNum() {
		return i;
	}
	
}

class Init {

	static void main() {
		
	}
}