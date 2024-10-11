package semantico_ast;

import main.Token;

public class NodoAsignacion extends NodoSentencia {
	
	private NodoExpAsignacion asignacion;
	
	public NodoAsignacion(Token token) {
		this.token = token;
	}
	
	public void setAsignacion(NodoExpAsignacion exp) {
		asignacion = exp;
	}

	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
