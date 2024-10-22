package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TablaSimbolos;
import semantico_ts.Tipo;
import semantico_ts.VarLocal;

public class NodoVarLocal extends NodoSentencia {
	
	private NodoExpCompuesta asignacion;
	
	public NodoVarLocal(Token idMetVar) {
		this.token = idMetVar;
	}
	
	public void setAsignacion(NodoExpCompuesta exp) {
		asignacion = exp;
	}

	@Override
	public void chequear() throws ExcepcionSemantica {
		Tipo tipoExp = asignacion.chequear().getTipo();
		VarLocal var = new VarLocal(token, tipoExp);
		TablaSimbolos.getTabla().getBloqueActual().agregarVarLocal(var);
	}

}
