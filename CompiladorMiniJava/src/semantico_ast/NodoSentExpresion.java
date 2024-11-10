package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;

public class NodoSentExpresion extends NodoSentencia {
	
	private NodoExpresion exp;
	
	public NodoSentExpresion(Token token, NodoExpresion exp) {
		this.token = token;
		this.exp = exp;
	}

	@Override
	public void chequear() throws ExcepcionSemantica {
		InfoCheck infoExp = exp.chequear();
		if (!infoExp.esSentencia()) {
			throw new ExcepcionSemantica(token, "La expresión debe ser una llamada o una asignación.");
		}
	}

	@Override
	public void generarCodigo() {
		exp.generarCodigo();		
	}

}
