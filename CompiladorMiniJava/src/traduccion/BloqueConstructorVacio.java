package traduccion;

import semantico_ast.NodoBloque;
import semantico_ts.ExcepcionSemantica;

public class BloqueConstructorVacio extends NodoBloque {

	public BloqueConstructorVacio() throws ExcepcionSemantica {
		super(null);
	}
	
	public void generarCodigo() {
	}

}
