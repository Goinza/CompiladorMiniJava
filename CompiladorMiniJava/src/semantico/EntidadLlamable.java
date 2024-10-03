package semantico;

import java.util.Map;

public interface EntidadLlamable {
	
	public Iterable<Parametro> getParametros();
	
	public Parametro getParametro(String nombre);
	
	public void agregarParametro(Parametro p) throws ExcepcionSemantica;

}
