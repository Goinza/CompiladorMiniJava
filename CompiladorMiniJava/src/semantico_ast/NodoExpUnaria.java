package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;
import traduccion.GeneradorCodigo;

public class NodoExpUnaria extends NodoExpCompuesta {
	
	private NodoExpCompuesta operando;
	
	public NodoExpUnaria(Token opUnaria) {
		this.token = opUnaria;
	}
	
	public void setOperando(NodoExpCompuesta exp) {
		operando = exp;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		Tipo tipoOperando = operando.chequear().getTipo();
		InfoCheck infoReturn = null;
		if (token.getTipoToken().equals("opSuma") || token.getTipoToken().equals("opResta")) {
			if (tipoOperando.getNombre().equals("int")) {
				infoReturn = new InfoCheck(tipoOperando, false, false);
			}
			else {
				throw new ExcepcionSemantica(token, "El operando debe ser de tipo entero.");
			}
		}
		else if (token.getTipoToken().equals("opNegacion")) {
			if (tipoOperando.getNombre().equals("boolean")) {
				infoReturn = new InfoCheck(tipoOperando, false, false);
			}
			else {
				throw new ExcepcionSemantica(token, "El operando debe ser de tipo booleano.");
			}
		} 

		return infoReturn;
	}

	@Override
	public void generarCodigo() {
		operando.generarCodigo();
		switch (token.getTipoToken()) {
			case "opSuma":
				break;
			case "opResta":
				GeneradorCodigo.generarInstruccion("NEG", "Menos unario");
				break;
			case "opNegacion":
				GeneradorCodigo.generarInstruccion("NOT", "Negacion");
				break;
		}
		
	}

}
