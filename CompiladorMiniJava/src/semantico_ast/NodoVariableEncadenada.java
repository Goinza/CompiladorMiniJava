package semantico_ast;

import main.Token;
import semantico_ts.Atributo;
import semantico_ts.Clase;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TablaSimbolos;
import semantico_ts.Tipo;

public class NodoVariableEncadenada extends NodoEncadenado {
	
	public NodoVariableEncadenada(Token token) {
		this.token = token;
	}

	@Override
	public InfoCheck chequear(Tipo t) throws ExcepcionSemantica {
		Clase c = TablaSimbolos.getTabla().getClase(t.getNombre());
		Atributo atr = c.getAtributo(token.getLexema());
		InfoCheck infoReturn;
		if (atr == null) {
			throw new ExcepcionSemantica(token, "La variable no es un atributo de la clase " + c.getNombre());
		}
		if (encadenado != null) {
			infoReturn = encadenado.chequear(atr.getTipo());
		}
		else {
			infoReturn = new InfoCheck(atr.getTipo(), true);
		}		
		
		return infoReturn;
	}

}
