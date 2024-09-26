package semantico;

import main.Token;

public class TipoEntero extends TipoPrimitivo {
	
	public TipoEntero(Token token) {
		this.token = token;
		this.nombre = token.getLexema();
	}
	
	public TipoEntero() {
		this.nombre = "int";
	}

}
