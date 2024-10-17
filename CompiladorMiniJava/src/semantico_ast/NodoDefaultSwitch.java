package semantico_ast;

import main.Token;

public class NodoDefaultSwitch {
	
	private Token token;
	private NodoSentencia sentencia;
	
	public NodoDefaultSwitch(Token token, NodoSentencia sentencia) {
		this.token = token;
		this.sentencia = sentencia;
	}
	
	public Token getToken() {
		return token;
	}

	public void chequear() {
		// TODO Auto-generated method stub

	}

}
