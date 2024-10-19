package semantico_ts;

public abstract class Tipo extends EntidadDeclarada {
	
	
	public abstract boolean conformaCon(Tipo t) throws ExcepcionSemantica;
	

}
