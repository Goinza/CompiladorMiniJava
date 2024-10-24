package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;

public class NodoCaseSwitch {
	
	private Token token;
	private NodoLiteral caso;
	private NodoSentencia sentencia;
	
	public NodoCaseSwitch(Token token, NodoLiteral caso, NodoSentencia sentencia) {
		this.token = token;
		this.caso = caso;
		this.sentencia = sentencia;
		if (sentencia != null) {
			sentencia.setBreak(true);	
		}		
	}
	
	public Token getToken() {
		return token;
	}

	public void chequear(Tipo tipoCondicion) throws ExcepcionSemantica {
		Tipo tipoCaso = caso.chequear().getTipo();
		if (!tipoCaso.conformaCon(tipoCondicion)) {
			throw new ExcepcionSemantica(token, "El tipo del caso no conforma con el tipo de la condición");
		}
		if (sentencia != null) {
			sentencia.chequear();
		}		
	}

}
