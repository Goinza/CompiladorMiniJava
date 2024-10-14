package semantico_ast;

import main.Token;

public class NodoIf extends NodoSentencia {
	
	private NodoExpresion condicion;
	private NodoSentencia bloqueThen;
	
	public NodoIf(Token token) {
		this.token = token;
	}
	
	public void setCondicion(NodoExpresion exp) {
		condicion = exp;
	}
	
	public void setBloqueThen(NodoSentencia bloque) {
		bloqueThen = bloque;
	}

	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
