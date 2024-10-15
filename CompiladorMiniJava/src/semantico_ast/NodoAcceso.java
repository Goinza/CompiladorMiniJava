package semantico_ast;


public abstract class NodoAcceso extends NodoOperando {
	
	protected NodoEncadenado encadenado;
	
	public void setEncadenado(NodoEncadenado encadenado) {
		this.encadenado = encadenado;
	}

}
