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
import semantico_ts.TipoVoid;
import traduccion.GeneradorCodigo;

public class NodoLlamadaEstatica extends NodoAcceso {
	
	private Token tokenClase;
	private List<NodoExpresion> parametros;
	private Metodo metodo;
	
	public NodoLlamadaEstatica(Token token, Token tokenClase) {
		this.token = token;
		this.tokenClase = tokenClase;
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
		Clase clase = TablaSimbolos.getTabla().getClase(tokenClase.getLexema());
		metodo = clase.getMetodo(token.getLexema());
		
		if (metodo == null) {
			throw new ExcepcionSemantica(token, "El método no está definido en la clase " + clase.getNombre() +".");
		}
		if (!metodo.esEstatico()) {
			throw new ExcepcionSemantica(token, "El método no es estático");
		}
		
		Tipo tipoParam, tipoExp;		
		List<Parametro> listaParam = metodo.getListaParametros();
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

		Tipo tipoLlamada = metodo.getTipoRetorno();
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
		if (!(metodo.getTipoRetorno() instanceof TipoVoid)) {
			GeneradorCodigo.generarInstruccion("RMEM 1", "Reservo lugar para el retorno");			
		}
		for (NodoExpresion ne : parametros) {
			ne.generarCodigo();
		}
		GeneradorCodigo.generarInstruccion("PUSH " + metodo.getEtiqueta(), "Cargo la etiqueta del metodo");
		GeneradorCodigo.generarInstruccion("CALL", null);
		
		if (encadenado != null) {
			encadenado.generarCodigo();
		}
	}

}
