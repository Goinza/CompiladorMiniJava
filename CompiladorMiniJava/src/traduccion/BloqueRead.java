package traduccion;

import semantico_ast.NodoBloque;
import semantico_ts.ExcepcionSemantica;

public class BloqueRead extends NodoBloque {
	
	public BloqueRead() throws ExcepcionSemantica {
		super(null);
	}
	
	public void generarCodigo() {
		GeneradorCodigo.generarInstruccion("READ", "Lee un valor entero");
		GeneradorCodigo.generarInstruccion("PUSH 48", "Por ASCII");
		GeneradorCodigo.generarInstruccion("SUB", null);
		GeneradorCodigo.generarInstruccion("STORE 3", "Devuelve el valor entero leído, poniendo el tope de la pila en la locación reservada");
	}

}
