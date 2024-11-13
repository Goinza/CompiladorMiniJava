package semantico_ts;

import semantico_ast.NodoBloque;
import traduccion.BloquePrintB;
import traduccion.BloquePrintBLn;
import traduccion.BloquePrintC;
import traduccion.BloquePrintCLn;
import traduccion.BloquePrintI;
import traduccion.BloquePrintILn;
import traduccion.BloquePrintLn;
import traduccion.BloquePrintS;
import traduccion.BloquePrintSLn;
import traduccion.BloqueRead;

public class ClaseSystem extends Clase {

	public ClaseSystem() throws ExcepcionSemantica {
		super("System");
		this.setPadre(null);
		agregarMetodosPredefinidos();
	}

	public void agregarMetodosPredefinidos() throws ExcepcionSemantica {
		Metodo m; 
		Parametro p;
		NodoBloque bloque;
		
		m = new Metodo("read", new TipoEntero(), true, this);
		bloque = new BloqueRead();
		bloque.setMetodo(m);
		this.agregarMetodo(m);
		
		m = new Metodo("printB", new TipoVoid(), true, this);
		p = new Parametro("b", new TipoBooleano());
		bloque = new BloquePrintB();
		bloque.setMetodo(m);
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printC", new TipoVoid(), true, this);
		p = new Parametro("c", new TipoCaracter());
		bloque = new BloquePrintC();
		bloque.setMetodo(m);
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printI", new TipoVoid(), true, this);
		p = new Parametro("i", new TipoEntero());
		bloque = new BloquePrintI();
		bloque.setMetodo(m);
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printS", new TipoVoid(), true, this);
		p = new Parametro("s", new TipoClase("String"));
		bloque = new BloquePrintS();
		bloque.setMetodo(m);
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("println", new TipoVoid(), true, this);
		bloque = new BloquePrintLn();
		bloque.setMetodo(m);
		this.agregarMetodo(m);
		
		m = new Metodo("printBln", new TipoVoid(), true, this);
		p = new Parametro("b", new TipoBooleano());
		bloque = new BloquePrintBLn();
		bloque.setMetodo(m);
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printCln", new TipoVoid(), true, this);
		p = new Parametro("c", new TipoCaracter());
		bloque = new BloquePrintCLn();
		bloque.setMetodo(m);
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printIln", new TipoVoid(), true, this);
		p = new Parametro("i", new TipoEntero());
		bloque = new BloquePrintILn();
		bloque.setMetodo(m);
		m.agregarParametro(p);
		this.agregarMetodo(m);
		
		m = new Metodo("printSln", new TipoVoid(), true, this);
		p = new Parametro("s", new TipoClase("String"));
		bloque = new BloquePrintSLn();
		bloque.setMetodo(m);
		m.agregarParametro(p);
		this.agregarMetodo(m);
	}
	
}
