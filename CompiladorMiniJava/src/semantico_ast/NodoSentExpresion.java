package semantico_ast;

import main.Token;

public class NodoSentExpresion extends NodoSentencia {
	
	private NodoExpresion exp;
	
	public NodoSentExpresion(Token token, NodoExpresion exp) {
		this.token = token;
		this.exp = exp;
	}

	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
