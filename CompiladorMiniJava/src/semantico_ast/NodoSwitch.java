package semantico_ast;

import java.util.LinkedList;
import java.util.List;

import main.Token;

public class NodoSwitch extends NodoSentencia {
	
	private NodoExpCompuesta condicion;
	private List<NodoCaseSwitch> bloquesCase;
	private NodoDefaultSwitch bloqueDefault;
	
	public NodoSwitch(Token token) {
		this.token = token;
		bloquesCase = new LinkedList<NodoCaseSwitch>();
	}
	
	public void setCondicion(NodoExpCompuesta exp) {
		condicion = exp;
	}
	
	public void agregarBloqueCase(NodoCaseSwitch nodoCase) {
		bloquesCase.addLast(nodoCase);
	}
	
	public void setBloqueDefault(NodoDefaultSwitch nodoDefault) {
		bloqueDefault = nodoDefault;
	}

	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
