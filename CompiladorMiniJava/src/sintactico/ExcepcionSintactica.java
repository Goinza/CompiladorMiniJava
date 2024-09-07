package sintactico;

public class ExcepcionSintactica extends Exception {
	
	private String lexema;
	private int linea;
	
	public ExcepcionSintactica() {
		super();
	}

	public String getMessage() {
		return "TO DO";
	}
	
	public String getErrorCode() {
		return "[Error:" + lexema + "|" + linea + "]";
	}

}
