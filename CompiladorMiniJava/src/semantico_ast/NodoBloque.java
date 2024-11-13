package semantico_ast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import main.Token;
import semantico_ts.Clase;
import semantico_ts.EntidadLlamable;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Metodo;
import semantico_ts.TablaSimbolos;
import semantico_ts.VarLocal;
import traduccion.Etiquetable;
import traduccion.GeneradorCodigo;

public class NodoBloque extends NodoSentencia{
	
	private List<NodoSentencia> sentencias;
	private NodoBloque bloquePadre;
	private Map<String, VarLocal> locales;
	private EntidadLlamable metodo;
	private Clase clase;
	private int etiqueta;
	
	public NodoBloque(NodoBloque padre) throws ExcepcionSemantica {
		sentencias = new LinkedList<NodoSentencia>();
		bloquePadre = padre;
		locales = new HashMap<String, VarLocal>();
		metodo = TablaSimbolos.getTabla().getMetodoActual();
		clase = TablaSimbolos.getTabla().getClaseActual();
	}
	
	public void agregarSentencia(NodoSentencia ns) {
		sentencias.addLast(ns);
	}
	
	//Retorna offset de la variable agregada
	public int agregarVarLocal(VarLocal var) throws ExcepcionSemantica {
		int offset = locales.size(); //Primera variable tiene offset 0, entonces el offset es el tamaño de la tabla antes de agregar la var local
		String nombreVar = var.getToken().getLexema();
		if (locales.get(nombreVar) != null) {
			throw new ExcepcionSemantica(var.getToken(), "La variable local está repetida.");
		}
		locales.put(nombreVar, var);
		
		return offset * -1;
	}
	
	public Iterable<VarLocal> getVariablesLocales() {
		return locales.values();
	}
	
	public NodoBloque getBloquePadre() {
		return bloquePadre;
	}
	
	public EntidadLlamable getMetodo() {
		return metodo;
	}
	
	public Clase getClase() {
		return clase;
	}
	
	public void chequear() throws ExcepcionSemantica {		
		if (bloquePadre != null) {
			locales = new HashMap<String, VarLocal>(bloquePadre.locales);
		}
		else {
			locales = new HashMap<String, VarLocal>();
		}
		TablaSimbolos.getTabla().setBloqueActual(this);
		for (NodoSentencia ns : sentencias) {
			ns.setBreak(admiteBreak);
			ns.chequear();
			
		}
		TablaSimbolos.getTabla().setBloqueActual(bloquePadre);
	}

	public void setMetodo(EntidadLlamable m) {
		metodo = m;		
		if (bloquePadre == null) {
			metodo.setBloquePrincipal(this);
		}
	}

	@Override
	public void generarCodigo() {
		for (NodoSentencia ns : sentencias) {
			ns.generarCodigo();
		}
		
		locales.size();
		GeneradorCodigo.generarInstruccion("FMEM " + locales.size(), null);
	}

}
