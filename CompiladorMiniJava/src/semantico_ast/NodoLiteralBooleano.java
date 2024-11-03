package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TipoBooleano;
import traduccion.GeneradorCodigo;

public class NodoLiteralBooleano extends NodoLiteral {
	
	public NodoLiteralBooleano(Token token) {
		this.token = token;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		return new InfoCheck(new TipoBooleano(), false, false);
	}

	@Override
	public void generarCodigo() {
		int valor = token.getLexema().equals("true") ? 1 : 0;
		GeneradorCodigo.generarInstruccion("PUSH " + valor, "Literal booleano");
		
	}

}
