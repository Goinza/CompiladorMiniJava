package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public class NodoVariableEncadenada extends NodoEncadenado {
	
	private NodoEncadenado encadenado;
	
	public NodoVariableEncadenada(Token token) {
		this.token = token;
	}
	
	public void setEncadenado(NodoEncadenado encadenado) {
		this.encadenado = encadenado;
	}

	@Override
	public Tipo chequear(Tipo t) {
		// TODO Auto-generated method stub
		return null;
	}

}
