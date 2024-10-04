package semantico_ts;

import main.Token;

public class TipoCaracter extends TipoPrimitivo {

	public TipoCaracter(Token token) {
		this.token = token;
		this.nombre = token.getLexema();
	}
	
	public TipoCaracter() {
		this.nombre = "char";
	}
	
}
