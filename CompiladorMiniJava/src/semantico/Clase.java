package semantico;

import java.util.HashMap;
import java.util.Map;

import main.Token;

public class Clase extends EntidadDeclarada {

	private String padre;
	private Map<String, Atributo> atributos;
	private Constructor constructor;
	private Map<String, Metodo> metodos;
	
	public Clase(Token token) {
		this.token = token;
		this.nombre = token.getLexema();
		atributos = new HashMap<String, Atributo>();
		metodos = new HashMap<String, Metodo>();
	}
	
	public Clase(String nombre) {
		this.nombre = nombre;
		atributos = new HashMap<String, Atributo>();
		metodos = new HashMap<String, Metodo>();
	}
	
	public String getPadre() {
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
	
	public void setPadre(String padre) {
		this.padre = padre;
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
	
	
}
