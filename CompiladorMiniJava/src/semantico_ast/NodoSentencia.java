package semantico_ast;

import main.Token;

public abstract class NodoSentencia {
	
	protected Token token;
	
	public Token getToken() {
		return token;
	}
	
	public abstract void chequear();

}
