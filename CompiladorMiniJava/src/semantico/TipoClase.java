package semantico;

import main.Token;

public class TipoClase extends Tipo {
	
	public TipoClase(Token token) {
		this.token = token;
		this.nombre = token.getLexema();
	}
	
	public TipoClase(String nombre) {
		this.nombre = nombre;
	}

}
