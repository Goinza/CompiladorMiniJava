package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TipoNulo;
import traduccion.GeneradorCodigo;

public class NodoLiteralNulo extends NodoLiteral {

	public NodoLiteralNulo(Token token) {
		this.token = token;
	}
	
	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		return new InfoCheck(new TipoNulo(), false, false);
	}

	@Override
	public void generarCodigo() {
		GeneradorCodigo.generarInstruccion("PUSH 0", "Valor nulo");		
	}

}
