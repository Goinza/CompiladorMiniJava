package semantico_ast;

import main.Token;
import semantico_ts.Clase;
import semantico_ts.EntidadLlamable;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Metodo;
import semantico_ts.TablaSimbolos;
import semantico_ts.Tipo;
import semantico_ts.TipoClase;
import traduccion.GeneradorCodigo;

public class NodoThis extends NodoAcceso {
	
	public NodoThis(Token token) {
		this.token = token;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		NodoBloque bloqueActual = TablaSimbolos.getTabla().getBloqueActual();
		Clase claseActual = bloqueActual.getClase();
		EntidadLlamable llamada = bloqueActual.getMetodo();
		if (llamada instanceof Metodo) {
			if (((Metodo)llamada).esEstatico()) {
				throw new ExcepcionSemantica(token, "La sentencia this no es válida en métodos estáticos.");
			}
		}
		Tipo tipoThis = new TipoClase(claseActual.getNombre());
		InfoCheck infoReturn = encadenado == null ? new InfoCheck(tipoThis, false, false) : encadenado.chequear(tipoThis);
		
		return infoReturn;
	}

	@Override
	public void generarCodigo() {
		GeneradorCodigo.generarInstruccion("LOAD 3", "this");		
	}

}
