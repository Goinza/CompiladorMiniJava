package semantico;

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
	
	public Map<String, Atributo> getAtributos() {
		return atributos;
	}
	
	public Constructor getConstructor() {
		return constructor;
	}
	
	public Map<String, Metodo> getMetodos() {
		return metodos;
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

	public void setConstructor(Constructor c) {
		constructor = c;
	}
	
	public void agregarMetodo(Metodo m) throws ExcepcionSemantica {
		if (metodos.get(m.getNombre()) != null) {
			throw new ExcepcionSemantica(m.getToken(), "El método " + m.getNombre() + " está repetido.");
		}
		metodos.put(m.getNombre(), m);
	}

	public void verificarDeclaracion() throws ExcepcionSemantica {
		if (TablaSimbolos.getTabla().getClases().get(nombrePadre) == null) {
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
			throw new ExcepcionSemantica(token, "La clase" + nombre + " contiene herencia circular");
		}
		visitados.put(nombre, this);
		Clase clasePadre = TablaSimbolos.getTabla().getClases().get(nombrePadre);
		clasePadre.verificarHerenciaCircular(visitados);
	}
	
	public boolean estaConsolidada() {
		return estaConsolidada;
	}
	
	public void consolidar() throws ExcepcionSemantica {		
		Clase clasePadre = TablaSimbolos.getTabla().getClases().get(nombrePadre);
		clasePadre.verificarHerenciaCircular(new HashMap<String, Clase>());
		
		if (!clasePadre.estaConsolidada()) {
			clasePadre.consolidar();
		}
		
		consolidarAtributos(clasePadre);
		consolidarMetodos(clasePadre);
		
		estaConsolidada = true;
	}
	
	private void consolidarAtributos(Clase clasePadre) throws ExcepcionSemantica {		
		Collection<Atributo> atributosPadre = clasePadre.getAtributos().values();
		Atributo atributoActual;
		for (Atributo a : atributosPadre) {
			atributoActual = atributos.get(a.getNombre());
			if (atributoActual != null) {
				throw new ExcepcionSemantica(atributoActual.token, "El atributo " + atributoActual.getNombre() + " ya existe en una super clase");
			}
			atributos.put(a.getNombre(), a);
		}
	}
	
	private void consolidarMetodos(Clase clasePadre) throws ExcepcionSemantica {		
		Collection<Metodo> metodosPadre = clasePadre.getMetodos().values();
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
	
	
}
