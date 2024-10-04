package semantico_ts;

import main.Token;

public class TipoBooleano extends TipoPrimitivo {

	public TipoBooleano(Token token) {
		this.token = token;
		this.nombre = token.getLexema();
	}
	
	public TipoBooleano() {	
		this.nombre = "boolean";
	}
	
}
