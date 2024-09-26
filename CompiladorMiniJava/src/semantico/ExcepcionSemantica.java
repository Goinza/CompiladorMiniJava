package semantico;

import main.Token;

public class ExcepcionSemantica extends Exception {
	
	private Token tokenActual;
	private String mensaje;
	
	public ExcepcionSemantica(Token tokenActual, String mensaje) {
		this.tokenActual = tokenActual;
		this.mensaje = mensaje;
	}

	public String getMessage() {
		return "Error semántico en línea " + tokenActual.getLinea() + " - " + mensaje;
	}
	
	public String getErrorCode() {
		return "[Error:" + tokenActual.getLexema() + "|" + tokenActual.getLinea() + "]";
	}
	
	public Token getTokenActual() {
		return tokenActual;
	}

}
