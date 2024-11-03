package semantico_ast;

import java.util.Arrays;
import java.util.List;

import main.Token;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Tipo;
import semantico_ts.TipoBooleano;
import traduccion.GeneradorCodigo;

public class NodoExpBinaria extends NodoExpCompuesta {
	
	private NodoExpCompuesta ladoIzquierdo;
	private NodoExpCompuesta ladoDerecho;
	
	public NodoExpBinaria(Token opBinaria) {
		this.token = opBinaria;
	}
	
	public void setLadoIzquierdo(NodoExpCompuesta exp) {
		ladoIzquierdo = exp;
	}
	
	public void setLadoDerecho(NodoExpCompuesta exp) {
		ladoDerecho = exp;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		Tipo tipoIzq = ladoIzquierdo.chequear().getTipo();
		Tipo tipoDer = ladoDerecho.chequear().getTipo();
		List<String> intToInt = Arrays.asList("opSuma", "opResta", "opMultiplicacion", "opDivision", "opModulo");
		List<String> boolToBool = Arrays.asList("opOr", "opAnd");
		List<String> intToBool = Arrays.asList("opMenor", "opMayor", "opMenorIgual", "opMayorIgual");
		InfoCheck infoReturn = null;
		
		if (intToInt.contains(token.getTipoToken())) {
			if (tipoIzq.equals(tipoDer) && tipoIzq.getNombre().equals("int")) {
				infoReturn = new InfoCheck(tipoIzq, false, false);
			}
			else {
				throw new ExcepcionSemantica(token, "Al menos uno de los operandos no es de tipo entero.");
			}
		}
		else if (boolToBool.contains(token.getTipoToken())) {
			if (tipoIzq.equals(tipoDer) && tipoIzq.getNombre().equals("boolean")) {
				infoReturn = new InfoCheck(tipoIzq, false, false);
			}
			else {
				throw new ExcepcionSemantica(token, "Al menos uno de los operandos no es de tipo booleano.");
			}
		}
		else if (intToBool.contains(token.getTipoToken())) {
			if (tipoIzq.equals(tipoDer) && tipoIzq.getNombre().equals("int")) {
				infoReturn = new InfoCheck(new TipoBooleano(), false, false);
			}
			else {
				throw new ExcepcionSemantica(token, "Al menos uno de los operandos no es de tipo entero.");
			}
		}
		else {
			//Relacion de igualdad, conformidad en una de las direcciones
			if (tipoIzq.conformaCon(tipoDer) || tipoDer.conformaCon(tipoIzq)) {
				infoReturn = new InfoCheck(new TipoBooleano(), false, false);
			}
			else {
				throw new ExcepcionSemantica(token, "Los operandos no son compatibles para esta operación.");
			}
		}
		
		return infoReturn;
	}

	@Override
	public void generarCodigo() {
		ladoIzquierdo.generarCodigo();
		ladoDerecho.generarCodigo();
		switch (token.getTipoToken()) {
			case "opSuma": 
				GeneradorCodigo.generarInstruccion("ADD", "Suma");
				break;
			case "opResta":
				GeneradorCodigo.generarInstruccion("SUB", "Resta");
				break;
			case "opMultiplicacion":
				GeneradorCodigo.generarInstruccion("MUL", "Multiplicacion");
				break;
			case "opDivision":
				GeneradorCodigo.generarInstruccion("DIV", "Division");
				break;
			case "opModulo":
				GeneradorCodigo.generarInstruccion("MOD", "Modulo");
				break;
			case "opOr":
				GeneradorCodigo.generarInstruccion("OR", "");
				break;
			case "opAnd":
				GeneradorCodigo.generarInstruccion("AND", "");
				break;
			case "opMenor":
				GeneradorCodigo.generarInstruccion("LT", "Menor");
				break;
			case "opMenorIgual":
				GeneradorCodigo.generarInstruccion("LE", "Menor o igual");
				break;
			case "opMayor":
				GeneradorCodigo.generarInstruccion("GT", "Mayor");
				break;
			case "opMayorIgual":
				GeneradorCodigo.generarInstruccion("GE", "Mayor o igual");
				break;
			case "opIgual":
				GeneradorCodigo.generarInstruccion("EQ", "Igual");
				break;
			case "opDistinto":
				GeneradorCodigo.generarInstruccion("NE", "Distinto");
				break;
		}
		
	}

}
