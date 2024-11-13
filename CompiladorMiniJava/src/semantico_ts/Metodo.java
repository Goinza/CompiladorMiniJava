package semantico_ts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.Token;
import traduccion.Etiquetable;
import traduccion.GeneradorCodigo;

public class Metodo extends EntidadLlamable implements Etiquetable {
	
	private Tipo tipoRetorno;
	private boolean esEstatico;
	private Clase claseOriginal;
	
	public Metodo(Token token, Tipo tipoRetorno, boolean esEstatico, Clase claseOriginal) {
		this(token.getLexema(), tipoRetorno, esEstatico, claseOriginal);
		this.token = token;
	}
	
	public Metodo(String nombre, Tipo tipoRetorno, boolean esEstatico, Clase claseOriginal) {
		this.nombre = nombre;
		this.tipoRetorno = tipoRetorno;
		parametros = new HashMap<String, Parametro>();
		listaParametros = new ArrayList<Parametro>();
		this.esEstatico = esEstatico;
		this.claseOriginal = claseOriginal;
	}
	
	public Tipo getTipoRetorno() {
		return tipoRetorno;
	}
	
	public boolean esEstatico() {
		return esEstatico;
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
	
	public boolean perteneceClase(Clase clase) {
		return claseOriginal.equals(clase); 
	}
	
	public void generarCodigo() {		
		//Instrucciones que finalizan la construccion del RA
		GeneradorCodigo.generarInstruccionEtiquetada(getEtiqueta(), "LOADFP", "Apila el valor del registro FP");
		GeneradorCodigo.generarInstruccion("LOADSP", "Apila el valor del registro FP");
		GeneradorCodigo.generarInstruccion("STOREFP", "Apila el valor del registro FP");
		
		bloquePrincipal.generarCodigo();
		
		GeneradorCodigo.generarInstruccion("STOREFP", "Almacena el tope de la pila en el registro");
		int cantParametros = listaParametros.size();
		if (!esEstatico) {
			cantParametros++; //"this" cuenta como un parámetro mas para RET
		}
		GeneradorCodigo.generarInstruccion("RET " + cantParametros, null);
	}

	@Override
	public String getEtiqueta() {
		return "lblMet" + nombre + "@" + claseOriginal.getNombre();
	}

}
