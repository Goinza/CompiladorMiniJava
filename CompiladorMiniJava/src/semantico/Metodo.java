package semantico;

import java.util.HashMap;
import java.util.Map;

public class Metodo {
	
	private String nombre;
	private Tipo tipoRetorno;
	private Map<String, Parametro> parametros;
	private boolean esEstatico;
	
	public Metodo(String nombre, Tipo tipoRetorno, boolean esEstatico) {
		this.nombre = nombre;
		this.tipoRetorno = tipoRetorno;
		parametros = new HashMap<String, Parametro>();
		this.esEstatico = esEstatico;
	}
	
	public Metodo(String nombre, Tipo tipoRetorno, Map<String, Parametro> parametros) {
		this.nombre = nombre;
		this.tipoRetorno = tipoRetorno;
		this.parametros = parametros;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Tipo getTipoRetorno() {
		return tipoRetorno;
	}
	
	public Map<String, Parametro> getParametros() {
		return parametros;
	}
	
	public boolean esEstatico() {
		return esEstatico;
	}
	
	public void agregarParametro(Parametro p) {
		parametros.put(p.getNombre(), p);
	}
	
	public void verificarDeclaracion() {
		
	}

}
