package main;

public class ExcepcionLexica extends Exception {
	
	private String lexema;
	private int linea;
	
	public ExcepcionLexica(String lexema, int linea) {
		this.lexema = lexema;
		this.linea = linea;
	}

	public String getMessage() {
		return "Error léxico en linea " + linea + ": " + lexema + " no es un símbolo válido";
	}
	
	public String getErrorCode() {
		return "[Error:" + lexema + "|" + linea + "]";
	}

}
