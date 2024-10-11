package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public abstract class NodoExpresion {
	
	protected Token token;
	
	public abstract Tipo chequear();

}
