package semantico_ast;

import semantico_ts.Tipo;

public class InfoCheck {

	private Tipo tipo;
	private boolean esAsignable;
	
	public InfoCheck(Tipo tipo, boolean esAsignable) {
		this.tipo = tipo;
		this.esAsignable = esAsignable;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public boolean esAsignable() {
		return esAsignable;
	}
	
}
