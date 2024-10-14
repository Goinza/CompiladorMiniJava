package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public class NodoLiteralEntero extends NodoLiteral {
	
	public NodoLiteralEntero(Token token) {
		this.token = token;
	}

	@Override
	public Tipo chequear() {
		// TODO Auto-generated method stub
		return null;
	}

}
