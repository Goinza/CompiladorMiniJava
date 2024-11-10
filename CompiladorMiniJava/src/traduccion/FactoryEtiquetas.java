package traduccion;

public class FactoryEtiquetas {
	
	private static int counter = 0;
	
	public static int crearEtiqueta() {
		counter++;
		return counter;
	}

}
