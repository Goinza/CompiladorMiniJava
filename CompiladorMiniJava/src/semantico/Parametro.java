package semantico;

public class Parametro {

	private String nombre;
	private Tipo tipo;
	
	public Parametro(String nombre, Tipo tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
}
