package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;
import traduccion.FactoryEtiquetas;
import traduccion.GeneradorCodigo;

public class NodoCaseSwitch {
	
	private Token token;
	private NodoLiteral caso;
	private NodoSentencia sentencia;
	private String etiquetaInicio; //Inicio del codigo posterior a la condicion
	private String etiquetaFin;
	
	public NodoCaseSwitch(Token token, NodoLiteral caso, NodoSentencia sentencia) {
		this.token = token;
		this.caso = caso;
		this.sentencia = sentencia;
		if (sentencia != null) {
			sentencia.setBreak(true);	
		}		
		etiquetaInicio = "lblCaseInicio" + FactoryEtiquetas.crearEtiqueta();
		etiquetaFin = "lblCaseFin" + FactoryEtiquetas.crearEtiqueta();
	}
	
	public Token getToken() {
		return token;
	}

	public void chequear(Tipo tipoCondicion) throws ExcepcionSemantica {
		Tipo tipoCaso = caso.chequear().getTipo();
		if (!tipoCaso.conformaCon(tipoCondicion)) {
			throw new ExcepcionSemantica(token, "El tipo del caso no conforma con el tipo de la condición");
		}
		if (sentencia != null) {
			sentencia.chequear();
		}		
	}
	
	public void generarCodigo() {
		GeneradorCodigo.generarInstruccion("DUP", "Copia de la condicion");
		caso.generarCodigo();
		GeneradorCodigo.generarInstruccion("EQ", "Igual");
		GeneradorCodigo.generarInstruccion("BF " + etiquetaFin, "Salta este caso");
		GeneradorCodigo.generarInstruccionEtiquetada(etiquetaInicio, "NOP", null);
		sentencia.generarCodigo();		
	}
	
	public String getEtiquetaInicio() {
		return etiquetaInicio;
	}
	
	public String getEtiquetaFin() {
		return etiquetaFin;
	}

}
