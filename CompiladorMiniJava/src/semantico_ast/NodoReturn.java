package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Metodo;
import semantico_ts.TablaSimbolos;
import semantico_ts.Tipo;

public class NodoReturn extends NodoSentencia {
	
	private NodoExpresion retorno;
	
	public NodoReturn(Token token) {
		this.token = token;
	}
	
	public void setRetorno(NodoExpresion exp) {
		retorno = exp;
	}

	@Override
	public void chequear() throws ExcepcionSemantica {
		try {
			if (retorno != null) {
				Metodo metodoActual = (Metodo) TablaSimbolos.getTabla().getBloqueActual().getMetodo();
				Tipo tipoMet = metodoActual.getTipoRetorno();
				InfoCheck infoRetorno = retorno.chequear();
				if (!infoRetorno.getTipo().conformaCon(tipoMet)) {
					throw new ExcepcionSemantica(token, "El tipo de la expresión a retornar no conforma con el tipo de retorno de este método.");
				}
			}		
		}
		catch (ClassCastException e) {
			throw new ExcepcionSemantica(token, "Un constructor no puede retornar una expresión.");
		}		
	}

	@Override
	public void generarCodigo() {
		// TODO Auto-generated method stub
		
	}
}
