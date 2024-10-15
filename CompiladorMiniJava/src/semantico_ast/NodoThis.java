package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public class NodoThis extends NodoAcceso {
	
	public NodoThis(Token token) {
		this.token = token;
	}

	@Override
	public Tipo chequear() {
		// TODO Auto-generated method stub
		return null;
	}

}
