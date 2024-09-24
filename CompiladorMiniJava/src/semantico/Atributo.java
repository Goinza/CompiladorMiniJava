package semantico;

public class Atributo {

	private String nombre;
	private Tipo tipo;
	private boolean esEstatico;
	
	public Atributo(String nombre, Tipo tipo, boolean esEstatico) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.esEstatico = esEstatico;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public boolean esEstatico() {
		return esEstatico;
	}
	
}
