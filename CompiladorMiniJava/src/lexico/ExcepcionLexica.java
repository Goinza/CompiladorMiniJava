package lexico;

public class ExcepcionLexica extends Exception {
	
	private String lexema;
	private int linea;
	private String mensaje;
	
	public ExcepcionLexica(String lexema, int linea, String mensaje) {
		this.lexema = lexema;
		this.linea = linea;
		this.mensaje = mensaje;
	}

	public String getMessage() {
		return "Error léxico con el lexema " + lexema + " en linea " + linea + " - " + mensaje;
	}
	
	public String getErrorCode() {
		return "[Error:" + lexema + "|" + linea + "]";
	}

}
