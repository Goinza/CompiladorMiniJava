package sintactico;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lexico.AnalizadorLexico;
import lexico.ExcepcionLexica;
import main.Token;
import semantico_ast.*;
import semantico_ts.*;

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
		ts.setEOFToken(tokenActual);
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
		Token padre = herenciaOpcional();
		c.setPadre(padre);
		match("llaveInicio");
		listaMiembros();
		match("llaveFin");
	}
	
	private Token herenciaOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		Token clase;
		if (tokenActual.getTipoToken().equals("wordextends")) {
			match("wordextends");
			Token token = tokenActual;
			match("idClase");			
			clase = token;
		}
		else {
			clase = null;
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
		SimpleEntry<NodoBloque, List<Parametro>> pair = contUnidad();
		NodoBloque bloque = pair == null ? null : pair.getKey();
		List<Parametro> parametros = pair == null ? null :pair.getValue();
		
		if (parametros != null) {
			Metodo m = new Metodo(token, tipo, esEstatico);
			ts.getClaseActual().agregarMetodo(m);
			ts.setMetodoActual(m);
			bloque.setMetodo(m);
			for (Parametro p : parametros) {
				m.agregarParametro(p);
			}
		}
		else {
			Atributo a = new Atributo(token, tipo, esEstatico);
			ts.getClaseActual().agregarAtributo(a);
		}
	}
	
	private SimpleEntry<NodoBloque, List<Parametro>> contUnidad() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		SimpleEntry<NodoBloque, List<Parametro>> pair = null;
		if (tokenActual.getTipoToken().equals("puntoComa")) {
			match("puntoComa");
		}
		else if (tokenActual.getTipoToken().equals("parentesisInicio")) {
			List<Parametro> parametros = argsFormales();
			NodoBloque bloque = bloque();
			pair = new SimpleEntry<NodoBloque, List<Parametro>>(bloque, parametros);
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "; o (");
		}
		
		return pair;
	}
	
	private void constructor() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		match("wordpublic");
		Token token = tokenActual;
		match("idClase");
		Constructor c = new Constructor(token);
		ts.getClaseActual().setConstructor(c);
		ts.setMetodoActual(c);
		List<Parametro> parametros = argsFormales();
		for (Parametro p : parametros) {
			c.agregarParametro(p);
		}
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
	
	private List<Parametro> argsFormales() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		match("parentesisInicio");
		List<Parametro> parametros = listaArgsFormalesOpcional();
		match("parentesisFin");
		
		return parametros;
	}
	
	private List<Parametro> listaArgsFormalesOpcional() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		List<String> primerosListaArgsFormales = Arrays.asList("wordboolean", "wordchar", "wordint", "idClase");
		List<Parametro> parametros;
		if (primerosListaArgsFormales.contains(tokenActual.getTipoToken())) {
			parametros = listaArgsFormales();
		}
		else {
			parametros = new ArrayList<Parametro>();
		}
		
		return parametros;
	}
	
	private List<Parametro> listaArgsFormales() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		Parametro p = argFormal();
		List<Parametro> parametros = contListaArgsFormales();
		parametros.addFirst(p);
		
		return parametros;
	}
	
	private List<Parametro> contListaArgsFormales() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		List<Parametro> parametros;
		if (tokenActual.getTipoToken().equals("coma")) {
			match("coma");
			parametros = listaArgsFormales();
		}
		else {
			parametros = new ArrayList<Parametro>();
		}
		
		return parametros;
	}
	
	private Parametro argFormal() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		Tipo tipo = tipo();
		Token token = tokenActual;
		match("idMetVar");
		Parametro p = new Parametro(token, tipo);
		return p;
	}
	
	private NodoBloque bloque() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		NodoBloque bloque = new NodoBloque(ts.getBloqueActual());
		ts.setBloqueActual(bloque);	
		ts.agregarAST(bloque);
		match("llaveInicio");
		listaSentencias();
		match("llaveFin");
		ts.setBloqueActual(bloque.getBloquePadre());
		
		return bloque;
	}
	
	private void listaSentencias() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		List<String> primerosSentencia = Arrays.asList("puntoComa", "opSuma", "opResta", "opNegacion", "wordtrue", "wordfalse", "wordnull", "wordthis", "wordnew", "parentesisInicio", "wordvar", "wordreturn", "wordbreak", "wordif", "wordwhile", "wordswitch", "llaveInicio", "intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase");
		if (primerosSentencia.contains(tokenActual.getTipoToken())) {
			NodoSentencia sentencia = sentencia();
			ts.getBloqueActual().agregarSentencia(sentencia);
			listaSentencias();
		}
		else {
			
		}
	}
	
	private NodoSentencia sentencia() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		NodoSentencia sentencia;
		List<String> primerosExpresion = Arrays.asList("opSuma", "opResta", "opNegacion", "wordtrue", "wordfalse", "wordnull", "wordthis", "wordnew", "parentesisInicio", "intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase");
		if (tokenActual.getTipoToken().equals("puntoComa")) {
			match("puntoComa");
			sentencia = null;
		}
		else if (primerosExpresion.contains(tokenActual.getTipoToken())) {
			Token token = tokenActual;
			NodoExpresion exp = expresion();
			match("puntoComa");
			sentencia = new NodoSentExpresion(token, exp);
		}
		else if (tokenActual.getTipoToken().equals("wordvar")) {
			sentencia = varLocal();
			match("puntoComa");
		}
		else if (tokenActual.getTipoToken().equals("wordreturn")) {
			sentencia = returnNT();
			match("puntoComa");
		}
		else if (tokenActual.getTipoToken().equals("wordbreak")) {
			breakNT();
			match("puntoComa");
			sentencia = null;
		}
		else if (tokenActual.getTipoToken().equals("wordif")) {
			sentencia = ifNT();
		}
		else if (tokenActual.getTipoToken().equals("wordwhile")) {
			sentencia = whileNT();
		}
		else if (tokenActual.getTipoToken().equals("wordswitch")) {
			sentencia = switchNT();
		}
		else if (tokenActual.getTipoToken().equals("llaveInicio")) {
			sentencia = bloque();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "+, -, !, true, false, null, this, new, (, literal de entero, literal de caracter, literal de string, identificador de metodo/variable, identificador de clase, var, return, break, if, while, switch o {");
		}
		
		return sentencia;
	}
	
	private NodoVarLocal varLocal() throws ExcepcionLexica, ExcepcionSintactica {
		Token token;
		match("wordvar");
		token = tokenActual;
		match("idMetVar");
		NodoVarLocal local = new NodoVarLocal(token);
		match("asignacion");		
		NodoExpCompuesta exp = expresionCompuesta();
		local.setAsignacion(exp);
		
		return local;
	}
	
	private NodoReturn returnNT() throws ExcepcionLexica, ExcepcionSintactica {
		Token token = tokenActual;
		match("wordreturn");
		NodoReturn nodo = new NodoReturn(token);
		NodoExpresion exp = expresionOpcional();
		nodo.setRetorno(exp);
		
		return nodo;
	}
	
	private void breakNT() throws ExcepcionLexica, ExcepcionSintactica {
		match("wordbreak");
	}
	
	private NodoExpresion expresionOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		NodoExpresion nodo;
		List<String> primerosExpresion = Arrays.asList("opSuma", "opResta", "opNegacion", "wordtrue", "wordfalse", "wordnull", "wordthis", "wordnew", "parentesisInicio", "intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase");
		if (primerosExpresion.contains(tokenActual.getTipoToken())) {
			nodo = expresion();
		}
		else {
			nodo = null;
		}
		
		return nodo;
	}
	
	private NodoSentencia ifNT() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		NodoSentencia nodo;
		Token token = tokenActual;
		match("wordif");
		match("parentesisInicio");
		NodoExpresion exp = expresion();
		match("parentesisFin");
		NodoSentencia sentenciaThen = sentencia();
		NodoSentencia sentenciaElse = elseOpcional();
		if (sentenciaElse == null) {
			NodoIf nodoIf = new NodoIf(token);
			nodoIf.setCondicion(exp);
			nodoIf.setBloqueThen(sentenciaThen);
			nodo = nodoIf;
		}
		else {
			NodoIfElse nodoElse = new NodoIfElse(token);
			nodoElse.setCondicion(exp);
			nodoElse.setBloqueThen(sentenciaThen);
			nodoElse.setBloqueElse(sentenciaElse);
			nodo = nodoElse;
		}
		
		return nodo;
	}
	
	private NodoSentencia elseOpcional() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		NodoSentencia sentenciaElse;
		if (tokenActual.getTipoToken().equals("wordelse")) {
			match("wordelse");
			sentenciaElse = sentencia();	
		}
		else {
			sentenciaElse = null;
		}
		
		return sentenciaElse;
	}
	
	private NodoWhile whileNT() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		Token token = tokenActual;
		match("wordwhile");
		NodoWhile nodo = new NodoWhile(token);
		match("parentesisInicio");
		NodoExpresion condicion = expresion();
		nodo.setCondicion(condicion);
		match("parentesisFin");
		NodoSentencia sentencia = sentencia();
		nodo.setSentencia(sentencia);
		
		return nodo;
	}
	
	private NodoSwitch switchNT() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		Token token = tokenActual;
		match("wordswitch");
		NodoSwitch nodo = new NodoSwitch(token);
		match("parentesisInicio");
		NodoExpresion exp = expresion();
		nodo.setCondicion(exp);
		match("parentesisFin");
		match("llaveInicio");
		listaSentenciasSwitch(nodo);
		match("llaveFin");
		
		return nodo;
	}
	
	private void listaSentenciasSwitch(NodoSwitch nodo) throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		List<String> primerosSwitch = Arrays.asList("wordcase", "worddefault");
		if (primerosSwitch.contains(tokenActual.getTipoToken())) {
			sentenciaSwitch(nodo);
			listaSentenciasSwitch(nodo);
		}
		else {
			
		}
	}
	
	private void sentenciaSwitch(NodoSwitch nodo) throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		Token token;
		NodoSentencia sentencia;
		if (tokenActual.getTipoToken().equals("wordcase")) {
			token = tokenActual;
			match("wordcase");
			NodoLiteral literal = literalPrimitivo();
			match("dosPuntos");
			sentencia = sentenciaOpcional();
			NodoCaseSwitch nodoCase = new NodoCaseSwitch(token, literal, sentencia);
			nodo.agregarBloqueCase(nodoCase);
		}
		else if (tokenActual.getTipoToken().equals("worddefault")) {
			token = tokenActual;
			match("worddefault");
			match("dosPuntos");
			sentencia = sentencia();
			NodoDefaultSwitch nodoDefault = new NodoDefaultSwitch(token, sentencia);
			nodo.setBloqueDefault(nodoDefault);
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "case o default");
		}
	}
	
	private NodoSentencia sentenciaOpcional() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
		List<String> primerosSentencia = Arrays.asList("puntoComa", "opSuma", "opResta", "opNegacion", "wordtrue", "wordfalse", "wordnull", "wordthis", "wordnew", "parentesisInicio", "wordvar", "wordreturn", "wordbreak", "wordif", "wordwhile", "wordswitch", "llaveInicio", "intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase");
		NodoSentencia nodo;
		if (primerosSentencia.contains(tokenActual.getTipoToken())) {
			nodo = sentencia();
		}
		else {
			nodo = null;
		}
		
		return nodo;
	}
	
	private NodoExpresion expresion() throws ExcepcionLexica, ExcepcionSintactica {
		NodoExpCompuesta expComp = expresionCompuesta();
		NodoExpresion exp = extensionExpresion(expComp);
		
		return exp;
	}
	
	private NodoExpresion extensionExpresion(NodoExpCompuesta expComp) throws ExcepcionLexica, ExcepcionSintactica {
		List<String> primerosAsignacion = Arrays.asList("asignacion", "asignacionSuma", "asignacionResta");
		NodoExpresion exp;
		if (primerosAsignacion.contains(tokenActual.getTipoToken())) {
			NodoExpAsignacion asignacion = operadorAsignacion();
			asignacion.setLadoIzquierdo(expComp);
			NodoExpCompuesta expComp2 = expresionCompuesta();
			asignacion.setLadoDerecho(expComp2);
			exp = asignacion;
		}
		else {
			exp = null;
		}
		
		return exp;
	}
	
	private NodoExpAsignacion operadorAsignacion() throws ExcepcionLexica, ExcepcionSintactica {
		Token token = tokenActual;
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
	
		return new NodoExpAsignacion(token);
	}
	
	private NodoExpCompuesta expresionCompuesta() throws ExcepcionLexica, ExcepcionSintactica {
		NodoExpCompuesta exp = expresionBasica();
		NodoExpCompuesta entExp = extensionExpresionCompuesta(exp);
		
		return entExp;
	}
	
	private NodoExpCompuesta extensionExpresionCompuesta(NodoExpCompuesta exp) throws ExcepcionLexica, ExcepcionSintactica {
		NodoExpCompuesta entExp;
		List<String> primerosOperadorBinario = Arrays.asList("opOr", "opAnd", "opIgual", "opDistinto", "opMenor", "opMayor", "opMenorIgual", "opMayorIgual", "opSuma", "opResta", "opMultiplicacion", "opDivision", "opModulo");
		if (primerosOperadorBinario.contains(tokenActual.getTipoToken())) {
			NodoExpBinaria binaria = operadorBinario();
			binaria.setLadoIzquierdo(exp);
			NodoExpCompuesta basica = expresionBasica();
			binaria.setLadoDerecho(basica);
			entExp = extensionExpresionCompuesta(binaria);
		}
		else {
			entExp = exp;
		}
		
		return entExp;
	}
	
	private NodoExpBinaria operadorBinario() throws ExcepcionLexica, ExcepcionSintactica {
		Token token = tokenActual;
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
		
		return new NodoExpBinaria(token);
	}
	
	private NodoExpCompuesta expresionBasica() throws ExcepcionSintactica, ExcepcionLexica {
		List<String> primerosOperadorUnario = Arrays.asList("opSuma", "opResta", "opNegacion");
		List<String> primerosOperando = Arrays.asList("wordtrue", "wordfalse", "wordnull", "wordthis", "wordnew", "parentesisInicio", "intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase");
		NodoExpCompuesta exp;
		if (primerosOperadorUnario.contains(tokenActual.getTipoToken())) {
			NodoExpUnaria unario = operadorUnario();
			NodoOperando operando = operando();
			unario.setOperando(operando);
			exp = unario;
		}
		else if (primerosOperando.contains(tokenActual.getTipoToken())) {
			exp = operando();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "+, -, !, true, false, null, this, new, (, literal de entero, literal de caracter, literal de string, identificador de metodo/variable o identificador de clase");
		}
		
		return exp;
	}
	
	private NodoExpUnaria operadorUnario() throws ExcepcionLexica, ExcepcionSintactica {
		Token token = tokenActual;
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
		
		return new NodoExpUnaria(token);
	}
	
	private NodoOperando operando() throws ExcepcionSintactica, ExcepcionLexica {
		List<String> primerosLiteral = Arrays.asList("wordtrue", "wordfalse", "wordnull", "intLiteral", "charLiteral", "stringLiteral");
		List<String> primerosAcceso = Arrays.asList("wordthis", "wordnew", "parentesisInicio", "idMetVar", "idClase");
		NodoOperando nodo;
		if (primerosLiteral.contains(tokenActual.getTipoToken())) {
			nodo = literal();
		}
		else if (primerosAcceso.contains(tokenActual.getTipoToken())) {
			nodo = acceso();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "true, fale, null, literal de entero, literal de caracter, literal de string, this, new, (, identificador de metodo/variable, identificador de clase");
		}
		
		return nodo;
	}
	
	private NodoLiteral literal() throws ExcepcionSintactica, ExcepcionLexica {
		List<String> primerosLiteralPrimitivo = Arrays.asList("wordtrue", "wordfalse", "intLiteral", "charLiteral");
		List<String> primerosLiteralObjeto = Arrays.asList("wordnull", "stringLiteral");
		NodoLiteral nodo;
		if (primerosLiteralPrimitivo.contains(tokenActual.getTipoToken())) {
			nodo = literalPrimitivo();
		}
		else if (primerosLiteralObjeto.contains(tokenActual.getTipoToken())) {
			nodo = literalObjeto();
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "true, false, literal de entero, literal de caracter, null o literal de string");
		}
		
		return nodo;
	}
	
	private NodoLiteral literalPrimitivo() throws ExcepcionLexica, ExcepcionSintactica {
		Token token = tokenActual;
		NodoLiteral nodo;
		if (tokenActual.getTipoToken().equals("wordtrue")) {
			match("wordtrue");
			nodo = new NodoLiteralBooleano(token);
		}
		else if (tokenActual.getTipoToken().equals("wordfalse")) {
			match("wordfalse");
			nodo = new NodoLiteralBooleano(token);
		}
		else if (tokenActual.getTipoToken().equals("intLiteral")) {
			match("intLiteral");
			nodo = new NodoLiteralEntero(token);
		}
		else if (tokenActual.getTipoToken().equals("charLiteral")) {
			match("charLiteral");
			nodo = new NodoLiteralCaracter(token);
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "true, false, literal de entero o literal de caracter");
		}
		
		return nodo;
	}
	
	private NodoLiteral literalObjeto() throws ExcepcionLexica, ExcepcionSintactica {
		Token token = tokenActual;
		NodoLiteral nodo;
		if (tokenActual.getTipoToken().equals("wordnull")) {
			match("wordnull");
			nodo = new NodoLiteralNulo(token);
		}
		else if (tokenActual.getTipoToken().equals("stringLiteral")) {
			match("stringLiteral");
			nodo = new NodoLiteralString(token);
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "null o literal de string");
		}
		
		return nodo;
	}
	
	private NodoAcceso acceso() throws ExcepcionSintactica, ExcepcionLexica {
		NodoAcceso nodo = primario();
		NodoEncadenado enc = encadenadoOpcional();
		nodo.setEncadenado(enc);
		
		return nodo;
	}
	
	private NodoAcceso primario() throws ExcepcionSintactica, ExcepcionLexica {
		NodoAcceso nodo;
		if (tokenActual.getTipoToken().equals("wordthis")) {
			nodo = accesoThis();
		}
		else if (tokenActual.getTipoToken().equals("idMetVar")) {
			nodo = accesoUnidad();
		}
		else if (tokenActual.getTipoToken().equals("wordnew")) {
			nodo = accesoConstructor();
		}
		else if (tokenActual.getTipoToken().equals("idClase")) {
			nodo = accesoMetodoEstatico();
		}
		else if (tokenActual.getTipoToken().equals("parentesisInicio")) {
			Token token = tokenActual;
			NodoExpresion exp = expresionParentizada();
			nodo = new NodoAccesoExpresion(token, exp);
		}
		else {
			throw new ExcepcionSintactica(tokenActual, "this, identificador de metodo/variable, new, identificador de clase o (");
		}
		
		return nodo;
	}
	
	private NodoThis accesoThis() throws ExcepcionLexica, ExcepcionSintactica {
		Token token = tokenActual;
		match("wordthis");
		
		return new NodoThis(token);
	}
	
	private NodoAcceso accesoUnidad() throws ExcepcionLexica, ExcepcionSintactica {
		Token token = tokenActual;
		NodoAcceso nodo;
		match("idMetVar");
		List<NodoExpresion> lista = argsOpcionales();
		if (lista == null) {
			nodo = new NodoVariable(token);
		}
		else {
			NodoLlamada llamada = new NodoLlamada(token);
			llamada.setParametros(lista);
			nodo = llamada;
		}
		
		return nodo;
	}
	
	private NodoConstructor accesoConstructor() throws ExcepcionLexica, ExcepcionSintactica {
		match("wordnew");
		Token token = tokenActual;
		match("idClase");
		List<NodoExpresion> lista = argsActuales();
		NodoConstructor nodo = new NodoConstructor(token);
		if (lista != null) {
			nodo.setParametros(lista);
		}		
		
		return nodo;
	}
	
	private NodoExpresion expresionParentizada() throws ExcepcionLexica, ExcepcionSintactica {
		match("parentesisInicio");
		NodoExpresion exp = expresion();
		match("parentesisFin");
		
		return exp;
	}
	
	private NodoLlamadaEstatica accesoMetodoEstatico() throws ExcepcionLexica, ExcepcionSintactica {
		Token tokenClase = tokenActual;
		match("idClase");
		match("punto");
		Token token = tokenActual;
		match("idMetVar");
		List<NodoExpresion> lista = argsActuales();
		NodoLlamadaEstatica nodo = new NodoLlamadaEstatica(token, tokenClase);
		nodo.setParametros(lista);
		
		return nodo;
	}
	
	
	private List<NodoExpresion> argsActuales() throws ExcepcionLexica, ExcepcionSintactica {
		match("parentesisInicio");
		List<NodoExpresion> lista = listaExpsOpcional();
		match("parentesisFin");
		
		return lista;
	}
	
	private List<NodoExpresion> listaExpsOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		List<NodoExpresion> lista;
		List<String> primerosListaExps = Arrays.asList("opSuma", "opResta", "opNegacion", "wordtrue", "wordfalse", "wordnull", "wordthis", "wordnew", "parentesisInicio", "intLiteral", "charLiteral", "stringLiteral", "idMetVar", "idClase");
		if (primerosListaExps.contains(tokenActual.getTipoToken())) {
			lista = new LinkedList<NodoExpresion>();
			listaExps(lista);
		}
		else {
			lista = null;
		}
		
		return lista;
	}
	
	private void listaExps(List<NodoExpresion> lista) throws ExcepcionLexica, ExcepcionSintactica {
		NodoExpresion exp = expresion();
		lista.addLast(exp);
		contListaExps(lista);
	}
	
	private void contListaExps(List<NodoExpresion> lista) throws ExcepcionLexica, ExcepcionSintactica {
		if (tokenActual.getTipoToken().equals("coma")) {
			match("coma");
			listaExps(lista);
		}
		else {
			
		}
	}
	
	private NodoEncadenado encadenadoOpcional() throws ExcepcionLexica, ExcepcionSintactica {
		NodoEncadenado nodo;
		if (tokenActual.getTipoToken().equals("punto")) {
			match("punto");
			Token token = tokenActual;
			match("idMetVar");
			List<NodoExpresion> lista = argsOpcionales();
			if (lista == null) {
				nodo = new NodoVariableEncadenada(token);
			}
			else {
				NodoLlamadaEncadenada llamada = new NodoLlamadaEncadenada(token);
				llamada.setParametros(lista);
				nodo = llamada;
			}
			NodoEncadenado enc = encadenadoOpcional();
			nodo.setEncadenado(enc);
		}
		else {
			nodo = null;
		}		
		
		return nodo;
	}
	
	private List<NodoExpresion> argsOpcionales() throws ExcepcionLexica, ExcepcionSintactica {
		List<NodoExpresion> lista;
		if (tokenActual.getTipoToken().equals("parentesisInicio")) {
			lista = argsActuales();
		}
		else {
			lista = null;
		}
		
		return lista;
	}
	
	
}
