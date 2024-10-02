package semantico;

import main.Token;

public class Parametro extends EntidadDeclarada {

	private Tipo tipo;
	
	public Parametro(Token token, Tipo tipo) {
		this(token.getLexema(), tipo);
		this.token = token;
	}
	
	public Parametro(String nombre, Tipo tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
	}
	
	public Tipo getTipo() {
		return tipo;
	}

	public void verificarDeclaracion() throws ExcepcionSemantica {
		if (tipo.getNombre().equals("void")) {
			throw new ExcepcionSemantica(tipo.getToken(), "Los parámetros no pueden tener tipo void");
		}
		tipo.verificarDeclaracion();
	}
	
	public boolean equals(Parametro p) {
		return nombre.equals(p.getNombre()) && tipo.equals(p.getTipo());
	}
	
}
