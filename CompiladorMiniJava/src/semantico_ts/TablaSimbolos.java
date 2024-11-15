package semantico_ts;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import main.Token;
import semantico_ast.NodoBloque;
import traduccion.GeneradorCodigo;

public class TablaSimbolos {
	
	private Map<String, Clase> clases;
	private Clase claseActual;
	private EntidadLlamable metodoActual;
	private NodoBloque bloqueActual;
	private List<NodoBloque> ast;
	private Metodo main;
	private Token eof;
	static private TablaSimbolos ts;
	
	private TablaSimbolos() throws ExcepcionSemantica {
		clases = new HashMap<String, Clase>();
		ast = new LinkedList<NodoBloque>();
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
		return bloqueActual;
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
	
	public void setBloqueActual(NodoBloque bloque) {
		bloqueActual = bloque;
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
	
	public void chequearAST() throws ExcepcionSemantica {
		for (NodoBloque bloque : ast) {
			bloque.chequear();
		}
	}
	
	public void generarCodigo() {
		callMain();
		primitivaHeapInit();
		primitivaMalloc();	
		for (Clase c : clases.values()) {
			c.generarCodigo();
		}
	}
	
	private void callMain() {
		String labelMain = main.getEtiqueta();
		GeneradorCodigo.generarInstruccion(".CODE", null);
		GeneradorCodigo.generarInstruccion("PUSH simple_heap_init", null);
		GeneradorCodigo.generarInstruccion("CALL", null);
		GeneradorCodigo.generarInstruccion("PUSH " + labelMain, null);
		GeneradorCodigo.generarInstruccion("CALL", null);
		GeneradorCodigo.generarInstruccion("HALT", null);	
		GeneradorCodigo.generarLineaVacia();
	}
	
	private void primitivaHeapInit() {
		GeneradorCodigo.generarInstruccionEtiquetada("simple_heap_init", "RET 0", "Retorna inmediatamente");	
		GeneradorCodigo.generarLineaVacia();
	}
	
	private void primitivaMalloc() {
		GeneradorCodigo.generarInstruccionEtiquetada("simple_malloc", "LOADFP", "Inicialización unidad");
		GeneradorCodigo.generarInstruccion("LOADSP", null);
		GeneradorCodigo.generarInstruccion("STOREFP", "Finaliza inicialización del RA");
		GeneradorCodigo.generarInstruccion("LOADHL", "hl");
		GeneradorCodigo.generarInstruccion("DUP", "hl");
		GeneradorCodigo.generarInstruccion("PUSH 1", "1");
		GeneradorCodigo.generarInstruccion("ADD", "hl+1");
		GeneradorCodigo.generarInstruccion("STORE 4", "Guarda el resultado (puntero a primera celda de memoria");
		GeneradorCodigo.generarInstruccion("LOAD 3", "Carga la cantidad de celdas a alojar");
		GeneradorCodigo.generarInstruccion("ADD", null);
		GeneradorCodigo.generarInstruccion("STOREHL", "Mueve el heap limit (hl). Expande el heap");
		GeneradorCodigo.generarInstruccion("STOREFP", null);
		GeneradorCodigo.generarInstruccion("RET 1", "Retorna eliminando el parámetro");	
		GeneradorCodigo.generarLineaVacia();
	}
	
	

}
