package sintactico;

import lexico.AnalizadorLexico;
import lexico.ExcepcionLexica;
import main.Token;

public class AnalizadorSintactico {

	private AnalizadorLexico analizadorLexico;
	private Token tokenActual;
	
	public AnalizadorSintactico(AnalizadorLexico al) throws ExcepcionLexica {
		analizadorLexico = al;
		tokenActual = analizadorLexico.getNextToken();
		inicial();
	}
	
	private void match(String nombreToken) throws ExcepcionLexica, ExcepcionSintactica {
		if (nombreToken.equals(tokenActual.getLexema())) {
			tokenActual = analizadorLexico.getNextToken();
		}
		else {
			throw new ExcepcionSintactica();
		}
	}
	
	private void inicial() {
		
	}
	
	private void listaClases() {
		
	}
	
	private void clase() {
		
	}
	
	private void herenciaOpcional() {
		
	}
	
	private void listaMiembros() {
		
	}
	
	private void miembro() {
		
	}
	
	private void unidad() {
		
	}
	
	private void constructor() {
		
	}
	
	private void tipoMiembro() {
		
	}
	
	private void tipo() {
		
	}
	
	private void tipoPrimitivo() {
		
	}
	
	private void estaticoOpcional() {
		
	}
	
	private void argsFormales() {
		
	}
	
	private void listaArgsFormalesOpcional() {
		
	}
	
	private void listaArgsFormales() {
		
	}
	
	private void contListaArgsFormales() {
		
	}
	
	private void argFormal() {
		
	}
	
	private void bloque() {
		
	}
	
	private void listaSentencia() {
		
	}
	
	private void sentencia() {
		
	}
	
	private void varLocal() {
		
	}
	
	private void returnNT() {
		
	}
	
	private void breakNT() {
		
	}
	
	private void expresionOpcional() {
		
	}
	
	private void ifNT() {
		
	}
	
	private void elseNT() {
		
	}
	
	private void whileNT() {
		
	}
	
	private void switchNT() {
		
	}
	
	private void listaSentenciasSwitch() {
		
	}
	
	private void sentenciaSwitch() {
		
	}
	
	private void sentenciaOpcional() {
		
	}
	
	private void expresion() {
		
	}
	
	private void extensionExpresion() {
		
	}
	
	private void operadorAsignacion() {
		
	}
	
	private void expresionCompuesta() {
		
	}
	
	private void extensionExpresionCompuesta() {
		
	}
	
	private void expresionBasica() {
		
	}
	
	private void operadorUnario() {
		
	}
	
	private void operando() {
		
	}
	
	private void literal() {
		
	}
	
	private void literalPrimitivo() {
		
	}
	
	private void literalObjeto() {
		
	}
	
	private void acceso() {
		
	}
	
	private void primario() {
		
	}
	
	private void accesoThis() {
		
	}
	
	private void accesoUnidad() {
		
	}
	
	private void accesoConstructor() {
		
	}
	
	private void expresionParentizada() {
		
	}
	
	private void accesoMetodoEstatico() {
		
	}
	
	
	private void argsActuales() {
		
	}
	
	private void listaExpsOpcional() {
		
	}
	
	private void listaExps() {
		
	}
	
	private void contListaExps() {
		
	}
	
	private void encadenadoOpcional() {
		
	}
	
	private void argsOpcionales() {
		
	}
	
	
}
