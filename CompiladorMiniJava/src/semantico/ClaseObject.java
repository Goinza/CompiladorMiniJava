package semantico;

public class ClaseObject extends Clase {

	public ClaseObject() throws ExcepcionSemantica {
		super("Object");
		agregarMetodosPredefinidos();
	}
	
	private void agregarMetodosPredefinidos() throws ExcepcionSemantica {
		Metodo m = new Metodo("debugPrint", new TipoVoid(), true);
		Parametro p = new Parametro("i", new TipoEntero());
		m.agregarParametro(p);
		this.agregarMetodo(m);
	}

}
