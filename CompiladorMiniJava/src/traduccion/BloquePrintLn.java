package traduccion;

import semantico_ast.NodoBloque;
import semantico_ts.ExcepcionSemantica;

public class BloquePrintLn extends NodoBloque {
	
	public BloquePrintLn() throws ExcepcionSemantica {
		super(null);
	}
	
	public void generarCodigo() {
		GeneradorCodigo.generarInstruccion("PRNLN", "Imprime el caracter de nueva línea");
	}

}
