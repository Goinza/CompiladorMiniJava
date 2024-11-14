package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;

public class NodoIfElse extends NodoSentencia {
	
	private NodoExpresion condicion;
	private NodoSentencia sentenciaThen;
	private NodoSentencia sentenciaElse;

	public NodoIfElse(Token token) {
		this.token = token;
	}
	
	public void setCondicion(NodoExpresion exp) {
		condicion = exp;
	}
	
	public void setBloqueThen(NodoSentencia sentencia) {
		sentenciaThen = sentencia;
	}
	
	public void setBloqueElse(NodoSentencia sentencia) {
		sentenciaElse = sentencia;
	}
	
	@Override
	public void chequear() throws ExcepcionSemantica {
		Tipo tipoCondicion = condicion.chequear().getTipo();
		if (tipoCondicion.getNombre().equals("boolean")) {
			sentenciaThen.chequear();
			sentenciaElse.chequear();
		}
		else {
			throw new ExcepcionSemantica(token, "La condición debe ser de tipo booleano.");
		}
	}

	@Override
	public void generarCodigo() {
		// TODO Auto-generated method stub
		
	}

}
