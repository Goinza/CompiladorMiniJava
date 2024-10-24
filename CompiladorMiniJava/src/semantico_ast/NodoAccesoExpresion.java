package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;

public class NodoAccesoExpresion extends NodoAcceso {
	
	private NodoExpresion exp;
	
	public NodoAccesoExpresion(Token token, NodoExpresion exp) {
		this.token = token;
		this.exp = exp;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		//Exp parentizada nunca es asignable
		InfoCheck infoExp = exp.chequear();
		InfoCheck infoEnc;
		InfoCheck infoReturn;
		if (encadenado != null) {
			infoEnc = encadenado.chequear(infoExp.getTipo());
			infoReturn = new InfoCheck(infoEnc.getTipo(), false, infoEnc.esSentencia());
		}
		else {
			infoReturn = new InfoCheck(infoExp.getTipo(), false, infoExp.esSentencia());
		}
		
		return infoReturn;
	}

}
