package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import traduccion.GeneradorCodigo;

public class NodoSentExpresion extends NodoSentencia {
	
	private NodoExpresion exp;
	private boolean tieneRetorno;
	
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
		tieneRetorno = !infoExp.getTipo().getNombre().equals("void") && exp instanceof NodoAcceso;
	}

	@Override
	public void generarCodigo() {
		exp.generarCodigo();	
		if (tieneRetorno) {
			GeneradorCodigo.generarInstruccion("POP", "Descartar retorno");
		}
	}

}
