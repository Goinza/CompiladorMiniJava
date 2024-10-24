///[Error:this|9]
// Usar this en un metodo estatico

class C {
	
	int a;
	
	static void metodoEstatico() {
		this.metodo();
	}
	
	void metodo() {
		
	}
	
}

class Init {

	static void main() {
		
	}
}