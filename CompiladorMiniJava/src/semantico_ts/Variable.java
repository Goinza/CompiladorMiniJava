package semantico_ts;

import traduccion.Offset;

public interface Variable extends Offset {
	
	public boolean esInstancia();
	
	public boolean esLocal();

}
