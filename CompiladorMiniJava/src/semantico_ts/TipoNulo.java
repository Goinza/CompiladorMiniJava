package semantico_ts;

public class TipoNulo extends Tipo {
	
	public TipoNulo() {
		this.nombre = "null";
	}

	@Override
	public boolean conformaCon(Tipo t) throws ExcepcionSemantica {
		return true;
	}

	@Override
	public void verificarDeclaracion() throws ExcepcionSemantica {
	}

}
