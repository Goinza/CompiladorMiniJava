package semantico_ast;

import main.Token;

public class NodoReturn extends NodoSentencia {
	
	private NodoExpresion retorno;
	
	public NodoReturn(Token token) {
		this.token = token;
	}
	
	public void setRetorno(NodoExpresion exp) {
		retorno = exp;
	}

	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
