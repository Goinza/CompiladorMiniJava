package semantico_ast;

import java.util.LinkedList;
import java.util.List;

import semantico_ts.ExcepcionSemantica;

public class NodoBloque extends NodoSentencia {
	
	private List<NodoSentencia> sentencias;
	
	public NodoBloque() {
		sentencias = new LinkedList<NodoSentencia>();
	}
	
	public void agregarSentencia(NodoSentencia ns) {
		sentencias.addLast(ns);
	}
	
	public void chequear() throws ExcepcionSemantica {
		for (NodoSentencia ns : sentencias) {
			ns.chequear();
		}
	}

}
