package semantico_ast;

import java.util.Iterator;

import main.Token;
import semantico_ts.Atributo;
import semantico_ts.Clase;
import semantico_ts.EntidadLlamable;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Parametro;
import semantico_ts.TablaSimbolos;
import semantico_ts.VarLocal;

public class NodoVariable extends NodoAcceso {
	
	public NodoVariable(Token idMetVar) {
		this.token = idMetVar;
	}

	@Override
	public InfoCheck chequear() throws ExcepcionSemantica {
		InfoCheck infoVar = null;
		NodoBloque bloqueActual = TablaSimbolos.getTabla().getBloqueActual();
		EntidadLlamable metodo = bloqueActual.getMetodo();
		Clase clase = bloqueActual.getClase();
		Iterator<Parametro> params = metodo.getParametros().iterator();
		Iterator<VarLocal> locales = bloqueActual.getVariablesLocales().iterator();
		Iterator<Atributo> atributos = clase.getAtributos().iterator();
		boolean found = false;
		Parametro p;
		VarLocal v;
		Atributo a;
		
		while (params.hasNext() && !found) {
			p = params.next();
			if (p.getToken().getLexema().equals(token.getLexema())) {
				found = true;
				infoVar = encadenado != null ? encadenado.chequear(p.getTipo()) : new InfoCheck(p.getTipo(), true);
			}
		}
		
		while (locales.hasNext() && !found) {
			v = locales.next();
			if (v.getToken().getLexema().equals(token.getLexema())) {
				found = true;
				infoVar = encadenado != null ? encadenado.chequear(v.getTipo()) : new InfoCheck(v.getTipo(), true);
			}
		}
		
		while (atributos.hasNext() && !found) {
			a = atributos.next();
			if (a.getToken().getLexema().equals(token.getLexema())) {
				found = true;
				infoVar = encadenado != null ? encadenado.chequear(a.getTipo()) : new InfoCheck(a.getTipo(), true);
			}
		}
		
		if (!found) {
			throw new ExcepcionSemantica(token, "La variable no existe.");
		}		
		
		return infoVar;
	}

}
