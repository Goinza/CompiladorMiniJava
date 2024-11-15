package semantico_ast;

public abstract class NodoExpCompuesta extends NodoExpresion {
	
	protected boolean esLadoIzqAsign = false;
	
	public boolean esLadoIzquierdoAsignacion() {
		return esLadoIzqAsign;
	}
	
	public void setEsLadoIzquierdoAsignacion() {
		esLadoIzqAsign = true;
	}

}
