package sintactico;

import main.Token;

public class ExcepcionSintactica extends Exception {
	
	private Token tokenActual;
	private String tokensEsperados;
	
	public ExcepcionSintactica(Token tokenActual, String tokensEsperados) {
		this.tokenActual = tokenActual;
		this.tokensEsperados = tokensEsperados;
	}

	public String getMessage() {
		return "Error sintáctico en línea " + tokenActual.getLinea() + ": se encontro " + tokenActual.getLexema() + " cuando se esperaba un " + tokensEsperados;
	}
	
	public String getErrorCode() {
		return "[Error:" + tokenActual.getLexema() + "|" + tokenActual.getLinea() + "]";
	}
	
	public Token getTokenActual() {
		return tokenActual;
	}

}
