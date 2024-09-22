///[SinErrores]
///Todas las pruebas de casos sin errores que hice estan en este archivo

class Abstract {
	
}

class Example extends Abstract {
	
	int a;
	String b;
	AnotherExample c;
	static boolean d;
	
	public Example(AnotherExample ae) {
		c = ae;
		a = 1;
		b = "Hola";
	}
	
	int getA() {
		return a;
	}
	
	void setA(int a) {
		this.a = a;
	}
	
	void setAll(int a, String b) {
		this.a = a;
		this.b = b;
	}
	
	void writeB() {
		Console.print(b);
	}
	
	AnotherExample resetC() {
		var aux = c;
		c = null;		
		return aux;
	}
	
	void duplicateA() {
		Console.print("Before: " + a);
		a = a * 2;
		Console.print("After: " + a);
	}
	
	int llamadaEncadenada() {
		var val = System.getIntObject().getValue();
		
		return val;
	}
	
	void testOperations() {
		var aux = 2 + 3;
		var aux2 = aux1 * 5;
		var aux3 = aux == 0;
		var aux4 = true && !aux3 || auxNoExiste;
		var aux5 = aux2 != aux3 || aux2 <= aux4 && aux5 >= 2 && (aux1 > aux1 || aux2 < aux2);
		var aux6 = (25 - aux2) / (25 % aux2);
		aux1 += 2;
		aux1 = 7;
		aux1 -= aux;
		aux2 = +5;
		aux2 = -2;
		aux3 = !1;
		aux4 = new Object() + 5;
		
		return;
	}
	
	void testSwitch(int flag) {
		switch (flag) {
			case 1: {
				something();
				break;
			}
			case 2:
			case 3:
				System.print("something else");
			default:
				def(flag);
		}
	}
	
	void loop(int count) {
		var i = 0;
		while (i<count) {
			doSomething(i);
			i = i + 1;
		}
		
		return 0;
	}
	
	boolean testIf(boolean cond1, boolean cond2) {
		var toReturn = false;
		if (cond1) {
			do1();
			toReturn = true;
		}
		else if (cond2) {
			do2();
			toReturn = true;
		}
		else {
			doNothing();
		}
		
		return toReturn;
	}
	
	static void systemOutPrint(String message) {		
		System.getOutStream().println(message);
	}

	void callFunctions() {
		call1(5 + 3, new Object("id1"), true, !aux);
		call2(8, obj);
	}
	
	void encadenados(Object obj1, Object obj2) {
		obj1.getClass().toString().chars().close().something.doSomething().getResult().data;
	}
	
}