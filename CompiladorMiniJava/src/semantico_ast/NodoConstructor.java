package semantico_ast;

import java.util.List;

import main.Token;
import semantico_ts.Clase;
import semantico_ts.Constructor;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Parametro;
import semantico_ts.TablaSimbolos;
import semantico_ts.Tipo;
import semantico_ts.TipoClase;

public class NodoConstructor extends NodoAcceso {
	
	private List<NodoExpresion> parametros;
	
	public NodoConstructor(Token idClase, List<NodoExpresion> parametros) {
		this.token = idClase;
		this.parametros = parametros;
	}
	
	public void agregarParametro(NodoExpresion exp) {
		parametros.addLast(exp);
	}
	
	public void setParametros(List<NodoExpresion> lista) {
		parametros = lista;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		Clase clase = TablaSimbolos.getTabla().getClase(token.getLexema());
		if (clase == null) {
			throw new ExcepcionSemantica(token, "La clase " + token.getLexema() + " no existe.");
		}
		Constructor cons = clase.getConstructor();
		Tipo tipoParam, tipoExp;
		List<Parametro> listaParam = cons.getListaParametros();
		int count = listaParam.size();
		if (count != parametros.size()) {
			throw new ExcepcionSemantica(token, "El constructor " + token.getLexema() + " no tiene la cantidad correcta de parámetros.");
		}
		for (int i=0; i<count; i++) {
			tipoParam = listaParam.get(i).getTipo();
			tipoExp = parametros.get(i).chequear().getTipo();
			if (!tipoExp.conformaCon(tipoParam)) {
				Token t = parametros.get(i).getToken();
				throw new ExcepcionSemantica(t, "El parámetro actual no conforma con el tipo del parámetro formal" + t.getLexema() + ".");
			}
		}
		
		Tipo tipoRetorno = new TipoClase(cons.getNombre());
		InfoCheck infoReturn;
		if (encadenado != null) {
			infoReturn = encadenado.chequear(tipoRetorno);
		}
		else {
			infoReturn = new InfoCheck(tipoRetorno, false, true);
		}
		
		return infoReturn;
	}

}
