package semantico_ast;

import main.Token;
import semantico_ts.Clase;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TablaSimbolos;
import semantico_ts.Tipo;
import semantico_ts.TipoClase;

public class NodoThis extends NodoAcceso {
	
	public NodoThis(Token token) {
		this.token = token;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		Clase claseActual = TablaSimbolos.getTabla().getClaseActual();
		Tipo tipoThis = new TipoClase(claseActual.getNombre());
		InfoCheck infoReturn = encadenado.chequear(tipoThis);
		
		return infoReturn;
	}

}
