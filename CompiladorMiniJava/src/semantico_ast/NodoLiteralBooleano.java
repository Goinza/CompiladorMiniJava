package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;
import semantico_ts.TipoBooleano;

public class NodoLiteralBooleano extends NodoLiteral {
	
	public NodoLiteralBooleano(Token token) {
		this.token = token;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		return new InfoCheck(new TipoBooleano(), false);
	}

}
