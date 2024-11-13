package traduccion;

import semantico_ast.NodoBloque;
import semantico_ts.ExcepcionSemantica;

public class BloquePrintCLn extends NodoBloque {
	
	public BloquePrintCLn() throws ExcepcionSemantica {
		super(null);
	}
	
	public void generarCodigo() {
		GeneradorCodigo.generarInstruccion("LOAD 3", "Apila el parámetro");
		GeneradorCodigo.generarInstruccion("CPRINT", "Imprime el char en el tope de la pila");
		GeneradorCodigo.generarInstruccion("PRNLN", "Imprime el caracter de nueva línea");
	}

}
