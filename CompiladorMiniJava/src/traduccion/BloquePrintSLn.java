package traduccion;

import semantico_ast.NodoBloque;
import semantico_ts.ExcepcionSemantica;

public class BloquePrintSLn extends NodoBloque {

	public BloquePrintSLn() throws ExcepcionSemantica {
		super(null);
	}
	
	public void generarCodigo() {
		GeneradorCodigo.generarInstruccion("LOAD 3", "Apila el parámetro");
		GeneradorCodigo.generarInstruccion("SPRINT", "Imprime el string en el tope de la pila");
		GeneradorCodigo.generarInstruccion("PRNLN", "Imprime el caracter de nueva línea");
	}
	
}
