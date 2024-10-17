package semantico_ast;

import main.Token;

public class NodoCaseSwitch {
	
	private Token token;
	private NodoLiteral caso;
	private NodoSentencia sentencia;
	
	public NodoCaseSwitch(Token token, NodoLiteral caso, NodoSentencia sentencia) {
		this.token = token;
		this.caso = caso;
		this.sentencia = sentencia;
	}
	
	public Token getToken() {
		return token;
	}

	public void chequear() {
		// TODO Auto-generated method stub

	}

}
