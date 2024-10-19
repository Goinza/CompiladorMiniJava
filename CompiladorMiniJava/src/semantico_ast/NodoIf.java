package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;

public class NodoIf extends NodoSentencia {
	
	private NodoExpresion condicion;
	private NodoSentencia sentenciaThen;
	
	public NodoIf(Token token) {
		this.token = token;
	}
	
	public void setCondicion(NodoExpresion exp) {
		condicion = exp;
	}
	
	public void setBloqueThen(NodoSentencia sentencia) {
		sentenciaThen = sentencia;
	}

	@Override
	public void chequear() throws ExcepcionSemantica {
		Tipo tipoCondicion = condicion.chequear().getTipo();
		if (tipoCondicion.getNombre().equals("boolean")) {
			sentenciaThen.chequear();
		}
		else {
			throw new ExcepcionSemantica(token, "La condición debe ser de tipo booleano.");
		}
	}

}
