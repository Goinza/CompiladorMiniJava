package semantico_ts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.Token;
import traduccion.Etiquetable;
import traduccion.GeneradorCodigo;
import traduccion.Offset;

public class Metodo extends EntidadLlamable implements Etiquetable, Offset {
	
	private Tipo tipoRetorno;
	private boolean esEstatico;
	private Clase claseOriginal;
	private int offset;
	
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
		
		int paramOffset = 3;
		if (!esEstatico) {
			paramOffset++;
		}
		//Asigno offsets en el orden inverso de la lista de parametros
		//De tal manera que el offset del ultimo parametro es 3 en metodos estaticos y 4 en dinamicos
		Iterable<Parametro> paramReversed = listaParametros.reversed();
		for (Parametro p : paramReversed) {
			p.setOffset(paramOffset);
			paramOffset++;
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

	@Override
	public int getOffset() {
		if (esEstatico) {
			return -1; //No se aplica offset a metodos estaticos ya que no pertenecen a la VT de la clase
		}
		return offset;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}

}
