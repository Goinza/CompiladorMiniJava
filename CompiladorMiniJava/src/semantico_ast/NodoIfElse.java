package semantico_ast;

import main.Token;

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
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
