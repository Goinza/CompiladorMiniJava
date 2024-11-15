package semantico_ts;

import main.Token;
import traduccion.Etiquetable;
import traduccion.Offset;

public class Atributo extends EntidadDeclarada implements Etiquetable, Variable{

	private Tipo tipo;
	private boolean esEstatico;
	private int offset;
	private Clase claseOriginal;
	
	public Atributo(Token token, Tipo tipo, boolean esEstatico, Clase claseOriginal) {
		this(token.getLexema(), tipo, esEstatico, claseOriginal);
		this.token = token;		
	}
	
	public Atributo(String nombre, Tipo tipo, boolean esEstatico, Clase claseOriginal) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.esEstatico = esEstatico;
		this.claseOriginal = claseOriginal;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public boolean esEstatico() {
		return esEstatico;
	}

	public void verificarDeclaracion() throws ExcepcionSemantica {
		if (tipo.getNombre().equals("void")) {
			throw new ExcepcionSemantica(tipo.getToken(), "Los atributos no pueden tener tipo void");
		}
		tipo.verificarDeclaracion();
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public String getEtiqueta() {
		return esEstatico ? "lblAtrEstatico" + token.getLexema() + "@" + claseOriginal.getNombre() : "";
	}

	@Override
	public boolean esInstancia() {
		return !esEstatico;
	}

	@Override
	public boolean esLocal() {
		return false;
	}
	
}
