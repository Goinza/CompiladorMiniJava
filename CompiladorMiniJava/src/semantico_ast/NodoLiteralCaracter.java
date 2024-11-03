package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TipoCaracter;
import traduccion.GeneradorCodigo;

public class NodoLiteralCaracter extends NodoLiteral {
	
	public NodoLiteralCaracter(Token token) {
		this.token = token;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		return new InfoCheck(new TipoCaracter(), false, false);
	}

	@Override
	public void generarCodigo() {
		char c = token.getLexema().charAt(0);
		int valor = c;
		GeneradorCodigo.generarInstruccion("PUSH " + valor, "Literal caracter " + c);		
	}

}
