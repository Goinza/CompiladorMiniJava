package semantico_ast;

import java.util.LinkedList;
import java.util.List;

public class NodoBloque {
	
	private List<NodoSentencia> sentencias;
	
	public NodoBloque() {
		sentencias = new LinkedList<NodoSentencia>();
	}
	
	public void agregarSentencia(NodoSentencia ns) {
		sentencias.addLast(ns);
	}
	
	public void chequear() {
		for (NodoSentencia ns : sentencias) {
			ns.chequear();
		}
	}

}
