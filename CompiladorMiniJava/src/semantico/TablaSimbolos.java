package semantico;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
	
	private Map<String, Clase> clases;
	private Clase claseActual;
	private EntidadLlamable metodoActual;
	static private TablaSimbolos ts;
	
	private TablaSimbolos() throws ExcepcionSemantica {
		clases = new HashMap<String, Clase>();
		this.agregarClasesPredefinidas();
	}
	
	public static TablaSimbolos getTabla() throws ExcepcionSemantica {
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
	
	public void agregarClase(Clase c) throws ExcepcionSemantica {
		if (clases.get(c.getNombre()) != null) {
			throw new ExcepcionSemantica(c.getToken(), "La clase " + c.getNombre() + " está repetida.");
		}
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
	
	private void agregarClasesPredefinidas() throws ExcepcionSemantica {
		Clase [] predefinidas = {new ClaseObject(), new ClaseString(), new ClaseSystem()};
		for (Clase c : predefinidas) {
			clases.put(c.getNombre(), c);
		}
	}

}
