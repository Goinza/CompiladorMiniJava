package semantico_ts;

import java.util.Map;

public class ClaseObject extends Clase {

	public ClaseObject() throws ExcepcionSemantica {
		super("Object");
		agregarMetodosPredefinidos();
		estaConsolidada = true;
	}
	
	private void agregarMetodosPredefinidos() throws ExcepcionSemantica {
		Metodo m = new Metodo("debugPrint", new TipoVoid(), true);
		Parametro p = new Parametro("i", new TipoEntero());
		m.agregarParametro(p);
		this.agregarMetodo(m);
	}
	
	public void verificarDeclaracion() throws ExcepcionSemantica {
	}
	
	public void verificarHerenciaCircular(Map<String, Clase> visitados) throws ExcepcionSemantica {
	}

}
