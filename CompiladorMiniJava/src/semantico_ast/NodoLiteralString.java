package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public class NodoLiteralString extends NodoLiteral {
	
	public NodoLiteralString(Token token) {
		this.token = token;
	}

	@Override
	public Tipo chequear() {
		// TODO Auto-generated method stub
		return null;
	}

}
