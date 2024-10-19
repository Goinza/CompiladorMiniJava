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

public class NodoLlamada extends NodoAcceso {
	
	private List<NodoExpresion> parametros;
	
	public NodoLlamada(Token idMetVar) {
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
	public InfoCheck chequear() throws ExcepcionSemantica {
		Clase claseActual = TablaSimbolos.getTabla().getClaseActual();
		Metodo met = claseActual.getMetodo(token.getLexema());
		
		if (met == null || met.esEstatico()) {
			throw new ExcepcionSemantica(token, "El método no está definido en la clase " + claseActual.getNombre() +".");
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
				Token t = parametros.get(i).getToken();
				throw new ExcepcionSemantica(t, "El parámetro actual " + t.getLexema() + " no conforma con el tipo del parámetro formal.");
			}
		}

		Tipo tipoLlamada = met.getTipoRetorno();
		InfoCheck infoReturn;
		if (encadenado != null) {
			infoReturn = encadenado.chequear(tipoLlamada);
		}
		else {
			infoReturn = new InfoCheck(tipoLlamada, false);
		}
		
		return infoReturn;
	}

}
