package semantico_ts;

public class ClaseSystem extends Clase {

	public ClaseSystem() throws ExcepcionSemantica {
		super("System");
		this.setPadre(null);
		agregarMetodosPredefinidos();
	}

	public void agregarMetodosPredefinidos() throws ExcepcionSemantica {
		Metodo m; 
		Parametro p;
		
		m = new Metodo("read", new TipoEntero(), true, this);
		this.agregarMetodo(m);
		
		m = new Metodo("printB", new TipoVoid(), true, this);
		p = new Parametro("b", new TipoBooleano());
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printC", new TipoVoid(), true, this);
		p = new Parametro("c", new TipoCaracter());
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printI", new TipoVoid(), true, this);
		p = new Parametro("i", new TipoEntero());
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printS", new TipoVoid(), true, this);
		p = new Parametro("s", new TipoClase("String"));
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("println", new TipoVoid(), true, this);
		this.agregarMetodo(m);
		
		m = new Metodo("printBln", new TipoVoid(), true, this);
		p = new Parametro("b", new TipoBooleano());
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printCln", new TipoVoid(), true, this);
		p = new Parametro("c", new TipoCaracter());
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printIln", new TipoVoid(), true, this);
		p = new Parametro("i", new TipoEntero());
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printSln", new TipoVoid(), true, this);
		p = new Parametro("s", new TipoClase("String"));
		m.agregarParametro(p);
		this.agregarMetodo(m);
	}
	
}
