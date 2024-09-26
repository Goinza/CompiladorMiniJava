package semantico;

import main.Token;

public class Parametro extends EntidadDeclarada {

	private Tipo tipo;
	
	public Parametro(Token token, Tipo tipo) {
		this.token = token;
		this.nombre = token.getLexema();
		this.tipo = tipo;
	}
	
	public Parametro(String nombre, Tipo tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
}
