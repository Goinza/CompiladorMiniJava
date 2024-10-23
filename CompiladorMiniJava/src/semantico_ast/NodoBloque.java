package semantico_ast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import semantico_ts.Clase;
import semantico_ts.EntidadLlamable;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Metodo;
import semantico_ts.TablaSimbolos;
import semantico_ts.VarLocal;

public class NodoBloque extends NodoSentencia {
	
	private List<NodoSentencia> sentencias;
	private NodoBloque bloquePadre;
	private Map<String, VarLocal> locales;
	private EntidadLlamable metodo;
	private Clase clase;
	
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
	
	public void agregarVarLocal(VarLocal var) throws ExcepcionSemantica {
		String nombreVar = var.getToken().getLexema();
		if (locales.get(nombreVar) != null) {
			throw new ExcepcionSemantica(var.getToken(), "La variable local está repetida.");
		}
		locales.put(nombreVar, var);
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
			locales = bloquePadre.locales;
		}
		else {
			locales = new HashMap<String, VarLocal>();
		}
		TablaSimbolos.getTabla().setBloqueActual(this);
		for (NodoSentencia ns : sentencias) {
			ns.chequear();
		}
	}

	public void setMetodo(Metodo m) {
		metodo = m;		
	}

}
