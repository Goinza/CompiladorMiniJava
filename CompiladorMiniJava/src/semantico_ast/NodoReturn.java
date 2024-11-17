package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Metodo;
import semantico_ts.TablaSimbolos;
import semantico_ts.Tipo;
import semantico_ts.TipoVoid;
import traduccion.GeneradorCodigo;

public class NodoReturn extends NodoSentencia {
	
	private NodoExpresion retorno;
	private Metodo metodo;
	
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
				metodo = (Metodo) TablaSimbolos.getTabla().getBloqueActual().getMetodo();
				Tipo tipoMet = metodo.getTipoRetorno();
				if (tipoMet instanceof TipoVoid) {
					throw new ExcepcionSemantica(token, "El retorno no puede tener una expresion");
				}
				InfoCheck infoRetorno = retorno.chequear();
				if (!infoRetorno.getTipo().conformaCon(tipoMet)) {
					throw new ExcepcionSemantica(token, "El tipo de la expresión a retornar no conforma con el tipo de retorno de este método.");
				}
			}
			else {
				if (metodo instanceof Metodo) {
					metodo = (Metodo) TablaSimbolos.getTabla().getBloqueActual().getMetodo();
					Tipo tipoMet = metodo.getTipoRetorno();
					if (!(tipoMet instanceof TipoVoid)) {
						throw new ExcepcionSemantica(token, "El retorno debe contener una expresion valida");
					}
				}				
			}
		}
		catch (ClassCastException e) {
			throw new ExcepcionSemantica(token, "Un constructor no puede retornar una expresión.");
		}		
	}

	@Override
	public void generarCodigo() {
		if (retorno != null) {
			retorno.generarCodigo();
			//Algo mas, moverlo al tope del RA
			int offset = 3;
			if (!metodo.esEstatico()) {
				offset++;
			}
			offset += metodo.getListaParametros().size();
			GeneradorCodigo.generarInstruccion("STORE " + offset, "Guarda el retorno");
			GeneradorCodigo.generarInstruccion("STOREFP", "Actualize el fp para que apunte al RA del llamador");
			GeneradorCodigo.generarInstruccion("RET " + (offset-3), null);
		}
	}
}
