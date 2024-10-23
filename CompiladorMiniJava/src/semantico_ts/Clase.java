package semantico_ts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import main.Token;

public class Clase extends EntidadDeclarada {

	protected Token padre;
	protected String nombrePadre;
	protected Map<String, Atributo> atributos;
	protected Constructor constructor;
	protected Map<String, Metodo> metodos;
	protected boolean estaConsolidada;
	
	public Clase(Token token) {
		this(token.getLexema());
		this.token = token;
	}
	
	public Clase(String nombre) {
		this.nombre = nombre;
		atributos = new HashMap<String, Atributo>();
		metodos = new HashMap<String, Metodo>();
		estaConsolidada = false;
	}
	
	public Token getPadre() {
		return padre;
	}
	
	public Iterable<Atributo> getAtributos() {
		return atributos.values();
	}
	
	public Atributo getAtributo(String nombre) {
		return atributos.get(nombre);
	}
	
	public Constructor getConstructor() {
		return constructor;
	}
	
	public Iterable<Metodo> getMetodos() {
		return metodos.values();
	}
	
	public Metodo getMetodo(String nombre) {
		return metodos.get(nombre);
	}
	
	public void setPadre(Token padre) {
		if (padre != null) {
			this.padre = padre;
			this.nombrePadre = padre.getLexema();
		}
		else {
			this.padre = null;
			this.nombrePadre = "Object";
		}
	}
	
	public void agregarAtributo(Atributo a) throws ExcepcionSemantica {
		if (atributos.get(a.getNombre()) != null) {
			throw new ExcepcionSemantica(a.getToken(), "El atributo " + a.getNombre() + " está repetido.");
		}
		atributos.put(a.getNombre(), a);
	}

	public void setConstructor(Constructor c) throws ExcepcionSemantica {
		if (constructor != null) {
			throw new ExcepcionSemantica(c.getToken(), "La clase " + nombre + " tiene más de un constructor.");
		}
		if (!c.getNombre().equals(nombre)) {
			throw new ExcepcionSemantica(c.getToken(), "El constructor de la clase " + nombre + " tiene un nombre incorrecto");
		}
		constructor = c;
	}
	
	public void agregarMetodo(Metodo m) throws ExcepcionSemantica {
		if (metodos.get(m.getNombre()) != null) {
			throw new ExcepcionSemantica(m.getToken(), "El método " + m.getNombre() + " está repetido.");
		}
		metodos.put(m.getNombre(), m);
		
		if (esMetodoMain(m)) {
			TablaSimbolos.getTabla().setMain(m);
		}
	}

	public void verificarDeclaracion() throws ExcepcionSemantica {
		if (TablaSimbolos.getTabla().getClase(nombrePadre) == null) {
			throw new ExcepcionSemantica(padre, "La clase " + nombrePadre + " no existe.");
		}
				
		for (Atributo a : atributos.values()) {
			a.verificarDeclaracion();
		}
		for (Metodo m : metodos.values()) {
			m.verificarDeclaracion();
		}
		
		if (constructor != null) {
			constructor.verificarDeclaracion();
		}
		else {
			constructor = new Constructor(nombre);
		}
	}
	
	public void verificarHerenciaCircular(Map<String, Clase> visitados) throws ExcepcionSemantica {		
		if (visitados.get(nombre) != null) {
			throw new ExcepcionSemantica(token, "La clase " + nombre + " contiene herencia circular");
		}
		visitados.put(nombre, this);
		Clase clasePadre = TablaSimbolos.getTabla().getClase(nombrePadre);
		clasePadre.verificarHerenciaCircular(visitados);
	}
	
	public boolean estaConsolidada() {
		return estaConsolidada;
	}
	
	public void consolidar() throws ExcepcionSemantica {		
		Clase clasePadre = TablaSimbolos.getTabla().getClase(nombrePadre);
		clasePadre.verificarHerenciaCircular(new HashMap<String, Clase>());
		
		if (!clasePadre.estaConsolidada()) {
			clasePadre.consolidar();
		}
		
		consolidarAtributos(clasePadre);
		consolidarMetodos(clasePadre);
		
		estaConsolidada = true;
	}
	
	private void consolidarAtributos(Clase clasePadre) throws ExcepcionSemantica {		
		Iterable<Atributo> atributosPadre = clasePadre.getAtributos();
		Atributo atributoActual;
		for (Atributo a : atributosPadre) {
			atributoActual = atributos.get(a.getNombre());
			if (atributoActual != null) {
				throw new ExcepcionSemantica(atributoActual.token, "El atributo " + atributoActual.getNombre() + " ya existe en una clase ancestro");
			}
			atributos.put(a.getNombre(), a);
		}
	}
	
	private void consolidarMetodos(Clase clasePadre) throws ExcepcionSemantica {		
		Iterable<Metodo> metodosPadre = clasePadre.getMetodos();
		Metodo metodoActual;
		for (Metodo m : metodosPadre) {
			metodoActual = metodos.get(m.getNombre());
			if (metodoActual == null) {
				metodos.put(m.getNombre(), m);
			}
			else if (!metodoActual.equals(m)) {
				throw new ExcepcionSemantica(metodoActual.getToken(), "El metodo " + metodoActual.getNombre() + " esta sobrecargado");
			}
		}
	}	
	
	private boolean esMetodoMain(Metodo m) {
		boolean esEstatico = m.esEstatico();
		boolean ceroParametros = m.getListaParametros().size() == 0;
		boolean retornoVoid = m.getTipoRetorno().getNombre().equals("void");
		boolean nombreMain = m.getNombre().equals("main");
		
		return esEstatico && retornoVoid && nombreMain && ceroParametros;
	}
	
	public boolean esDescendienteDe(Clase c) throws ExcepcionSemantica {
		boolean iguales = nombre.equals(c.getNombre());
		boolean esHijo = nombrePadre.equals(c.getNombre());
		Clase clasePadre = TablaSimbolos.getTabla().getClase(nombrePadre);
		
		return iguales || esHijo || clasePadre.esDescendienteDe(c);
	}
	
}
