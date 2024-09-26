package semantico;

import main.Token;

public class Atributo extends EntidadDeclarada {

	private Tipo tipo;
	private boolean esEstatico;
	
	public Atributo(Token token, Tipo tipo, boolean esEstatico) {
		this.token = token;
		this.nombre = token.getLexema();
		this.tipo = tipo;
		this.esEstatico = esEstatico;
	}
	
	public Atributo(String nombre, Tipo tipo, boolean esEstatico) {
		this.nombre = token.getLexema();
		this.tipo = tipo;
		this.esEstatico = esEstatico;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public boolean esEstatico() {
		return esEstatico;
	}
	
}
