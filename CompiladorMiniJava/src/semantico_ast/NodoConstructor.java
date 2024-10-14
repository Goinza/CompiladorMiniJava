package semantico_ast;

import java.util.LinkedList;
import java.util.List;

import main.Token;
import semantico_ts.Tipo;

public class NodoConstructor extends NodoAcceso {
	
	private NodoEncadenado encadenado;
	private List<NodoExpCompuesta> parametros;
	
	public NodoConstructor(Token token) {
		this.token = token;
		parametros = new LinkedList<NodoExpCompuesta>();
	}
	
	public void setEncadenado(NodoEncadenado encadenado) {
		this.encadenado = encadenado;
	}
	
	public void agregarParametro(NodoExpCompuesta exp) {
		parametros.addLast(exp);
	}

	@Override
	public Tipo chequear() {
		// TODO Auto-generated method stub
		return null;
	}

}
