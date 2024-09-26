package semantico;

import java.util.HashMap;
import java.util.Map;

import main.Token;

public class Constructor extends EntidadDeclarada implements EntidadLlamable {

	private Map<String, Parametro> parametros;
	
	public Constructor(Token token) {
		this.token = token;
		this.nombre = token.getLexema();
		parametros = new HashMap<String, Parametro>();
	}
	
	public Constructor(String nombre) {
		this.nombre = nombre;
		parametros = new HashMap<String, Parametro>();
	}
	
	public Map<String, Parametro> getParametros() {
		return parametros;
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
