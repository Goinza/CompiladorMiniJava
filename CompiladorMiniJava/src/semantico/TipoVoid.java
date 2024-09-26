package semantico;

import main.Token;

public class TipoVoid extends Tipo {
	
	public TipoVoid(Token token) {
		this.token = token;
		this.nombre = token.getLexema();
	}
	
	public TipoVoid() {		
		this.nombre = "void";
	}

}
