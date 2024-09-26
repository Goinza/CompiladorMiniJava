package sintactico;

import java.util.Arrays;
import java.util.List;

import lexico.AnalizadorLexico;
import lexico.ExcepcionLexica;
import main.Token;
import semantico.Atributo;
import semantico.Clase;
import semantico.Constructor;
import semantico.ExcepcionSemantica;
import semantico.Metodo;
import semantico.Parametro;
import semantico.TablaSimbolos;
import semantico.Tipo;
import semantico.TipoBooleano;
import semantico.TipoCaracter;
import semantico.TipoClase;
import semantico.TipoEntero;
import semantico.TipoVoid;

public class AnalizadorSintactico {

	private AnalizadorLexico analizadorLexico;
	private Token tokenActual;
	private TablaSimbolos ts;
	
	public AnalizadorSintactico(AnalizadorLexico al) throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		analizadorLexico = al;
		tokenActual = analizadorLexico.getNextToken();
		ts = TablaSimbolos.getTabla();
		inicial();
	}
	
	private void match(String nombreToken) throws ExcepcionLexica, ExcepcionSintactica {
		if (nombreToken.equals(tokenActual.getTipoToken())) {
			tokenActual = analizadorLexico.getNextToken();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, nombreToken);
		}
	}
	
	private void inicial() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		listaClases();
		match("EOF");
	}
	
	private void listaClases() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		if (tokenActual.getTipoToken().equals("wordclass")) {
			clase();
			listaClases();
		}
		else {
			
		}
	}
	
	private void clase() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		match("wordclass");
		Token token = tokenActual;
		match("idClase");
		Clase c = new Clase(token);
		ts.setClaseActual(c);
		ts.agregarClase(c);
		String padre = herenciaOpcional();
		c.setPadre(padre);
		match("llaveInicio");
		listaMiembros();
		match("llaveFin");
	}
	
	private String herenciaOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		String clase;
		if (tokenActual.getTipoToken().equals("wordextends")) {
			match("wordextends");
			Token token = tokenActual;
			match("idClase");			
			clase = token.getLexema();
		}
		else {
			clase = "Object";
		}
		
		return clase;
	}
	
	private void listaMiembros() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
		List<String> primerosMiembro = Arrays.asList("wordstatic", "wordboolean", "wordchar", "wordint", "wordvoid", "wordpublic", "idClase");
		if (primerosMiembro.contains(tokenActual.getTipoToken())) {
			miembro();
			listaMiembros();
		}
		else {
			
		}
	}
	
	private void miembro() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
		List<String> primerosUnidad = Arrays.asList("wordstatic", "wordboolean", "wordchar", "wordint", "wordvoid", "idClase");
		if (primerosUnidad.contains(tokenActual.getTipoToken())) {
			unidad();
		}
		else if (tokenActual.getTipoToken().equals("wordpublic")) {
			constructor();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "static, boolean, char, int, void o public");
		}
	}
	
	private void unidad() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		boolean esEstatico = estaticoOpcional();
		Tipo tipo = tipoMiembro();
		Token token = tokenActual;
		match("idMetVar");
		boolean esMetodo = contUnidad();
		
		if (esMetodo) {
			Metodo m = new Metodo(token, tipo, esEstatico);
			ts.getClaseActual().agregarMetodo(m);
			ts.setMetodoActual(m);
		}
		else {
			Atributo a = new Atributo(token, tipo, esEstatico);
			ts.getClaseActual().agregarAtributo(a);
		}
	}
	
	private boolean contUnidad() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		boolean esMetodo;
		if (tokenActual.getTipoToken().equals("puntoComa")) {
			esMetodo = false; 
			match("puntoComa");
		}
		else if (tokenActual.getTipoToken().equals("parentesisInicio")) {
			esMetodo = true;
			argsFormales();
			bloque();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "; o (");
		}
		
		return esMetodo;
	}
	
	private void constructor() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		match("wordpublic");
		Token token = tokenActual;
		match("idClase");
		Constructor c = new Constructor(token);
		ts.getClaseActual().setConstructor(c);
		ts.setMetodoActual(c);
		argsFormales();
		bloque();
	}
	
	private Tipo tipoMiembro() throws ExcepcionSintactica, ExcepcionLexica {
		Tipo tipo;
		List<String> primerosTipo = Arrays.asList("wordboolean", "wordchar", "wordint", "idClase");
		if (primerosTipo.contains(tokenActual.getTipoToken())) {
			tipo = tipo();
		}
		else if (tokenActual.getTipoToken().equals("wordvoid")) {
			Token token = tokenActual;
			match("wordvoid");
			tipo = new TipoVoid(token);
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "boolean, char, int, void o identificador de clase");
		}
		
		return tipo;
	}
	
	private Tipo tipo() throws ExcepcionSintactica, ExcepcionLexica {
		Tipo tipo;
		List<String> primerosTipoPrimitivo = Arrays.asList("wordboolean", "wordchar", "wordint");
		if (primerosTipoPrimitivo.contains(tokenActual.getTipoToken())) {
			tipo = tipoPrimitivo();
		}
		else if (tokenActual.getTipoToken().equals("idClase")) {
			Token token = tokenActual;
			match("idClase");
			tipo = new TipoClase(token);
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "boolean, char, int o identificador de clase");
		}
		
		return tipo;
	}
	
	private Tipo tipoPrimitivo() throws ExcepcionLexica, ExcepcionSintactica {
		Tipo tipo;
		Token token;
		if (tokenActual.getTipoToken().equals("wordboolean")) {
			token = tokenActual;
			match("wordboolean");
			tipo = new TipoBooleano(token);
		}
		else if (tokenActual.getTipoToken().equals("wordchar")) {
			token = tokenActual;
			match("wordchar");
			tipo = new TipoCaracter(token);
		}
		else if (tokenActual.getTipoToken().equals("wordint")) {
			token = tokenActual;
			match("wordint");
			tipo = new TipoEntero(token);
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "boolean, char o int");
		}
		
		return tipo;
	}
	
	private boolean estaticoOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		boolean esEstatico;
		if (tokenActual.getTipoToken().equals("wordstatic")) {
			match("wordstatic");
			esEstatico = true;
		}
		else {
			esEstatico = false;
		}
		
		return esEstatico;
	}
	
	private void argsFormales() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		match("parentesisInicio");
		listaArgsFormalesOpcional();
		match("parentesisFin");
	}
	
	private void listaArgsFormalesOpcional() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		List<String> primerosListaArgsFormales = Arrays.asList("wordboolean", "wordchar", "wordint", "idClase");
		if (primerosListaArgsFormales.contains(tokenActual.getTipoToken())) {
			listaArgsFormales();
		}
		else {
			
		}
	}
	
	private void listaArgsFormales() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		argFormal();
		contListaArgsFormales();
	}
	
	private void contListaArgsFormales() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		if (tokenActual.getTipoToken().equals("coma")) {
			match("coma");
			listaArgsFormales();
		}
		else {
			
		}
	}
	
	private void argFormal() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		Tipo tipo = tipo();
		Token token = tokenActual;
		match("idMetVar");
		Parametro p = new Parametro(token, tipo);
		ts.getMetodoActual().agregarParametro(p);
	}
	
	private void bloque() throws ExcepcionLexica, ExcepcionSintactica {
		match("llaveInicio");
		listaSentencias();
		match("llaveFin");
	}
	
	private void listaSentencias() throws ExcepcionLexica, ExcepcionSintactica {
		List<String> primerosSentencia = Arrays.asList("puntoComa", "opSuma", "opResta", "opNegacion", "wordtrue", "wordfalse", "wordnull", "wordthis", "wordnew", "parentesisInicio", "wordvar", "wordreturn", "wordbreak", "wordif", "wordwhile", "wordswitch", "llaveInicio", "intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase");
		if (primerosSentencia.contains(tokenActual.getTipoToken())) {
			sentencia();
			listaSentencias();
		}
		else {
			
		}
	}
	
	private void sentencia() throws ExcepcionLexica, ExcepcionSintactica {
		List<String> primerosExpresion = Arrays.asList("opSuma", "opResta", "opNegacion", "wordtrue", "wordfalse", "wordnull", "wordthis", "wordnew", "parentesisInicio", "intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase");
		if (tokenActual.getTipoToken().equals("puntoComa")) {
			match("puntoComa");
		}
		else if (primerosExpresion.contains(tokenActual.getTipoToken())) {
			expresion();
			match("puntoComa");
		}
		else if (tokenActual.getTipoToken().equals("wordvar")) {
			varLocal();
			match("puntoComa");
		}
		else if (tokenActual.getTipoToken().equals("wordreturn")) {
			returnNT();
			match("puntoComa");
		}
		else if (tokenActual.getTipoToken().equals("wordbreak")) {
			breakNT();
			match("puntoComa");
		}
		else if (tokenActual.getTipoToken().equals("wordif")) {
			ifNT();
		}
		else if (tokenActual.getTipoToken().equals("wordwhile")) {
			whileNT();
		}
		else if (tokenActual.getTipoToken().equals("wordswitch")) {
			switchNT();
		}
		else if (tokenActual.getTipoToken().equals("llaveInicio")) {
			bloque();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "+, -, !, true, false, null, this, new, (, literal de entero, literal de caracter, literal de string, identificador de metodo/variable, identificador de clase, var, return, break, if, while, switch o {");
		}
	}
	
	private void varLocal() throws ExcepcionLexica, ExcepcionSintactica {
		match("wordvar");
		match("idMetVar");
		match("asignacion");
		expresionCompuesta();
	}
	
	private void returnNT() throws ExcepcionLexica, ExcepcionSintactica {
		match("wordreturn");
		expresionOpcional();
	}
	
	private void breakNT() throws ExcepcionLexica, ExcepcionSintactica {
		match("wordbreak");
	}
	
	private void expresionOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		List<String> primerosExpresion = Arrays.asList("opSuma", "opResta", "opNegacion", "wordtrue", "wordfalse", "wordnull", "wordthis", "wordnew", "parentesisInicio", "intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase");
		if (primerosExpresion.contains(tokenActual.getTipoToken())) {
			expresion();
		}
		else {
			
		}
	}
	
	private void ifNT() throws ExcepcionLexica, ExcepcionSintactica {
		match("wordif");
		match("parentesisInicio");
		expresion();
		match("parentesisFin");
		sentencia();
		elseOpcional();
	}
	
	private void elseOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getTipoToken().equals("wordelse")) {
			match("wordelse");
			sentencia();	
		}
		else {
			
		}
	}
	
	private void whileNT() throws ExcepcionLexica, ExcepcionSintactica {
		match("wordwhile");
		match("parentesisInicio");
		expresion();
		match("parentesisFin");
		sentencia();
	}
	
	private void switchNT() throws ExcepcionLexica, ExcepcionSintactica {
		match("wordswitch");
		match("parentesisInicio");
		expresion();
		match("parentesisFin");
		match("llaveInicio");
		listaSentenciasSwitch();
		match("llaveFin");
	}
	
	private void listaSentenciasSwitch() throws ExcepcionLexica, ExcepcionSintactica {
		List<String> primerosSwitch = Arrays.asList("wordcase", "worddefault");
		if (primerosSwitch.contains(tokenActual.getTipoToken())) {
			sentenciaSwitch();
			listaSentenciasSwitch();
		}
		else {
			
		}
	}
	
	private void sentenciaSwitch() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getTipoToken().equals("wordcase")) {
			match("wordcase");
			literalPrimitivo();
			match("dosPuntos");
			sentenciaOpcional();
		}
		else if (tokenActual.getTipoToken().equals("worddefault")) {
			match("worddefault");
			match("dosPuntos");
			sentencia();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "case o default");
		}
	}
	
	private void sentenciaOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		List<String> primerosSentencia = Arrays.asList("puntoComa", "opSuma", "opResta", "opNegacion", "wordtrue", "wordfalse", "wordnull", "wordthis", "wordnew", "parentesisInicio", "wordvar", "wordreturn", "wordbreak", "wordif", "wordwhile", "wordswitch", "llaveInicio", "intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase");
		if (primerosSentencia.contains(tokenActual.getTipoToken())) {
			sentencia();
		}
		else {
			
		}
	}
	
	private void expresion() throws ExcepcionLexica, ExcepcionSintactica {
		expresionCompuesta();
		extensionExpresion();
	}
	
	private void extensionExpresion() throws ExcepcionLexica, ExcepcionSintactica {
		List<String> primerosAsignacion = Arrays.asList("asignacion", "asignacionSuma", "asignacionResta");
		if (primerosAsignacion.contains(tokenActual.getTipoToken())) {
			operadorAsignacion();
			expresionCompuesta();
		}
		else {
			
		}
	}
	
	private void operadorAsignacion() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getTipoToken().equals("asignacion")) {
			match("asignacion");
		}
		else if (tokenActual.getTipoToken().equals("asignacionSuma")) {
			match("asignacionSuma");
		}
		else if (tokenActual.getTipoToken().equals("asignacionResta")) {
			match("asignacionResta");
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "=, += o -=");
		}
	}
	
	private void expresionCompuesta() throws ExcepcionLexica, ExcepcionSintactica {
		expresionBasica();
		extensionExpresionCompuesta();
	}
	
	private void extensionExpresionCompuesta() throws ExcepcionLexica, ExcepcionSintactica {
		List<String> primerosOperadorBinario = Arrays.asList("opOr", "opAnd", "opIgual", "opDistinto", "opMenor", "opMayor", "opMenorIgual", "opMayorIgual", "opSuma", "opResta", "opMultiplicacion", "opDivision", "opModulo");
		if (primerosOperadorBinario.contains(tokenActual.getTipoToken())) {
			operadorBinario();
			expresionBasica();
			extensionExpresionCompuesta();
		}
		else {
			
		}
	}
	
	private void operadorBinario() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getTipoToken().equals("opOr")) {
			match("opOr");
		}
		else if (tokenActual.getTipoToken().equals("opAnd")) {
			match("opAnd");
		}
		else if (tokenActual.getTipoToken().equals("opIgual")) {
			match("opIgual");
		}
		else if (tokenActual.getTipoToken().equals("opDistinto")) {
			match("opDistinto");
		}
		else if (tokenActual.getTipoToken().equals("opMenor")) {
			match("opMenor");
		}
		else if (tokenActual.getTipoToken().equals("opMayor")) {
			match("opMayor");
		}
		else if (tokenActual.getTipoToken().equals("opMenorIgual")) {
			match("opMenorIgual");
		}
		else if (tokenActual.getTipoToken().equals("opMayorIgual")) {
			match("opMayorIgual");
		}
		else if (tokenActual.getTipoToken().equals("opSuma")) {
			match("opSuma");
		}
		else if (tokenActual.getTipoToken().equals("opResta")) {
			match("opResta");
		}
		else if (tokenActual.getTipoToken().equals("opMultiplicacion")) {
			match("opMultiplicacion");
		}
		else if (tokenActual.getTipoToken().equals("opDivision")) {
			match("opDivision");
		}
		else if (tokenActual.getTipoToken().equals("opModulo")) {
			match("opModulo");
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "||, &&, ==, !=, <, >, <=, >=, +, -, *, / o %");
		}
	}
	
	private void expresionBasica() throws ExcepcionSintactica, ExcepcionLexica {
		List<String> primerosOperadorUnario = Arrays.asList("opSuma", "opResta", "opNegacion");
		List<String> primerosOperando = Arrays.asList("wordtrue", "wordfalse", "wordnull", "wordthis", "wordnew", "parentesisInicio", "intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase");
		if (primerosOperadorUnario.contains(tokenActual.getTipoToken())) {
			operadorUnario();
			operando();
		}
		else if (primerosOperando.contains(tokenActual.getTipoToken())) {
			operando();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "+, -, !, true, false, null, this, new, (, literal de entero, literal de caracter, literal de string, identificador de metodo/variable o identificador de clase");
		}
	}
	
	private void operadorUnario() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getTipoToken().equals("opSuma")) {
			match("opSuma");
		}
		else if (tokenActual.getTipoToken().equals("opResta")) {
			match("opResta");
		}
		else if (tokenActual.getTipoToken().equals("opNegacion")) {
			match("opNegacion");
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "+, - o !");
		}
	}
	
	private void operando() throws ExcepcionSintactica, ExcepcionLexica {
		List<String> primerosLiteral = Arrays.asList("wordtrue", "wordfalse", "wordnull", "intLiteral", "charLiteral", "stringLiteral");
		List<String> primerosAcceso = Arrays.asList("wordthis", "wordnew", "parentesisInicio", "idMetVar", "idClase");
		if (primerosLiteral.contains(tokenActual.getTipoToken())) {
			literal();
		}
		else if (primerosAcceso.contains(tokenActual.getTipoToken())) {
			acceso();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "true, fale, null, literal de entero, literal de caracter, literal de string, this, new, (, identificador de metodo/variable, identificador de clase");
		}
	}
	
	private void literal() throws ExcepcionSintactica, ExcepcionLexica {
		List<String> primerosLiteralPrimitivo = Arrays.asList("wordtrue", "wordfalse", "intLiteral", "charLiteral");
		List<String> primerosLiteralObjeto = Arrays.asList("wordnull", "stringLiteral");
		if (primerosLiteralPrimitivo.contains(tokenActual.getTipoToken())) {
			literalPrimitivo();
		}
		else if (primerosLiteralObjeto.contains(tokenActual.getTipoToken())) {
			literalObjeto();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "true, false, literal de entero, literal de caracter, null o literal de string");
		}
	}
	
	private void literalPrimitivo() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getTipoToken().equals("wordtrue")) {
			match("wordtrue");
		}
		else if (tokenActual.getTipoToken().equals("wordfalse")) {
			match("wordfalse");
		}
		else if (tokenActual.getTipoToken().equals("intLiteral")) {
			match("intLiteral");
		}
		else if (tokenActual.getTipoToken().equals("charLiteral")) {
			match("charLiteral");
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "true, false, literal de entero o literal de caracter");
		}
	}
	
	private void literalObjeto() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getTipoToken().equals("wordnull")) {
			match("wordnull");
		}
		else if (tokenActual.getTipoToken().equals("stringLiteral")) {
			match("stringLiteral");
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "null o literal de string");
		}
	}
	
	private void acceso() throws ExcepcionSintactica, ExcepcionLexica {
		primario();
		encadenadoOpcional();
	}
	
	private void primario() throws ExcepcionSintactica, ExcepcionLexica {
		if (tokenActual.getTipoToken().equals("wordthis")) {
			accesoThis();
		}
		else if (tokenActual.getTipoToken().equals("idMetVar")) {
			accesoUnidad();
		}
		else if (tokenActual.getTipoToken().equals("wordnew")) {
			accesoConstructor();
		}
		else if (tokenActual.getTipoToken().equals("idClase")) {
			accesoMetodoEstatico();
		}
		else if (tokenActual.getTipoToken().equals("parentesisInicio")) {
			expresionParentizada();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "this, identificador de metodo/variable, new, identificador de clase o (");
		}
	}
	
	private void accesoThis() throws ExcepcionLexica, ExcepcionSintactica {
		match("wordthis");
	}
	
	private void accesoUnidad() throws ExcepcionLexica, ExcepcionSintactica {
		match("idMetVar");
		argsOpcionales();
	}
	
	private void accesoConstructor() throws ExcepcionLexica, ExcepcionSintactica {
		match("wordnew");
		match("idClase");
		argsActuales();
	}
	
	private void expresionParentizada() throws ExcepcionLexica, ExcepcionSintactica {
		match("parentesisInicio");
		expresion();
		match("parentesisFin");
	}
	
	private void accesoMetodoEstatico() throws ExcepcionLexica, ExcepcionSintactica {
		match("idClase");
		match("punto");
		match("idMetVar");
		argsActuales();
	}
	
	
	private void argsActuales() throws ExcepcionLexica, ExcepcionSintactica {
		match("parentesisInicio");
		listaExpsOpcional();
		match("parentesisFin");
	}
	
	private void listaExpsOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		List<String> primerosListaExps = Arrays.asList("opSuma", "opResta", "opNegacion", "wordtrue", "wordfalse", "wordnull", "wordthis", "wordnew", "parentesisInicio", "intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase");
		if (primerosListaExps.contains(tokenActual.getTipoToken())) {
			listaExps();
		}
		else {
			
		}
	}
	
	private void listaExps() throws ExcepcionLexica, ExcepcionSintactica {
		expresion();
		contListaExps();
	}
	
	private void contListaExps() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getTipoToken().equals("coma")) {
			match("coma");
			listaExps();
		}
		else {
			
		}
	}
	
	private void encadenadoOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getTipoToken().equals("punto")) {
			match("punto");
			match("idMetVar");
			argsOpcionales();
			encadenadoOpcional();
		}
		else {
			
		}		
	}
	
	private void argsOpcionales() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getTipoToken().equals("parentesisInicio")) {
			argsActuales();
		}
		else {
			
		}
	}
	
	
}
