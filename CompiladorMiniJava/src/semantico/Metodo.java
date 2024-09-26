package semantico;

import java.util.HashMap;
import java.util.Map;

import main.Token;

public class Metodo extends EntidadDeclarada implements EntidadLlamable {
	
	private Tipo tipoRetorno;
	private Map<String, Parametro> parametros;
	private boolean esEstatico;
	
	public Metodo(Token token, Tipo tipoRetorno, boolean esEstatico) {
		this.token = token;
		this.nombre = token.getLexema();
		this.tipoRetorno = tipoRetorno;
		parametros = new HashMap<String, Parametro>();
		this.esEstatico = esEstatico;
	}
	
	public Metodo(String nombre, Tipo tipoRetorno, boolean esEstatico) {
		this.nombre = nombre;
		this.tipoRetorno = tipoRetorno;
		parametros = new HashMap<String, Parametro>();
		this.esEstatico = esEstatico;
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
	
	public void agregarParametro(Parametro p) throws ExcepcionSemantica {
		if (parametros.get(p.getNombre()) != null) {
			throw new ExcepcionSemantica(p.getToken(), "El parámetro " + p.getNombre() + " está repetido.");
		}
		parametros.put(p.getNombre(), p);
	}
	
	public void verificarDeclaracion() {
		
	}

}
