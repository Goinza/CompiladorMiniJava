package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;
import traduccion.FactoryEtiquetas;
import traduccion.GeneradorCodigo;

public class NodoWhile extends NodoSentencia {
	
	private NodoExpresion condicion;
	private NodoSentencia sentencia;
	private String etiquetaInicio;
	private String etiquetaFin;
	
	public NodoWhile(Token token) {
		this.token = token;
		etiquetaInicio = "lblWhileInicio" + FactoryEtiquetas.crearEtiqueta();
		etiquetaFin = "lblWhileFin" + FactoryEtiquetas.crearEtiqueta();
	}

	public void setCondicion(NodoExpresion exp) {
		condicion = exp;
	}
	
	public void setSentencia(NodoSentencia sent) {
		sentencia = sent;
		sentencia.setBreak(true);
	}
	
	@Override
	public void chequear() throws ExcepcionSemantica {
		Tipo tipoCondicion = condicion.chequear().getTipo();
		boolean esBooleano = tipoCondicion.getNombre().equals("boolean");
		if (!esBooleano) {
			throw new ExcepcionSemantica(token, "La condición debe ser del tipo booleano.");
		}
		sentencia.chequear();
	}

	@Override
	public void generarCodigo() {		
		GeneradorCodigo.generarInstruccionEtiquetada(etiquetaInicio, "NOP", null);
		condicion.generarCodigo();
		GeneradorCodigo.generarInstruccion("BF " + etiquetaFin, "Fin de ultima iteracion");		
		sentencia.generarCodigo();
		GeneradorCodigo.generarInstruccion("JUMP " + etiquetaInicio, "Siguiente iteracion");
		GeneradorCodigo.generarInstruccionEtiquetada(etiquetaFin, "NOP", null);		
	}

}
