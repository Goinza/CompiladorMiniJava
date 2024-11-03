package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;

public abstract class NodoExpresion {
	
	protected Token token;
	
	public Token getToken() {
		return token;
	}
	
	public abstract InfoCheck chequear() throws ExcepcionSemantica;
	
	public abstract void generarCodigo();

}
