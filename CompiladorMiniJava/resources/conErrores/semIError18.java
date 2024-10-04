///[Error:B|21]
///Herencia circular
///Como el mapeo de clases en la TS no esta ordenada, no es posible determinar que token causara el error hasta que se ejecute al menos una vez
///En este caso lo detecta cuando realiza la consolidacion de la clase B

class A extends B{
	
	int a;
	boolean b;
	
	static void main() {
		
	}
	
	B f() {
		
	}
	
}

class B extends C {
	
}

class C extends A {
	
}