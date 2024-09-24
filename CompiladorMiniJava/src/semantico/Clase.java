package semantico;

import java.util.HashMap;
import java.util.Map;

public class Clase {

	private String nombre;
	private Map<String, Atributo> atributos;
	private Map<String, Constructor> constructores;
	private Map<String, Metodo> metodos;
	
	public Clase(String nombre) {
		this.nombre = nombre;
		atributos = new HashMap<String, Atributo>();
		constructores = new HashMap<String, Constructor>();
		metodos = new HashMap<String, Metodo>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Map<String, Atributo> getAtributos() {
		return atributos;
	}
	
	public Map<String, Constructor> getConstructores() {
		return constructores;
	}
	
	public Map<String, Metodo> getMetodos() {
		return metodos;
	}
	
	public void agregarAtributo(Atributo a) {
		atributos.put(a.getNombre(), a);
	}

	public void agregarConstructor(Constructor c) {
		constructores.put(c.getNombre(), c);
	}
	
	public void agregarMetodo(Metodo m) {
		metodos.put(m.getNombre(), m);
	}
	
	
}
