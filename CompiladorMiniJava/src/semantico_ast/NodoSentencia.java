package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;

public abstract class NodoSentencia {
	
	protected Token token;
	protected boolean admiteBreak = false;
	
	public Token getToken() {
		return token;
	}
	
	public abstract void chequear() throws ExcepcionSemantica;
	
	public boolean admiteBreak() {
		return admiteBreak;
	}
	
	public void setBreak(boolean admiteBreak) {
		this.admiteBreak = admiteBreak;
	}

}
