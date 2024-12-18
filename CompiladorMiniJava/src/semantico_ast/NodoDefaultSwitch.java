package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;

public class NodoDefaultSwitch {
	
	private Token token;
	private NodoSentencia sentencia;
	
	public NodoDefaultSwitch(Token token, NodoSentencia sentencia) {
		this.token = token;
		this.sentencia = sentencia;
		sentencia.setBreak(true);
	}
	
	public Token getToken() {
		return token;
	}

	public void chequear() throws ExcepcionSemantica {
		sentencia.chequear();
	}
	
	public void generarCodigo() {
		sentencia.generarCodigo();		
	}

}
