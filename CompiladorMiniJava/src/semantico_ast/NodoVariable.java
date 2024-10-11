package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public class NodoVariable extends NodoOperando {
	
	private NodoEncadenado encadenado;
	
	public NodoVariable(Token token) {
		this.token = token;
	}
	
	public void setEncadenado(NodoEncadenado encadenado) {
		this.encadenado = encadenado;
	}

	@Override
	public Tipo chequear() {
		// TODO Auto-generated method stub
		return null;
	}

}
