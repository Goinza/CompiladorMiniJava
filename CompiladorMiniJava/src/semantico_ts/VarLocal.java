package semantico_ts;

import main.Token;
import traduccion.Offset;

public class VarLocal implements Offset {

	private Token token;
	private Tipo tipo;
	private int offset;
	
	public VarLocal(Token token, Tipo tipo) {
		this.token = token;
		this.tipo = tipo;
	}
	
	public Token getToken() {
		return token;
	}
	
	public Tipo getTipo() {
		return tipo;
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
