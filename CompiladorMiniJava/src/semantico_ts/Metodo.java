package semantico_ts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Token;

public class Metodo extends EntidadDeclarada implements EntidadLlamable {
	
	private Tipo tipoRetorno;
	private Map<String, Parametro> parametros;
	private List<Parametro> listaParametros;
	private boolean esEstatico;
	
	public Metodo(Token token, Tipo tipoRetorno, boolean esEstatico) {
		this(token.getLexema(), tipoRetorno, esEstatico);
		this.token = token;
	}
	
	public Metodo(String nombre, Tipo tipoRetorno, boolean esEstatico) {
		this.nombre = nombre;
		this.tipoRetorno = tipoRetorno;
		parametros = new HashMap<String, Parametro>();
		listaParametros = new ArrayList<Parametro>();
		this.esEstatico = esEstatico;
	}
	
	public Tipo getTipoRetorno() {
		return tipoRetorno;
	}
	
	public Iterable<Parametro> getParametros() {
		return parametros.values();
	}
	
	public Parametro getParametro(String nombre) {
		return parametros.get(nombre);
	}
	
	public List<Parametro> getListaParametros() {
		return listaParametros;
	}
	
	public boolean esEstatico() {
		return esEstatico;
	}
	
	public void agregarParametro(Parametro p) throws ExcepcionSemantica {
		if (parametros.get(p.getNombre()) != null) {
			throw new ExcepcionSemantica(p.getToken(), "El parámetro " + p.getNombre() + " está repetido.");
		}
		parametros.put(p.getNombre(), p);
		listaParametros.addLast(p);
	}
	
	public void verificarDeclaracion() throws ExcepcionSemantica {
		tipoRetorno.verificarDeclaracion();
		
		for (Parametro p : parametros.values()) {
			p.verificarDeclaracion();
		}
	}
	
	public boolean equals(Metodo m) {
		boolean isEquals = false;
		List<Parametro> otraLista = m.getListaParametros();
		int count = listaParametros.size();
		if (nombre.equals(m.getNombre()) && count == otraLista.size() && tipoRetorno.equals(m.getTipoRetorno()) && esEstatico == m.esEstatico()) {
			isEquals = true;
			int i = 0;
			while (i<count && isEquals) {
				isEquals = listaParametros.get(i).equals(otraLista.get(i));
				i++;
			}
			
		}	
		
		return isEquals;
	}

}
