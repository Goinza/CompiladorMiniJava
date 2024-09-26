package semantico;

public class ClaseSystem extends Clase {

	public ClaseSystem() throws ExcepcionSemantica {
		super("System");
		agregarMetodosPredefinidos();
	}

	public void agregarMetodosPredefinidos() throws ExcepcionSemantica {
		Metodo m; 
		Parametro p;
		
		m = new Metodo("read", new TipoEntero(), true);
		this.agregarMetodo(m);
		
		m = new Metodo("printB", new TipoVoid(), true);
		p = new Parametro("b", new TipoBooleano());
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printC", new TipoVoid(), true);
		p = new Parametro("c", new TipoCaracter());
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printI", new TipoVoid(), true);
		p = new Parametro("i", new TipoEntero());
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printS", new TipoVoid(), true);
		p = new Parametro("s", new TipoClase("String"));
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("println", new TipoVoid(), true);
		this.agregarMetodo(m);
		
		m = new Metodo("printBln", new TipoVoid(), true);
		p = new Parametro("b", new TipoBooleano());
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printCln", new TipoVoid(), true);
		p = new Parametro("c", new TipoCaracter());
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printIln", new TipoVoid(), true);
		p = new Parametro("i", new TipoEntero());
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printSln", new TipoVoid(), true);
		p = new Parametro("s", new TipoClase("String"));
		m.agregarParametro(p);
		this.agregarMetodo(m);
	}
	
}
