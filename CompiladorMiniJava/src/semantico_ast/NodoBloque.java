package semantico_ast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import semantico_ts.Clase;
import semantico_ts.EntidadLlamable;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TablaSimbolos;
import semantico_ts.VarLocal;
import traduccion.GeneradorCodigo;

public class NodoBloque extends NodoSentencia{
	
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
	
	//Retorna offset de la variable agregada
	public void agregarVarLocal(VarLocal var) throws ExcepcionSemantica {
		int offset = locales.size(); //Primera variable tiene offset 0, entonces el offset es el tamaño de la tabla antes de agregar la var local
		var.setOffset(offset * -1); //Var locales tienen offset negativo
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
			locales = new HashMap<String, VarLocal>(bloquePadre.locales);
			metodo = bloquePadre.metodo;
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
	
	public void setClase(Clase c) {
		clase = c;
	}

	@Override
	public void generarCodigo() {
		for (NodoSentencia ns : sentencias) {
			ns.generarCodigo();
		}
		
		int localesPropias = locales.size();
		if (bloquePadre != null) {
			localesPropias -= bloquePadre.locales.size();
		}
		locales.size();
		GeneradorCodigo.generarInstruccion("FMEM " + localesPropias, null);
	}

}
