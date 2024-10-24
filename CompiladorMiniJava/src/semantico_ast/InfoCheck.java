package semantico_ast;

import semantico_ts.Tipo;

public class InfoCheck {

	private Tipo tipo;
	private boolean esAsignable;
	private boolean esSentencia;
	
	public InfoCheck(Tipo tipo, boolean esAsignable, boolean esSentencia) {
		this.tipo = tipo;
		this.esAsignable = esAsignable;
		this.esSentencia = esSentencia;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public boolean esAsignable() {
		return esAsignable;
	}
	
	public boolean esSentencia() {
		return esSentencia;
	}
	
}
