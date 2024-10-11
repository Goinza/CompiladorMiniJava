package semantico_ast;

import main.Token;

public class NodoCaseSwitch extends NodoSentencia {
	
	private NodoOperando caso;
	private NodoBloque bloque;
	
	public NodoCaseSwitch(Token token) {
		this.token = token;
	}
	
	public void setCaso(NodoOperando caso) {
		this.caso = caso;
	}
	
	public void setBloque(NodoBloque bloque) {
		this.bloque = bloque;
	}

	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
