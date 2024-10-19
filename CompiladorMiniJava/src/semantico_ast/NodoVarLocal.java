package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;

public class NodoVarLocal extends NodoSentencia {
	
	private NodoExpCompuesta asignacion;
	
	public NodoVarLocal(Token token) {
		this.token = token;
	}
	
	public void setAsignacion(NodoExpCompuesta exp) {
		asignacion = exp;
	}

	@Override
	public void chequear() throws ExcepcionSemantica {
		// TODO Auto-generated method stub

	}

}
