package semantico;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
	
	private Map<String, Clase> clases;
	private Clase claseActual;
	private EntidadLlamable metodoActual;
	static private TablaSimbolos ts;
	
	private TablaSimbolos() {
		clases = new HashMap<String, Clase>();
	}
	
	public static TablaSimbolos getTabla() {
		if (ts == null) {
			ts = new TablaSimbolos();
		}
		
		return ts;
	}
	
	public Map<String, Clase> getClases() {
		return clases;
	}
	
	public Clase getClaseActual() {
		return claseActual;
	}
	
	public EntidadLlamable getMetodoActual() {
		return metodoActual;
	}
	
	public void agregarClase(Clase c) {
		clases.put(c.getNombre(), c);
	}
	
	public void setClaseActual(Clase c) {
		claseActual = c;
	}
	
	public void setMetodoActual(EntidadLlamable m) {
		metodoActual = m;
	}
	
	public void verificarDeclaracion() {
		
	}

}
