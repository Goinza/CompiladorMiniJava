package semantico_ast;

import main.Token;

public class NodoVarLocal extends NodoSentencia {
	
	private NodoExpCompuesta asignacion;
	
	public NodoVarLocal(Token token) {
		this.token = token;
	}
	
	public void setAsignacion(NodoExpCompuesta exp) {
		asignacion = exp;
	}

	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
