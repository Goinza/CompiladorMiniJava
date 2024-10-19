package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;

public class NodoVariable extends NodoAcceso {
	
	public NodoVariable(Token idMetVar) {
		this.token = idMetVar;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		//Variable es parametro, var local o atributo de clase
		// TODO Auto-generated method stub
		return null;
	}

}
