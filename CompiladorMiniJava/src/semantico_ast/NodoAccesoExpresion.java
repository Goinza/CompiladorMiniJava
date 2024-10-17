package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public class NodoAccesoExpresion extends NodoAcceso {
	
	private NodoExpresion exp;
	
	public NodoAccesoExpresion(Token token, NodoExpresion exp) {
		this.token = token;
		this.exp = exp;
	}

	@Override
	public Tipo chequear() {
		// TODO Auto-generated method stub
		return null;
	}

}
