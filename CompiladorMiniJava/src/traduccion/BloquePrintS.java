package traduccion;

import semantico_ast.NodoBloque;
import semantico_ts.ExcepcionSemantica;

public class BloquePrintS extends NodoBloque {

	public BloquePrintS() throws ExcepcionSemantica {
		super(null);
	}
	
	public void generarCodigo() {
		GeneradorCodigo.generarInstruccion("LOAD 3", "Apila el parámetro");
		GeneradorCodigo.generarInstruccion("SPRINT", "Imprime el string en el tope de la pila");
	}
	
}
