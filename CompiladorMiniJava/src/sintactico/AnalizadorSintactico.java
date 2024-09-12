package sintactico;

import java.util.Arrays;

import lexico.AnalizadorLexico;
import lexico.ExcepcionLexica;
import main.Token;

public class AnalizadorSintactico {

	private AnalizadorLexico analizadorLexico;
	private Token tokenActual;
	
	public AnalizadorSintactico(AnalizadorLexico al) throws ExcepcionLexica, ExcepcionSintactica {
		analizadorLexico = al;
		tokenActual = analizadorLexico.getNextToken();
		inicial();
	}
	
	private void match(String nombreToken) throws ExcepcionLexica, ExcepcionSintactica {
		if (nombreToken.equals(tokenActual.getLexema()) || nombreToken.equals(tokenActual.getTipoToken())) {
			tokenActual = analizadorLexico.getNextToken();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, nombreToken);
		}
	}
	
	private void inicial() throws ExcepcionLexica, ExcepcionSintactica {
		listaClases();
	}
	
	private void listaClases() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals("class")) {
			clase();
			listaClases();
		}
		else {
			
		}
	}
	
	private void clase() throws ExcepcionLexica, ExcepcionSintactica {
		match("class");
		match("idClase");
		herenciaOpcional();
		match("{");
		listaMiembros();
		match("}");
	}
	
	private void herenciaOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals("extends")) {
			match("extends");
			match("idClase");			
		}
		else {
			
		}
	}
	
	private void listaMiembros() throws ExcepcionSintactica, ExcepcionLexica {
		if (Arrays.asList("static", "boolean", "char", "int", "void", "public").contains(tokenActual.getLexema()) || tokenActual.getTipoToken().equals("idClase")) {
			miembro();
			listaMiembros();
		}
		else {
			
		}
	}
	
	private void miembro() throws ExcepcionSintactica, ExcepcionLexica {
		if (Arrays.asList("static", "boolean", "char", "int", "void").contains(tokenActual.getLexema()) || tokenActual.getTipoToken().equals("idClase")) {
			unidad();
		}
		else if (tokenActual.getLexema().equals("public")) {
			constructor();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "static, boolean, char, int, void o public");
		}
	}
	
	private void unidad() throws ExcepcionLexica, ExcepcionSintactica {
		estaticoOpcional();
		tipoMiembro();
		match("idMetVar");
		contUnidad();
	}
	
	private void contUnidad() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals(";")) {
			match(";");
		}
		else if (tokenActual.getLexema().equals("(")) {
			argsFormales();
			bloque();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "; o (");
		}
	}
	
	private void constructor() throws ExcepcionLexica, ExcepcionSintactica {
		match("public");
		match("idClase");
		argsFormales();
		bloque();
	}
	
	private void tipoMiembro() throws ExcepcionSintactica, ExcepcionLexica {
		if (Arrays.asList("boolean", "char", "int").contains(tokenActual.getLexema()) || tokenActual.getTipoToken().equals("idClase")) {
			tipo();
		}
		else if (tokenActual.getLexema().equals("void")) {
			match("void");
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "boolean, char, int, idClase o void");
		}
	}
	
	private void tipo() throws ExcepcionSintactica, ExcepcionLexica {
		if (Arrays.asList("boolean", "char", "int").contains(tokenActual.getLexema())) {
			tipoPrimitivo();
		}
		else if (tokenActual.getTipoToken().equals("idClase")) {
			match("idClase");
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "boolean, char, int o idClase");
		}
	}
	
	private void tipoPrimitivo() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals("boolean")) {
			match("boolean");
		}
		else if (tokenActual.getLexema().equals("char")) {
			match("char");
		}
		else if (tokenActual.getLexema().equals("int")) {
			match("int");
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "boolean, char o int");
		}
	}
	
	private void estaticoOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals("static")) {
			match("static");
		}
		else {
			
		}
	}
	
	private void argsFormales() throws ExcepcionLexica, ExcepcionSintactica {
		match("(");
		listaArgsFormalesOpcional();
		match(")");
	}
	
	private void listaArgsFormalesOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		if (Arrays.asList("boolean", "char", "int").contains(tokenActual.getLexema()) || tokenActual.getTipoToken().equals("idClase")) {
			listaArgsFormales();
		}
		else {
			
		}
	}
	
	private void listaArgsFormales() throws ExcepcionLexica, ExcepcionSintactica {
		argFormal();
		contListaArgsFormales();
	}
	
	private void contListaArgsFormales() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals(",")) {
			match(",");
			listaArgsFormales();
		}
		else {
			
		}
	}
	
	private void argFormal() throws ExcepcionLexica, ExcepcionSintactica {
		tipo();
		match("idMetVar");
	}
	
	private void bloque() throws ExcepcionLexica, ExcepcionSintactica {
		match("{");
		listaSentencias();
		match("}");
	}
	
	private void listaSentencias() throws ExcepcionLexica, ExcepcionSintactica {
		if (Arrays.asList(";", "+", "-", "!", "true", "false", "null", "this", "new", "(", "var", "return", "break", "if", "while", "switch", "{").contains(tokenActual.getLexema())
				|| Arrays.asList("intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase").contains(tokenActual.getTipoToken())) {
			sentencia();
			listaSentencias();
		}
		else {
			
		}
	}
	
	private void sentencia() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals(";")) {
			match(";");
		}
		else if (Arrays.asList("+", "-", "!", "true", "false", "null", "this", "new", "(").contains(tokenActual.getLexema())
				|| Arrays.asList("intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase").contains(tokenActual.getTipoToken())) {
			expresion();
			match(";");
		}
		else if (tokenActual.getLexema().equals("var")) {
			varLocal();
			match(";");
		}
		else if (tokenActual.getLexema().equals("return")) {
			returnNT();
			match(";");
		}
		else if (tokenActual.getLexema().equals("break")) {
			breakNT();
			match(";");
		}
		else if (tokenActual.getLexema().equals("if")) {
			ifNT();
		}
		else if (tokenActual.getLexema().equals("while")) {
			whileNT();
		}
		else if (tokenActual.getLexema().equals("switch")) {
			switchNT();
		}
		else if (tokenActual.getLexema().equals("{")) {
			bloque();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "+, -, !, true, false, null, this, new, (, intLiteral, charLiteral, stringLiteral, idMetVar, idClase, var, return, break, if, while, switch o {");
		}
	}
	
	private void varLocal() throws ExcepcionLexica, ExcepcionSintactica {
		match("var");
		match("idMetVar");
		match("=");
		expresionCompuesta();
	}
	
	private void returnNT() throws ExcepcionLexica, ExcepcionSintactica {
		match("return");
		expresionOpcional();
	}
	
	private void breakNT() throws ExcepcionLexica, ExcepcionSintactica {
		match("break");
	}
	
	private void expresionOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		if (Arrays.asList("+", "-", "!", "true", "false", "null", "this", "new", "(").contains(tokenActual.getLexema())
				|| Arrays.asList("intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase").contains(tokenActual.getTipoToken())) {
			expresion();
		}
		else {
			
		}
	}
	
	private void ifNT() throws ExcepcionLexica, ExcepcionSintactica {
		match("if");
		match("(");
		expresion();
		match(")");
		sentencia();
		elseNT();
	}
	
	private void elseNT() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals("else")) {
			match("else");
			sentencia();	
		}
		else {
			
		}
	}
	
	private void whileNT() throws ExcepcionLexica, ExcepcionSintactica {
		match("while");
		match("(");
		expresion();
		match(")");
		sentencia();
	}
	
	private void switchNT() throws ExcepcionLexica, ExcepcionSintactica {
		match("switch");
		match("(");
		expresion();
		match(")");
		match("{");
		listaSentenciasSwitch();
		match("}");
	}
	
	private void listaSentenciasSwitch() throws ExcepcionLexica, ExcepcionSintactica {
		if (Arrays.asList("case", "default").contains(tokenActual.getLexema())) {
			sentenciaSwitch();
			listaSentenciasSwitch();
		}
		else {
			
		}
	}
	
	private void sentenciaSwitch() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals("case")) {
			match("case");
			literalPrimitivo();
			match(":");
			sentenciaOpcional();
		}
		else if (tokenActual.getLexema().equals("default")) {
			match("default");
			match(":");
			sentencia();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "case o default");
		}
	}
	
	private void sentenciaOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		if (Arrays.asList(";", "+", "-", "!", "true", "false", "null", "this", "new", "(", "var", "return", "break", "if", "while", "switch", "{").contains(tokenActual.getLexema())
				|| Arrays.asList("intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase").contains(tokenActual.getTipoToken())) {
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
		if (Arrays.asList("=", "+=", "-+").contains(tokenActual.getLexema())) {
			operadorAsignacion();
			expresionCompuesta();
		}
		else {
			
		}
	}
	
	private void operadorAsignacion() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals("=")) {
			match("=");
		}
		else if (tokenActual.getLexema().equals("+=")) {
			match("+=");
		}
		else if (tokenActual.getLexema().equals("-=")) {
			match("-=");
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
		if (Arrays.asList("||", "&&", "==", "!=", "<", ">", "<=", ">=", "+", "-", "*", "/", "%").contains(tokenActual.getLexema())) {
			operadorBinario();
			expresionBasica();
			extensionExpresionCompuesta();
		}
		else {
			
		}
	}
	
	private void operadorBinario() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals("||")) {
			match("||");
		}
		else if (tokenActual.getLexema().equals("&&")) {
			match("&&");
		}
		else if (tokenActual.getLexema().equals("==")) {
			match("==");
		}
		else if (tokenActual.getLexema().equals("!=")) {
			match("!=");
		}
		else if (tokenActual.getLexema().equals("<")) {
			match("<");
		}
		else if (tokenActual.getLexema().equals(">")) {
			match(">");
		}
		else if (tokenActual.getLexema().equals("<=")) {
			match("<=");
		}
		else if (tokenActual.getLexema().equals(">=")) {
			match(">=");
		}
		else if (tokenActual.getLexema().equals("+")) {
			match("+");
		}
		else if (tokenActual.getLexema().equals("-")) {
			match("-");
		}
		else if (tokenActual.getLexema().equals("*")) {
			match("*");
		}
		else if (tokenActual.getLexema().equals("/")) {
			match("/");
		}
		else if (tokenActual.getLexema().equals("%")) {
			match("%");
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "||, &&, ==, !=, <, >, <=, >=, +, -, *, / o %");
		}
	}
	
	private void expresionBasica() throws ExcepcionSintactica, ExcepcionLexica {
		if (Arrays.asList("+", "-", "!").contains(tokenActual.getLexema())) {
			operadorUnario();
			operando();
		}
		else if (Arrays.asList("true", "false", "null", "this", "new", "(").contains(tokenActual.getLexema()) 
				|| Arrays.asList("intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase").contains(tokenActual.getTipoToken())) {
			operando();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "+, -, !, true, false, null, this, new, (, intLiteral, charLiteral, stringLiteral, idMetVar o idClase");
		}
	}
	
	private void operadorUnario() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals("+")) {
			match("+");
		}
		else if (tokenActual.getLexema().equals("-")) {
			match("-");
		}
		else if (tokenActual.getLexema().equals("!")) {
			match("!");
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "+, - o !");
		}
	}
	
	private void operando() throws ExcepcionSintactica, ExcepcionLexica {
		if (Arrays.asList("true", "false", "null").contains(tokenActual.getLexema()) 
				|| Arrays.asList("intLiteral", "charLiteral", "stringLiteral").contains(tokenActual.getTipoToken())) {
			literal();
		}
		else if (Arrays.asList("this", "new", "(").contains(tokenActual.getLexema()) 
				|| Arrays.asList("idMetVar", "idClase").contains(tokenActual.getTipoToken())) {
			acceso();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "true, fale, null, intLiteral, charLiteral, stringLiteral, this, new, (, idMetVar, idClase");
		}
	}
	
	private void literal() throws ExcepcionSintactica, ExcepcionLexica {
		if (Arrays.asList("true", "false").contains(tokenActual.getLexema())
				|| Arrays.asList("intLiteral", "charLiteral").contains(tokenActual.getTipoToken())) {
			literalPrimitivo();
		}
		else if (tokenActual.getLexema().equals("null") || tokenActual.getTipoToken().equals("stringLiteral")) {
			literalObjeto();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "true, false, intLiteral, charLiteral, null o stringLiteral");
		}
	}
	
	private void literalPrimitivo() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals("true")) {
			match("true");
		}
		else if (tokenActual.getLexema().equals("false")) {
			match("false");
		}
		else if (tokenActual.getTipoToken().equals("intLiteral")) {
			match("intLiteral");
		}
		else if (tokenActual.getTipoToken().equals("charLiteral")) {
			match("charLiteral");
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "true, false, intLiteral o charLiteral");
		}
	}
	
	private void literalObjeto() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals("null")) {
			match("null");
		}
		else if (tokenActual.getTipoToken().equals("stringLiteral")) {
			match("stringLiteral");
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "null o stringLiteral");
		}
	}
	
	private void acceso() throws ExcepcionSintactica, ExcepcionLexica {
		primario();
		encadenadoOpcional();
	}
	
	private void primario() throws ExcepcionSintactica, ExcepcionLexica {
		if (tokenActual.getLexema().equals("this")) {
			accesoThis();
		}
		else if (tokenActual.getTipoToken().equals("idMetVar")) {
			accesoUnidad();
		}
		else if (tokenActual.getLexema().equals("new")) {
			accesoConstructor();
		}
		else if (tokenActual.getTipoToken().equals("idClase")) {
			accesoMetodoEstatico();
		}
		else if (tokenActual.getLexema().equals("(")) {
			expresionParentizada();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "this, idMetVar, new, idClase o (");
		}
	}
	
	private void accesoThis() throws ExcepcionLexica, ExcepcionSintactica {
		match("this");
	}
	
	private void accesoUnidad() throws ExcepcionLexica, ExcepcionSintactica {
		match("idMetVar");
		argsOpcionales();
	}
	
	private void accesoConstructor() throws ExcepcionLexica, ExcepcionSintactica {
		match("new");
		match("idClase");
		argsActuales();
	}
	
	private void expresionParentizada() throws ExcepcionLexica, ExcepcionSintactica {
		match("(");
		expresion();
		match(")");
	}
	
	private void accesoMetodoEstatico() throws ExcepcionLexica, ExcepcionSintactica {
		match("idClase");
		match(".");
		match("idMetVar");
		argsActuales();
	}
	
	
	private void argsActuales() throws ExcepcionLexica, ExcepcionSintactica {
		match("(");
		listaExpsOpcional();
		match(")");
	}
	
	private void listaExpsOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		if (Arrays.asList("+", "-", "!", "true", "false", "null", "this", "new", "(").contains(tokenActual.getLexema())
				|| Arrays.asList("intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase").contains(tokenActual.getTipoToken())) {
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
		if (tokenActual.getLexema().equals(",")) {
			match(",");
			listaExps();
		}
		else {
			
		}
	}
	
	private void encadenadoOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals(".")) {
			match(".");
			match("idMetVar");
			argsOpcionales();
			encadenadoOpcional();
		}
		else {
			
		}		
	}
	
	private void argsOpcionales() throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getLexema().equals("(")) {
			argsActuales();
		}
		else {
			
		}
	}
	
	
}
