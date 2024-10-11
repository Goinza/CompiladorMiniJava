package semantico_ast;

import main.Token;

public class NodoVarLocal extends NodoSentencia {
	
	private NodoVariable ladoIzquierdo;
	private NodoExpCompuesta ladoDerecho;
	
	public NodoVarLocal(Token token) {
		this.token = token;
	}
	
	public void setLadoIzquierdo(NodoVariable var) {
		ladoIzquierdo = var;
	}
	
	public void setLadoDerecho(NodoExpCompuesta exp) {
		ladoDerecho = exp;
	}

	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
