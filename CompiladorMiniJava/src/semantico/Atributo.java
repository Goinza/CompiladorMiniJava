package semantico;

import main.Token;

public class Atributo extends EntidadDeclarada {

	private Tipo tipo;
	private boolean esEstatico;
	
	public Atributo(Token token, Tipo tipo, boolean esEstatico) {
		this(token.getLexema(), tipo, esEstatico);
		this.token = token;		
	}
	
	public Atributo(String nombre, Tipo tipo, boolean esEstatico) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.esEstatico = esEstatico;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public boolean esEstatico() {
		return esEstatico;
	}

	public void verificarDeclaracion() throws ExcepcionSemantica {
		if (tipo.getNombre().equals("void")) {
			throw new ExcepcionSemantica(tipo.getToken(), "Los atributos no pueden tener tipo void");
		}
		tipo.verificarDeclaracion();
	}
	
}
