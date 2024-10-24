package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;
import semantico_ts.TipoCaracter;

public class NodoLiteralCaracter extends NodoLiteral {
	
	public NodoLiteralCaracter(Token token) {
		this.token = token;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		return new InfoCheck(new TipoCaracter(), false, false);
	}

}
