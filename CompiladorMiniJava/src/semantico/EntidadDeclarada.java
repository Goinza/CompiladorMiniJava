package semantico;

import main.Token;

public abstract class EntidadDeclarada {
	
	protected Token token;
	protected String nombre;
	
	public Token getToken() {
		return token;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setToken(Token token) {
		this.token = token;
	}

}
