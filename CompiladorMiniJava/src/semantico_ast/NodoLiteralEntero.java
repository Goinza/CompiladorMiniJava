package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TipoEntero;
import traduccion.GeneradorCodigo;

public class NodoLiteralEntero extends NodoLiteral {
	
	public NodoLiteralEntero(Token token) {
		this.token = token;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		return new InfoCheck(new TipoEntero(), false, false);
	}

	@Override
	public void generarCodigo() {
		String valor = token.getLexema();
		GeneradorCodigo.generarInstruccion("PUSH " + valor, "Literal entero");		
	}

}
