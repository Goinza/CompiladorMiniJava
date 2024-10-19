package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;

public abstract class NodoSentencia {
	
	protected Token token;
	
	public Token getToken() {
		return token;
	}
	
	public abstract void chequear() throws ExcepcionSemantica;

}
