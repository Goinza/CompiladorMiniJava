package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public class NodoVariable extends NodoAcceso {
	
	public NodoVariable(Token token) {
		this.token = token;
	}

	@Override
	public Tipo chequear() {
		// TODO Auto-generated method stub
		return null;
	}

}
