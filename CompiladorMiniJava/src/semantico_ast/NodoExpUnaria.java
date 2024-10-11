package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public class NodoExpUnaria extends NodoExpCompuesta {
	
	private NodoExpCompuesta operando;
	
	public NodoExpUnaria(Token token ) {
		this.token = token;
	}
	
	public void setOperando(NodoExpCompuesta exp) {
		operando = exp;
	}

	@Override
	public Tipo chequear() {
		// TODO Auto-generated method stub
		return null;
	}

}
