package semantico;

import java.util.Map;

public interface EntidadLlamable {
	
	public Map<String, Parametro> getParametros();
	
	public void agregarParametro(Parametro p);

}
