package semantico_ts;

import main.Token;

public class TipoClase extends Tipo {
	
	public TipoClase(Token token) {
		this.token = token;
		this.nombre = token.getLexema();
	}
	
	public TipoClase(String nombre) {
		this.nombre = nombre;
	}

	public void verificarDeclaracion() throws ExcepcionSemantica {
		if (TablaSimbolos.getTabla().getClase(nombre) == null) {
			throw new ExcepcionSemantica(token, "Tipo de clase " + nombre + " no existe");
		}
	}

	public boolean conformaCon(Tipo t) throws ExcepcionSemantica {
		Clase clase = TablaSimbolos.getTabla().getClase(nombre);
		Clase clase2 = TablaSimbolos.getTabla().getClase(t.getNombre());
		if (clase2 != null) {
			return clase.esDescendienteDe(clase2);
		}
		return false;
	}

}
