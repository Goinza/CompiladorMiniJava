package semantico_ast;

import java.util.LinkedList;
import java.util.List;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TablaSimbolos;
import semantico_ts.Tipo;
import traduccion.FactoryEtiquetas;
import traduccion.GeneradorCodigo;

public class NodoSwitch extends NodoSentencia {
	
	private NodoExpresion condicion;
	private List<NodoCaseSwitch> casos;
	private NodoDefaultSwitch casoDefault;
	private String etiquetaFin;
	
	public NodoSwitch(Token token) {
		this.token = token;
		casos = new LinkedList<NodoCaseSwitch>();
		etiquetaFin = "lblSwitchFin" + FactoryEtiquetas.crearEtiqueta();
	}
	
	public void setCondicion(NodoExpresion exp) {
		condicion = exp;
	}
	
	public void agregarBloqueCase(NodoCaseSwitch nodoCase) {
		casos.addLast(nodoCase);
	}
	
	public void setBloqueDefault(NodoDefaultSwitch nodoDefault) throws ExcepcionSemantica {
		if (casoDefault != null) {
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
			cs.chequear(tipoCondicion);
		}
		if (casoDefault != null) {
			casoDefault.chequear();
		}		
	}

	@Override
	public void generarCodigo() {
		try {
			TablaSimbolos.getTabla().setEtiquetaFinLoop(etiquetaFin);
			NodoCaseSwitch caso;	
			int size = casos.size();
			condicion.generarCodigo();	
			for (int i=0; i<size; i++) {
				caso = casos.get(i);
				caso.generarCodigo();
				if (i+1 < size) {
					GeneradorCodigo.generarInstruccion("JUMP " + casos.get(i+1).getEtiquetaInicio(), "Salto al codigo del siguiente caso");
				}		
				GeneradorCodigo.generarInstruccionEtiquetada(caso.getEtiquetaFin(), "NOP", null);
			}		
			if (casoDefault != null) {
				casoDefault.generarCodigo();
			}
			GeneradorCodigo.generarInstruccionEtiquetada(etiquetaFin, "NOP", null);
			GeneradorCodigo.generarInstruccion("POP", "Elimina el valor de la condicion");
			TablaSimbolos.getTabla().setEtiquetaFinLoop("");
		} catch (ExcepcionSemantica e) {
			e.printStackTrace();
		}
		
	}

}
