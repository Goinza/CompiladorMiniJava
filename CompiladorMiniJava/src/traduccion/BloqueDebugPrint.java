package traduccion;

import semantico_ast.NodoBloque;
import semantico_ts.ExcepcionSemantica;

public class BloqueDebugPrint extends NodoBloque {
	
	public BloqueDebugPrint() throws ExcepcionSemantica {
		super(null);
	}
	
	public void generarCodigo() {
		GeneradorCodigo.generarInstruccion("LOAD 3", "Apila el parámetro");
		GeneradorCodigo.generarInstruccion("IPRINT", "Imprime el entero en el tope de la pila");
	}

}
