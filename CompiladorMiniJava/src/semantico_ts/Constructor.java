package semantico_ts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Token;

public class Constructor extends EntidadLlamable {

	private Map<String, Parametro> parametros;
	private List<Parametro> listaParametros;
	
	public Constructor(Token token) {
		this(token.getLexema());
		this.token = token;
	}
	
	public Constructor(String nombre) {
		this.nombre = nombre;
		parametros = new HashMap<String, Parametro>();
		listaParametros = new ArrayList<Parametro>();
	}

	public void verificarDeclaracion() throws ExcepcionSemantica {		
		for (Parametro p : parametros.values()) {
			p.verificarDeclaracion();
		}
	}
	
	public void generarCodigo() {
		//Etiqueta, registro de activacion, actualizacion del FP (similar a metodo?)
		//Usar RET 1 porque tiene "this"
		bloquePrincipal.generarCodigo();
	}
	
}
