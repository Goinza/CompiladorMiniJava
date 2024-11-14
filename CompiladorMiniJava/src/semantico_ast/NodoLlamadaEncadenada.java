package semantico_ast;

import java.util.LinkedList;
import java.util.List;

import main.Token;
import semantico_ts.Clase;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Metodo;
import semantico_ts.Parametro;
import semantico_ts.TablaSimbolos;
import semantico_ts.Tipo;

public class NodoLlamadaEncadenada extends NodoEncadenado {
	
	private List<NodoExpresion> parametros;
	
	public NodoLlamadaEncadenada(Token idMetVar) {
		this.token = idMetVar;
		parametros = new LinkedList<NodoExpresion>();
	}
	
	public void agregarParametro(NodoExpresion exp) {
		parametros.addLast(exp);
	}
	
	public void setParametros(List<NodoExpresion> lista) {
		parametros = lista;
	}

	@Override
	public InfoCheck chequear(Tipo t) throws ExcepcionSemantica {
		Clase clase = TablaSimbolos.getTabla().getClase(t.getNombre());
		if (clase == null) {
			throw new ExcepcionSemantica(token, "La parte izquierda de la expresión punto es un tipo primitivo o void.");
		}
		Metodo met = clase.getMetodo(token.getLexema());
		
		if (met == null || met.esEstatico()) {
			throw new ExcepcionSemantica(token, "El método no está definido en la clase " + clase.getNombre() +".");
		}
		
		Tipo tipoParam, tipoExp;		
		List<Parametro> listaParam = met.getListaParametros();
		int count = listaParam.size();
		if (count != parametros.size()) {
			throw new ExcepcionSemantica(token, "El método " + token.getLexema() + " no tiene la cantidad correcta de parámetros.");
		}
		for (int i=0; i<count; i++) {
			tipoParam = listaParam.get(i).getTipo();
			tipoExp = parametros.get(i).chequear().getTipo();
			if (!tipoExp.conformaCon(tipoParam)) {
				Token tok = parametros.get(i).getToken();
				throw new ExcepcionSemantica(tok, "El parámetro actual " + tok.getLexema() + " no conforma con el tipo del parámetro formal.");
			}
		}


		Tipo tipoLlamada = met.getTipoRetorno();
		InfoCheck infoReturn;
		if (encadenado != null) {
			infoReturn = encadenado.chequear(tipoLlamada);
		}
		else {
			infoReturn = new InfoCheck(tipoLlamada, false, true);
		}
		
		return infoReturn;
	}

	@Override
	public void generarCodigo() {
		// TODO Auto-generated method stub
		
	}

}
