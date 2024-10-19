package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;
import semantico_ts.TipoNulo;

public class NodoLiteralNulo extends NodoLiteral {

	public NodoLiteralNulo(Token token) {
		this.token = token;
	}
	
	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		return new InfoCheck(new TipoNulo(), false);
	}

}
