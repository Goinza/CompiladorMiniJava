package semantico_ast;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;
import traduccion.GeneradorCodigo;

public class NodoExpAsignacion extends NodoExpresion {
	
	private NodoExpCompuesta ladoIzquierdo;
	private NodoExpCompuesta ladoDerecho;
	
	public NodoExpAsignacion(Token opAsignacion) {
		this.token = opAsignacion;
	}
	
	public void setLadoIzquierdo(NodoExpCompuesta exp) {
		ladoIzquierdo = exp;
	}
	
	public void setLadoDerecho(NodoExpCompuesta exp) {
		ladoDerecho = exp;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		InfoCheck infoIzq = ladoIzquierdo.chequear();
		if (!infoIzq.esAsignable()) {
			throw new ExcepcionSemantica(token, "El lado izquierdo de la asignación no es una variable asignable.");
		}
		Tipo tipoIzq = infoIzq.getTipo();
		Tipo tipoDer = ladoDerecho.chequear().getTipo();
		String op = token.getTipoToken();
		
		if (op == "asignacion" && !tipoDer.conformaCon(tipoIzq)) {
			throw new ExcepcionSemantica(token, "El tipo del lado derecho de la asignación no conforma con el tipo del lado izquierdo.");
		}
		if (op == "asignacionSuma" || op == "asignacionResta") {
			if (!(tipoIzq.equals(tipoDer) && tipoIzq.getNombre().equals("int"))) {
				throw new ExcepcionSemantica(token, "Este tipo de asignación sólo es compatible con el tipo entero.");
			}
		}
		
		return new InfoCheck(tipoDer, false, true);
	}

	@Override
	public void generarCodigo() {
		String op = token.getTipoToken();
		if (op == "asignacionSuma" || op == "asignacionResta") {
			//Accede al valor original de la variable
			ladoIzquierdo.generarCodigo();
		}
		ladoIzquierdo.setEsLadoIzquierdoAsignacion();
		ladoDerecho.generarCodigo();
		//Suma o resta el valor original del lado izquierdo con el lado derecho
		if (op == "asignacionSuma") {
			GeneradorCodigo.generarInstruccion("ADD", "Suma");
		}
		else if (op == "asignacionResta") {
			GeneradorCodigo.generarInstruccion("SUB", "Resta");
		}
		ladoIzquierdo.generarCodigo();
		
	}

}
