package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;

public class NodoWhile extends NodoSentencia {
	
	private NodoExpresion condicion;
	private NodoSentencia sentencia;
	
	public NodoWhile(Token token) {
		this.token = token;
	}

	public void setCondicion(NodoExpresion exp) {
		condicion = exp;
	}
	
	public void setSentencia(NodoSentencia sent) {
		sentencia = sent;
		sentencia.setBreak(true);
	}
	
	@Override
	public void chequear() throws ExcepcionSemantica {
		Tipo tipoCondicion = condicion.chequear().getTipo();
		boolean esBooleano = tipoCondicion.getNombre().equals("boolean");
		if (!esBooleano) {
			throw new ExcepcionSemantica(token, "La expresion debe ser del tipo booleano.");
		}
		sentencia.chequear();
	}

}
