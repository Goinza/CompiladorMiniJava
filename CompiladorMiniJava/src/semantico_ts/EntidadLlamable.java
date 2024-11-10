package semantico_ts;

import java.util.List;
import java.util.Map;

import semantico_ast.NodoBloque;

public abstract class EntidadLlamable extends EntidadDeclarada {
	
	protected Map<String, Parametro> parametros;
	protected List<Parametro> listaParametros;
	protected NodoBloque bloquePrincipal;
	
	public Iterable<Parametro> getParametros() {
		return parametros.values();
	}
	
	public Parametro getParametro(String nombre) {
		return parametros.get(nombre);
	}
	
	public List<Parametro> getListaParametros() {
		return listaParametros;
	}
	
	public void agregarParametro(Parametro p) throws ExcepcionSemantica {
		if (parametros.get(p.getNombre()) != null) {
			throw new ExcepcionSemantica(p.getToken(), "El parámetro " + p.getNombre() + " está repetido.");
		}
		parametros.put(p.getNombre(), p);
		listaParametros.addLast(p);
	}
	
	public void setBloquePrincipal(NodoBloque bloque) {
		bloquePrincipal = bloque;
	}
	
	public abstract void generarCodigo();

}
