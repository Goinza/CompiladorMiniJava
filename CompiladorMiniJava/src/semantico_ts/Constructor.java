package semantico_ts;

import java.util.ArrayList;
import java.util.HashMap;

import main.Token;
import traduccion.Etiquetable;
import traduccion.GeneradorCodigo;

public class Constructor extends EntidadLlamable implements Etiquetable {
	
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
		GeneradorCodigo.generarInstruccionEtiquetada(getEtiqueta(), "LOADFP", "Apila el valor del registro FP");
		GeneradorCodigo.generarInstruccion("LOADSP", "Apila el valor del registro FP");
		GeneradorCodigo.generarInstruccion("STOREFP", "Apila el valor del registro FP");
		
		bloquePrincipal.generarCodigo();
		
		GeneradorCodigo.generarInstruccion("STOREFP", "Almacena el tope de la pila en el registro");
		int cantParametros = listaParametros.size() + 1;
		GeneradorCodigo.generarInstruccion("RET " + cantParametros, null);
	}

	@Override
	public String getEtiqueta() {
		return "lblConstructor@" + nombre;
	}
	
}
