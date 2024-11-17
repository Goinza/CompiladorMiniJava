package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TipoClase;
import traduccion.FactoryEtiquetas;
import traduccion.GeneradorCodigo;

public class NodoLiteralString extends NodoLiteral {
	
	private String etiqueta;
	
	public NodoLiteralString(Token token) {
		this.token = token;
		etiqueta = "lblString" + FactoryEtiquetas.crearEtiqueta();
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		return new InfoCheck(new TipoClase("String"), false, false);
	}

	@Override
	public void generarCodigo() {
		GeneradorCodigo.generarInstruccion(".DATA", null);
		GeneradorCodigo.generarInstruccionEtiquetada(etiqueta, "DW " + token.getLexema() + ",0", "Literal String");
		GeneradorCodigo.generarInstruccion(".CODE", null);
		GeneradorCodigo.generarInstruccion("PUSH " + etiqueta, null);
	}

}
