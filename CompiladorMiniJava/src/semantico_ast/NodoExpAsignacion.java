package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public class NodoExpAsignacion extends NodoExpresion {
	
	private NodoVariable ladoIzquierdo;
	private NodoExpCompuesta ladoDerecho;
	
	public NodoExpAsignacion(Token token) {
		this.token = token;
	}
	
	public void setLadoIzquierdo(NodoVariable var) {
		ladoIzquierdo = var;
	}
	
	public void setLadoDerecho(NodoExpCompuesta exp) {
		ladoDerecho = exp;
	}

	@Override
	public Tipo chequear() {
		// TODO Auto-generated method stub
		return null;
	}

}
