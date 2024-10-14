package semantico_ast;

import main.Token;

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
	}
	
	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
