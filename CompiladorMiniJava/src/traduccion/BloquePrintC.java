package traduccion;

import semantico_ast.NodoBloque;
import semantico_ts.ExcepcionSemantica;

public class BloquePrintC extends NodoBloque {

	public BloquePrintC() throws ExcepcionSemantica {
		super(null);
	}
	
	public void generarCodigo() {
		GeneradorCodigo.generarInstruccion("LOAD 3", "Apila el parámetro");
		GeneradorCodigo.generarInstruccion("CPRINT", "Imprime el char en el tope de la pila");
	}
	
}
