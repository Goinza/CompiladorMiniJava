package semantico_ts;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import main.Token;
import semantico_ast.NodoBloque;

public class TablaSimbolos {
	
	private Map<String, Clase> clases;
	private Clase claseActual;
	private EntidadLlamable metodoActual;
	private Deque<NodoBloque> bloqueActual;
	private List<NodoBloque> ast;
	private Metodo main;
	private Token eof;
	static private TablaSimbolos ts;
	
	private TablaSimbolos() throws ExcepcionSemantica {
		clases = new HashMap<String, Clase>();
		ast = new LinkedList<NodoBloque>();
		bloqueActual = new LinkedList<NodoBloque>();
	}
	
	public static TablaSimbolos getTabla() throws ExcepcionSemantica {
		if (TablaSimbolos.ts == null) {
			inicializarTabla();
		}
		
		return TablaSimbolos.ts;
	}
	
	public static void inicializarTabla() throws ExcepcionSemantica {
		TablaSimbolos.ts = new TablaSimbolos();
		ts.agregarClasesPredefinidas();
	}
	
	public void setMain(Metodo m) {
		main = m;
	}
	
	public void setEOFToken(Token t) {
		eof = t;
	}
	
	public void agregarAST(NodoBloque bloque) {
		ast.addFirst(bloque);
	}
	
	public Iterable<Clase> getClases() {
		return clases.values();
	}
	
	public Clase getClase(String nombre) {
		return clases.get(nombre);
	}
	
	public Clase getClaseActual() {
		return claseActual;
	}
	
	public EntidadLlamable getMetodoActual() {
		return metodoActual;
	}
	
	public NodoBloque getBloqueActual() {
		return bloqueActual.getFirst();
	}
	
	public void agregarClase(Clase c) throws ExcepcionSemantica {
		if (clases.get(c.getNombre()) != null) {
			throw new ExcepcionSemantica(c.getToken(), "La clase " + c.getNombre() + " está repetida.");
		}
		clases.put(c.getNombre(), c);
	}
	
	public void setClaseActual(Clase c) {
		claseActual = c;
	}
	
	public void setMetodoActual(EntidadLlamable m) {
		metodoActual = m;
	}
	
	public void agregarBloqueActual(NodoBloque bloque) {
		bloqueActual.addFirst(bloque);
	}
	
	public void removerBloqueActual() {
		bloqueActual.removeFirst();
	}
	
	public void verificarDeclaracion() throws ExcepcionSemantica {
		if (main == null) {
			throw new ExcepcionSemantica(eof, "Método estático main no existe.");
		}
		for (Clase c : clases.values()) {
			c.verificarDeclaracion();
		}
	}
	
	public void consolidar() throws ExcepcionSemantica {
		for (Clase c : clases.values()) {
			if (!c.estaConsolidada()) {
				c.consolidar();				
			}
		}
	}
	
	private void agregarClasesPredefinidas() throws ExcepcionSemantica {
		Clase [] predefinidas = {new ClaseObject(), new ClaseString(), new ClaseSystem()};
		for (Clase c : predefinidas) {
			agregarClase(c);
		}
	}
	
	public void chequearAST() {
		for (NodoBloque bloque : ast) {
			bloque.chequear();
		}
	}

}
