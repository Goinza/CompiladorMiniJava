package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;
import semantico_ts.TipoClase;

public class NodoLiteralString extends NodoLiteral {
	
	public NodoLiteralString(Token token) {
		this.token = token;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		return new InfoCheck(new TipoClase("String"), false);
	}

}
