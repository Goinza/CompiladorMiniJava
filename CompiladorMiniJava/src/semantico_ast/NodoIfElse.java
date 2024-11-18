package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;
import traduccion.FactoryEtiquetas;
import traduccion.GeneradorCodigo;

public class NodoIfElse extends NodoSentencia {
	
	private NodoExpresion condicion;
	private NodoSentencia sentenciaThen;
	private NodoSentencia sentenciaElse;
	private String etiquetaElse;
	private String etiquetaFin;

	public NodoIfElse(Token token) {
		this.token = token;
		this.etiquetaElse = "lblElse" + FactoryEtiquetas.crearEtiqueta();
		this.etiquetaFin = "lblEndIf" + FactoryEtiquetas.crearEtiqueta();
	}
	
	public void setCondicion(NodoExpresion exp) {
		condicion = exp;
	}
	
	public void setBloqueThen(NodoSentencia sentencia) {
		sentenciaThen = sentencia;
	}
	
	public void setBloqueElse(NodoSentencia sentencia) {
		sentenciaElse = sentencia;
	}
	
	@Override
	public void chequear() throws ExcepcionSemantica {
		sentenciaThen.setBreak(admiteBreak);
		sentenciaElse.setBreak(admiteBreak);
		Tipo tipoCondicion = condicion.chequear().getTipo();
		if (tipoCondicion.getNombre().equals("boolean")) {
			sentenciaThen.chequear();
			sentenciaElse.chequear();
		}
		else {
			throw new ExcepcionSemantica(token, "La condición debe ser de tipo booleano.");
		}
	}

	@Override
	public void generarCodigo() {
		condicion.generarCodigo();
		GeneradorCodigo.generarInstruccion("BF " + etiquetaElse, "Salto al else si es falso");
		sentenciaThen.generarCodigo();
		GeneradorCodigo.generarInstruccion("JUMP " + etiquetaFin, "Salto al final del if");
		GeneradorCodigo.generarInstruccionEtiquetada(etiquetaElse, "NOP", null);	
		sentenciaElse.generarCodigo();
		GeneradorCodigo.generarInstruccionEtiquetada(etiquetaFin, "NOP", null);	
	}

}
