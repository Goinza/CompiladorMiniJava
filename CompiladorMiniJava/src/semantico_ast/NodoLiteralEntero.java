package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;
import semantico_ts.TipoEntero;

public class NodoLiteralEntero extends NodoLiteral {
	
	public NodoLiteralEntero(Token token) {
		this.token = token;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		return new InfoCheck(new TipoEntero(), false, false);
	}

}
