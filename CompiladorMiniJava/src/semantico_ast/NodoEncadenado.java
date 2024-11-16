package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;

public abstract class NodoEncadenado {

	protected Token token;
	protected NodoEncadenado encadenado;
	protected boolean esLadoIzqAsign = false;
	
	public void setEncadenado(NodoEncadenado encadenado) {
		this.encadenado = encadenado;
	}
	
	public abstract InfoCheck chequear(Tipo t) throws ExcepcionSemantica;
	
	public abstract void generarCodigo();
	
	public boolean esLadoIzquierdoAsignacion() {
		return esLadoIzqAsign;
	}
	
	public void setEsLadoIzquierdoAsignacion() {
		esLadoIzqAsign = true;
	}
	
}
