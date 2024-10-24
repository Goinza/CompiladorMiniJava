package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;

public abstract class NodoEncadenado {

	protected Token token;
	protected NodoEncadenado encadenado;
	
	public void setEncadenado(NodoEncadenado encadenado) {
		this.encadenado = encadenado;
	}
	
	public abstract InfoCheck chequear(Tipo t) throws ExcepcionSemantica;
	
}
