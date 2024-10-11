package semantico_ast;

import main.Token;

public class NodoDefaultSwitch extends NodoSentencia {
	
	private NodoBloque bloque;
	
	public NodoDefaultSwitch(Token token) {
		this.token = token;
	}
	
	public void setBloque(NodoBloque bloque) {
		this.bloque = bloque;
	}

	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
