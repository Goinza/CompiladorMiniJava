package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;

public class NodoBreak extends NodoSentencia {
		
	public NodoBreak(Token token) {
		this.token = token;	
	}

	@Override
	public void chequear() throws ExcepcionSemantica {
		if (!admiteBreak) {
			throw new ExcepcionSemantica(token, "La palabra break solo puede usarse dentro de un bloque while o un bloque switch.");
		}
	}

	@Override
	public void generarCodigo() {
		// TODO Auto-generated method stub
		
	}

}
