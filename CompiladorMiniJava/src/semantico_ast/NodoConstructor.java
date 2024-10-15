package semantico_ast;

import java.util.LinkedList;
import java.util.List;

import main.Token;
import semantico_ts.Tipo;

public class NodoConstructor extends NodoAcceso {
	
	private List<NodoExpresion> parametros;
	
	public NodoConstructor(Token token) {
		this.token = token;
		parametros = new LinkedList<NodoExpresion>();
	}
	
	public void agregarParametro(NodoExpresion exp) {
		parametros.addLast(exp);
	}
	
	public void setParametros(List<NodoExpresion> lista) {
		parametros = lista;
	}

	@Override
	public Tipo chequear() {
		// TODO Auto-generated method stub
		return null;
	}

}
