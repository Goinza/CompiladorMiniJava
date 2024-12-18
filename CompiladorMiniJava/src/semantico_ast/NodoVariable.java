package semantico_ast;

import java.util.Iterator;

import main.Token;
import semantico_ts.Atributo;
import semantico_ts.Clase;
import semantico_ts.EntidadLlamable;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.Metodo;
import semantico_ts.Parametro;
import semantico_ts.TablaSimbolos;
import semantico_ts.VarLocal;
import semantico_ts.Variable;
import traduccion.GeneradorCodigo;

public class NodoVariable extends NodoAcceso {
	
	private Variable variable;
	
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
				variable = p;
				infoVar = encadenado != null ? encadenado.chequear(p.getTipo()) : new InfoCheck(p.getTipo(), true, false);
			}
		}
		
		while (locales.hasNext() && !found) {
			v = locales.next();
			if (v.getToken().getLexema().equals(token.getLexema())) {
				found = true;
				variable = v;
				infoVar = encadenado != null ? encadenado.chequear(v.getTipo()) : new InfoCheck(v.getTipo(), true, false);
			}
		}
		
		while (atributos.hasNext() && !found) {
			a = atributos.next();
			if (a.getToken().getLexema().equals(token.getLexema())) {
				found = true;
				variable = a;
				infoVar = encadenado != null ? encadenado.chequear(a.getTipo()) : new InfoCheck(a.getTipo(), true, false);
				if (!a.esEstatico()) {
					EntidadLlamable llamada = TablaSimbolos.getTabla().getBloqueActual().getMetodo();
					if (llamada instanceof Metodo) {
						if (((Metodo)llamada).esEstatico()) {
							throw new ExcepcionSemantica(token, "No se puede acceder a un atributo no estático desde un método estático.");
						}
					}
				}
			}
		}
		
		if (!found) {
			throw new ExcepcionSemantica(token, "La variable " + token.getLexema() + " no existe.");
		}		
		return infoVar;
	}

	@Override
	public void generarCodigo() {
		if (variable.esInstancia()) {
			GeneradorCodigo.generarInstruccion("LOAD 3", "Cargo this");
			if (!esLadoIzquierdoAsignacion() || encadenado != null) {
				GeneradorCodigo.generarInstruccion("LOADREF " + variable.getOffset(), "Offset de variable en this");
			}
			else {
				GeneradorCodigo.generarInstruccion("SWAP", null);
				GeneradorCodigo.generarInstruccion("STOREREF " + variable.getOffset(), "Offset de variable en this");
			}
		}
		else if (variable.esLocal()) {
			if (!esLadoIzquierdoAsignacion() || encadenado != null) {
				GeneradorCodigo.generarInstruccion("LOAD " + variable.getOffset(), "Offset de variable en RA");
			}
			else {
				GeneradorCodigo.generarInstruccion("STORE " + variable.getOffset(), "Offset de variable en RA");
			}
		}
		
		if (encadenado != null) {
			encadenado.generarCodigo();
		}
	}

}
