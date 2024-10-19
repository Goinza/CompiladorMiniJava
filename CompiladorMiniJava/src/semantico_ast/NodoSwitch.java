package semantico_ast;

import java.util.LinkedList;
import java.util.List;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;

public class NodoSwitch extends NodoSentencia {
	
	private NodoExpresion condicion;
	private List<NodoCaseSwitch> casos;
	private NodoDefaultSwitch casoDefault;
	
	public NodoSwitch(Token token) {
		this.token = token;
		casos = new LinkedList<NodoCaseSwitch>();
	}
	
	public void setCondicion(NodoExpresion exp) {
		condicion = exp;
	}
	
	public void agregarBloqueCase(NodoCaseSwitch nodoCase) {
		casos.addLast(nodoCase);
	}
	
	public void setBloqueDefault(NodoDefaultSwitch nodoDefault) throws ExcepcionSemantica {
		if (nodoDefault != null) {
			throw new ExcepcionSemantica(nodoDefault.getToken(), "Un bloque switch no puede tener más de un caso default");
		}
		casoDefault = nodoDefault;
	}

	@Override
	public void chequear() throws ExcepcionSemantica {
		Tipo tipoCondicion = condicion.chequear().getTipo();
		boolean esEntero = tipoCondicion.getNombre().equals("int");
		boolean esBooleano = tipoCondicion.getNombre().equals("boolean");
		boolean esCaracter = tipoCondicion.getNombre().equals("char");
		boolean esPrimitivo = esEntero || esBooleano || esCaracter;
		if (!esPrimitivo) {
			throw new ExcepcionSemantica(token, "La expresión debe ser de un tipo primitivo");
		}
		
		for (NodoCaseSwitch cs : casos) {
			cs.chequear();
		}
		casoDefault.chequear();
	}

}
