package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TablaSimbolos;
import traduccion.GeneradorCodigo;

public class NodoBreak extends NodoSentencia {
		
	public NodoBreak(Token token) {
		this.token = token;	
	}

	@Override
	public void chequear() throws ExcepcionSemantica {
		if (!admiteBreak) {
			throw new ExcepcionSemantica(token, "La palabra break solo puede usarse dentro de un bloque while o un bloque switch.");
		}
	}

	@Override
	public void generarCodigo() {
		try {
			String etiqueta = TablaSimbolos.getTabla().getEtiquetaFinLoop();
			GeneradorCodigo.generarInstruccion("JUMP " + etiqueta, null);
		} catch (ExcepcionSemantica e) {
			e.printStackTrace();
		}
	}

}
