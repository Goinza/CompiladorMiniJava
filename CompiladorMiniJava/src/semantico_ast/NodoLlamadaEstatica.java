package semantico_ast;

import java.util.LinkedList;
import java.util.List;

import main.Token;
import semantico_ts.Tipo;

public class NodoLlamadaEstatica extends NodoAcceso {
	
	private Token tokenClase;
	private NodoEncadenado encadenado;
	private List<NodoExpCompuesta> parametros;
	
	public NodoLlamadaEstatica(Token token, Token tokenClase) {
		this.token = token;
		this.tokenClase = tokenClase;
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
