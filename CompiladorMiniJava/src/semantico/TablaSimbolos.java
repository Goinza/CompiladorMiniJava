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
		if (TablaSimbolos.ts == null) {
			TablaSimbolos.ts = new TablaSimbolos();
		}
		
		return TablaSimbolos.ts;
	}
	
	public static void inicializarTabla() throws ExcepcionSemantica {
		TablaSimbolos.ts = new TablaSimbolos();
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
	
	public void verificarDeclaracion() throws ExcepcionSemantica {
		for (Clase c : clases.values()) {
			c.verificarDeclaracion();
		}
	}
	
	public void consolidar() throws ExcepcionSemantica {
		for (Clase c : clases.values()) {
			if (!c.estaConsolidada()) {
				c.consolidar();				
			}
		}
	}
	
	private void agregarClasesPredefinidas() throws ExcepcionSemantica {
		Clase [] predefinidas = {new ClaseObject(), new ClaseString(), new ClaseSystem()};
		for (Clase c : predefinidas) {
			agregarClase(c);
		}
	}

}
