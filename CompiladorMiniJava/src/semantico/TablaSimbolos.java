package semantico;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
	
	private Map<String, Clase> clases;
	private Clase claseActual;
	private Metodo metodoActual;
	
	public TablaSimbolos() {
		clases = new HashMap<String, Clase>();
	}
	
	public Map<String, Clase> getClases() {
		return clases;
	}
	
	public Clase getClaseActual() {
		return claseActual;
	}
	
	public Metodo getMetodoActual() {
		return metodoActual;
	}
	
	public void agregarClase(Clase c) {
		clases.put(c.getNombre(), c);
	}
	
	public void setClaseActual(Clase c) {
		claseActual = c;
	}
	
	public void setMetodoActual(Metodo m) {
		metodoActual = m;
	}
	
	public void verificarDeclaracion() {
		
	}

}
