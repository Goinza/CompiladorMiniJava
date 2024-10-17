package semantico_ast;

import java.util.LinkedList;
import java.util.List;

import main.Token;
import semantico_ts.ExcepcionSemantica;

public class NodoSwitch extends NodoSentencia {
	
	private NodoExpresion condicion;
	private List<NodoCaseSwitch> bloquesCase;
	private NodoDefaultSwitch bloqueDefault;
	
	public NodoSwitch(Token token) {
		this.token = token;
		bloquesCase = new LinkedList<NodoCaseSwitch>();
	}
	
	public void setCondicion(NodoExpresion exp) {
		condicion = exp;
	}
	
	public void agregarBloqueCase(NodoCaseSwitch nodoCase) {
		bloquesCase.addLast(nodoCase);
	}
	
	public void setBloqueDefault(NodoDefaultSwitch nodoDefault) throws ExcepcionSemantica {
		if (nodoDefault != null) {
			throw new ExcepcionSemantica(nodoDefault.getToken(), "Un bloque switch no puede tener más de un caso default");
		}
		bloqueDefault = nodoDefault;
	}

	@Override
	public void chequear() {
		// TODO Auto-generated method stub

	}

}
