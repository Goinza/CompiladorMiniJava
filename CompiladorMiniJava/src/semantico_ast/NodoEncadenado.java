package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public abstract class NodoEncadenado {

	protected Token token;
	
	public abstract Tipo chequear(Tipo t);
	
}
