package semantico_ast;

import main.Token;

public class NodoIfElse extends NodoSentencia {
	
	private NodoExpCompuesta condicion;
	private NodoBloque bloqueThen;
	private NodoBloque bloqueElse;

	public NodoIfElse(Token token) {
		this.token = token;
	}
	
	public void setCondicion(NodoExpCompuesta exp) {
		condicion = exp;
	}
	
	public void setBloqueThen(NodoBloque bloque) {
		bloqueThen = bloque;
	}
	
	public void setBloqueElse(NodoBloque bloque) {
		bloqueElse = bloque;
	}
	
	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
