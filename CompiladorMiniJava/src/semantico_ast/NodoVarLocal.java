package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TablaSimbolos;
import semantico_ts.Tipo;
import semantico_ts.TipoNulo;
import semantico_ts.TipoVoid;
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
		if (tipoExp instanceof TipoVoid || tipoExp instanceof TipoNulo) {
			throw new ExcepcionSemantica(token, "Asignacion a var local no puede ser nulo o void");
		}
		VarLocal var = new VarLocal(token, tipoExp);
		TablaSimbolos.getTabla().getBloqueActual().agregarVarLocal(var);
		offset = var.getOffset();
	}

	@Override
	public void generarCodigo() {
		GeneradorCodigo.generarInstruccion("RMEM 1", "Reserva memoria para var local");
		asignacion.generarCodigo();
		GeneradorCodigo.generarInstruccion("STORE " + offset, "Store var local " + token.getLexema());
	}

}
