///[SinErrores]

class Example extends Abstract {
	
	int a;
	String b;
	AnotherExample c;
	
	public Example(AnotherExample ae) {
		c = ae;
		a = 1;
		b = "Hola";
	}
	
	int getA() {
		return a;
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
	}
	
	static void systemOutPrint(String message) {		
		System.getOutStream().println(message);
	}
	
	
}