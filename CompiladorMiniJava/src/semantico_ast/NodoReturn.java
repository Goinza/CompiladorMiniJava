package semantico_ast;

import main.Token;

public class NodoReturn extends NodoSentencia {
	
	private NodoExpCompuesta retorno;
	
	public NodoReturn(Token token) {
		this.token = token;
	}
	
	public void setRetorno(NodoExpCompuesta exp) {
		retorno = exp;
	}

	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
