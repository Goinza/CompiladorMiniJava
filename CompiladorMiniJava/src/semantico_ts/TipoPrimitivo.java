package semantico_ts;

public abstract class TipoPrimitivo extends Tipo {
	
	public void verificarDeclaracion() throws ExcepcionSemantica {
		//Los tipos primitivos siempre son validos
	}
	
	public boolean conformaCon(Tipo t) {
		return this.equals(t);
	}

}
