package traduccion;

import semantico_ast.NodoBloque;
import semantico_ts.ExcepcionSemantica;

public class BloquePrintBLn extends NodoBloque {
	
	public BloquePrintBLn() throws ExcepcionSemantica {
		super(null);
	}
	
	public void generarCodigo() {
		GeneradorCodigo.generarInstruccion("LOAD 3", "Apila el parámetro");
		GeneradorCodigo.generarInstruccion("BPRINT", "Imprime el boolean en el tope de la pila");
		GeneradorCodigo.generarInstruccion("PRNLN", "Imprime el caracter de nueva línea");
	}

}
