package semantico_ast;

import main.Token;

public class NodoIf extends NodoSentencia {
	
	private NodoExpCompuesta condicion;
	private NodoBloque bloqueThen;
	
	public NodoIf(Token token) {
		this.token = token;
	}
	
	public void setCondicion(NodoExpCompuesta exp) {
		condicion = exp;
	}
	
	public void setBloqueThen(NodoBloque bloque) {
		bloqueThen = bloque;
	}

	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
