package semantico;

import java.util.HashMap;
import java.util.Map;

public class Constructor {

	private String nombre;
	private Map<String, Parametro> parametros;
	
	
	public Constructor(String nombre) {
		this.nombre = nombre;
		parametros = new HashMap<String, Parametro>();
	}
	
	public Constructor(String nombre, Map<String, Parametro> parametros) {
		this.nombre = nombre;
		this.parametros = parametros;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Map<String, Parametro> getParametros() {
		return parametros;
	}
	
	public void agregarParametro(Parametro p) {
		parametros.put(p.getNombre(), p);
	}
	
	public void verificarDeclaracion() {
		
	}
	
}
