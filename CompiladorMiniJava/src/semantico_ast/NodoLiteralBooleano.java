package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public class NodoLiteralBooleano extends NodoOperando {
	
	public NodoLiteralBooleano(Token token) {
		this.token = token;
	}

	@Override
	public Tipo chequear() {
		// TODO Auto-generated method stub
		return null;
	}

}
