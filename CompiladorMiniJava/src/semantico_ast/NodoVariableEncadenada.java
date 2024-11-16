package semantico_ast;

import main.Token;
import semantico_ts.Atributo;
import semantico_ts.Clase;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TablaSimbolos;
import semantico_ts.Tipo;
import traduccion.GeneradorCodigo;

public class NodoVariableEncadenada extends NodoEncadenado {
	
	private Atributo atributo;
	
	public NodoVariableEncadenada(Token token) {
		this.token = token;
	}

	@Override
	public InfoCheck chequear(Tipo t) throws ExcepcionSemantica {
		Clase c = TablaSimbolos.getTabla().getClase(t.getNombre());
		atributo = c.getAtributo(token.getLexema());
		InfoCheck infoReturn;
		if (atributo == null) {
			throw new ExcepcionSemantica(token, "La variable no es un atributo de la clase " + c.getNombre());
		}
		if (encadenado != null) {
			infoReturn = encadenado.chequear(atributo.getTipo());
		}
		else {
			infoReturn = new InfoCheck(atributo.getTipo(), true, false);
		}		
		
		return infoReturn;
	}

	@Override
	public void generarCodigo() {
		if (!esLadoIzquierdoAsignacion() || encadenado != null) {
			GeneradorCodigo.generarInstruccion("LOADREF " + atributo.getOffset(), "Offset de variable en this");
		}
		else {
			GeneradorCodigo.generarInstruccion("SWAP", null);
			GeneradorCodigo.generarInstruccion("STOREREF " + atributo.getOffset(), "Offset de variable en this");
		}
		
		if (encadenado != null) {
			encadenado.generarCodigo();
		}
	}

}
