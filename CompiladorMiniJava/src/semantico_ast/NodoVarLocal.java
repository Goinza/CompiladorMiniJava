package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TablaSimbolos;
import semantico_ts.Tipo;
import semantico_ts.VarLocal;
import traduccion.GeneradorCodigo;

public class NodoVarLocal extends NodoSentencia {
	
	private NodoExpCompuesta asignacion;
	private int offset;
	
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
		offset = TablaSimbolos.getTabla().getBloqueActual().agregarVarLocal(var);
	}

	@Override
	public void generarCodigo() {
		asignacion.generarCodigo();
		GeneradorCodigo.generarInstruccion("STORE " + offset, "Store Var Local " + token.getLexema());
	}

}
