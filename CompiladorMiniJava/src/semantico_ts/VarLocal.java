package semantico_ts;

import main.Token;

public class VarLocal {

	private Token token;
	private Tipo tipo;
	
	public VarLocal(Token token, Tipo tipo) {
		this.token = token;
		this.tipo = tipo;
	}
	
	public Token getToken() {
		return token;
	}
	
	public Tipo getTipo() {
		return tipo;
	}

}
