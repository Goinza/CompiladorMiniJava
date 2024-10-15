package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public class NodoVariableEncadenada extends NodoEncadenado {
	
	public NodoVariableEncadenada(Token token) {
		this.token = token;
	}

	@Override
	public Tipo chequear(Tipo t) {
		// TODO Auto-generated method stub
		return null;
	}

}
