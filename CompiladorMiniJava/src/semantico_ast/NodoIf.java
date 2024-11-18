package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;
import traduccion.FactoryEtiquetas;
import traduccion.GeneradorCodigo;

public class NodoIf extends NodoSentencia {
	
	private NodoExpresion condicion;
	private NodoSentencia sentenciaThen;
	private String etiqueta;
	
	public NodoIf(Token token) {
		this.token = token;
		this.etiqueta = "lblElse" + FactoryEtiquetas.crearEtiqueta();
	}
	
	public void setCondicion(NodoExpresion exp) {
		condicion = exp;
	}
	
	public void setBloqueThen(NodoSentencia sentencia) {
		sentenciaThen = sentencia;
	}

	@Override
	public void chequear() throws ExcepcionSemantica {
		sentenciaThen.setBreak(admiteBreak);
		Tipo tipoCondicion = condicion.chequear().getTipo();
		if (tipoCondicion.getNombre().equals("boolean")) {
			sentenciaThen.chequear();
		}
		else {
			throw new ExcepcionSemantica(token, "La condición debe ser de tipo booleano.");
		}		
	}

	@Override
	public void generarCodigo() {
		condicion.generarCodigo();
		GeneradorCodigo.generarInstruccion("BF " + etiqueta, "Salto si es falso");
		sentenciaThen.generarCodigo();
		GeneradorCodigo.generarInstruccionEtiquetada(etiqueta, "NOP", null);
	}

}
