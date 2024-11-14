package semantico_ts;

import main.Token;
import traduccion.Offset;

public class Parametro extends EntidadDeclarada implements Offset {

	private Tipo tipo;
	private int offset;
	
	public Parametro(Token token, Tipo tipo) {
		this(token.getLexema(), tipo);
		this.token = token;
	}
	
	public Parametro(String nombre, Tipo tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
	}
	
	public Tipo getTipo() {
		return tipo;
	}

	public void verificarDeclaracion() throws ExcepcionSemantica {
		if (tipo.getNombre().equals("void")) {
			throw new ExcepcionSemantica(tipo.getToken(), "Los parámetros no pueden tener tipo void");
		}
		tipo.verificarDeclaracion();
	}
	
	public boolean equals(Parametro p) {
		return tipo.equals(p.getTipo());
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}
