package semantico_ts;

import main.Token;

public class TipoVoid extends Tipo {
	
	public TipoVoid(Token token) {
		this.token = token;
		this.nombre = token.getLexema();
	}
	
	public TipoVoid() {		
		this.nombre = "void";
	}

	public void verificarDeclaracion() throws ExcepcionSemantica {
		//El tipo void es siempre válido, en el sentido de que siempre existe como posible tipo de entidad
		//Verificar que un atributo o parámetro no es responsabilidad de esta clase o metodo
	}
	
}
