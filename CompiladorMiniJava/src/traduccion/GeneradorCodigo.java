package traduccion;

public class GeneradorCodigo {
	
	public static void openFile(String pathname) {
		
	}
	
	public static void closeFile() {
		
	}
	
	//El comentario puede ser nulo
	public static void generarInstruccion(String instruccion, String comentario) {
		String linea = instruccion;
		if (comentario != null) {
			linea += " ; " + comentario;
		}
		
		System.out.println(linea);
	}
	
	public static void generarInstruccionEtiquetada(String etiqueta, String instruccion, String comentario) {
		String linea = etiqueta + " : " + instruccion;
		if (comentario != null) {
			linea += " ; " + comentario;
		}
		
		System.out.println(linea);
	}
	
	

}
