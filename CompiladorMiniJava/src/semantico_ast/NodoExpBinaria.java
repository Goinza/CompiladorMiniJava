package semantico_ast;

import main.Token;
import semantico_ts.Tipo;

public class NodoExpBinaria extends NodoExpCompuesta {
	
	private NodoExpCompuesta ladoIzquierdo;
	private NodoExpCompuesta ladoDerecho;
	
	public NodoExpBinaria(Token token) {
		this.token = token;
	}
	
	public void setLadoIzquierdo(NodoExpCompuesta exp) {
		ladoIzquierdo = exp;
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
