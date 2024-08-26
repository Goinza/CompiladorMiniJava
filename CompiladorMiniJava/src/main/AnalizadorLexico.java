package main;

import java.io.IOException;

import sourcemanager.SourceManager;

public class AnalizadorLexico {

	private SourceManager io;
	private char lastReadChar;
	private String lexema;
	
	public AnalizadorLexico(SourceManager sm) {
		io = sm;
		updateLastReadChar();
	}
	
	public Token getNextToken() throws ExcepcionLexica {
		lexema = "";
		return e0();
	}
	
	private void updateLexema() {
		lexema += lastReadChar;
	}
	
	private void updateLastReadChar() {
		try {
			lastReadChar = io.getNextChar();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private String getIdType(String word) {
		String type = "";
		switch (word) {
			case "true":
			case "false":
				type = "boolLiteral";
				break;
			case "null":
				type = "nullLiteral";
				break;
			case "class":
			case "extends":
			case "public":
			case "static":
			case "void":
			case "boolean":
			case "char":
			case "int":
			case "if":
			case "else":
			case "while":
			case "return":
			case "var":
			case "switch":
			case "case":
			case "break":
			case "this":
			case "new":
				type = "word" + word;
				break;
			default:
				type = "idMetVar";
				break;
		}
				
		return type;
	}
	
	private Token e0() throws ExcepcionLexica {
		if (Character.isWhitespace(lastReadChar)) {
			updateLastReadChar();
			return e0();
		}
		if (Character.isUpperCase(lastReadChar)) {
			updateLexema();
			updateLastReadChar();
			return e1();
		}
		if (Character.isLowerCase(lastReadChar)) {
			updateLexema();
			updateLastReadChar();
			return e2();
		}
		if (Character.isDigit(lastReadChar)) {
			updateLexema();
			updateLastReadChar();
			return e3();
		}
		if (lastReadChar == '\'') {
			updateLexema();
			updateLastReadChar();
			return e4();
		}
		if (lastReadChar == '"') {
			updateLexema();
			updateLastReadChar();
			return e8();
		}
		if (lastReadChar == '(') {
			updateLexema();
			updateLastReadChar();
			return e12();
		}
		if (lastReadChar == ')') {
			updateLexema();
			updateLastReadChar();
			return e13();
		}
		if (lastReadChar == '{') {
			updateLexema();
			updateLastReadChar();
			return e14();
		}
		if (lastReadChar == '}') {
			updateLexema();
			updateLastReadChar();
			return e15();
		}
		if (lastReadChar == ';') {
			updateLexema();
			updateLastReadChar();
			return e16();
		}
		if (lastReadChar == ',') {
			updateLexema();
			updateLastReadChar();
			return e17();
		}
		if (lastReadChar == '.') {
			updateLexema();
			updateLastReadChar();
			return e18();
		}
		if (lastReadChar == ':') {
			updateLexema();
			updateLastReadChar();
			return e19();
		}
		if (lastReadChar == '>') {
			updateLexema();
			updateLastReadChar();
			return e20();
		}
		if (lastReadChar == '<') {
			updateLexema();
			updateLastReadChar();
			return e22();	
		}
		if (lastReadChar == '!') {
			updateLexema();
			updateLastReadChar();
			return e24();
		}
		if (lastReadChar == '=') {
			updateLexema();
			updateLastReadChar();
			return e26();
		}
		if (lastReadChar == '+') {
			updateLexema();
			updateLastReadChar();
			return e28();
		}
		if (lastReadChar == '-') {
			updateLexema();
			updateLastReadChar();
			return e30();
		}
		if (lastReadChar == '*') {
			updateLexema();
			updateLastReadChar();
			return e32();
		}
		if (lastReadChar == '/') {
			updateLexema();
			updateLastReadChar();
			return e33();
		}
		if (lastReadChar == '%') {
			updateLexema();
			updateLastReadChar();
			return e34();
		}
		if (lastReadChar == '&') {
			updateLexema();
			updateLastReadChar();
			return e35();
		}
		if (lastReadChar == '|') {
			updateLexema();
			updateLastReadChar();
			return e37();
		}
		if (lastReadChar == SourceManager.END_OF_FILE) {
			updateLexema();
			try {
				io.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			return e39();
		}
		
		updateLexema();
		throw new ExcepcionLexica(lexema, io.getLineNumber());		
	}
	
	private Token e1() {
		if (Character.isUpperCase(lastReadChar)) {
			updateLexema();
			updateLastReadChar();
			return e1();
		}
		if (Character.isLowerCase(lastReadChar)) {
			updateLexema();
			updateLastReadChar();
			return e1();
		}
		if (Character.isDigit(lastReadChar)) {
			updateLexema();
			updateLastReadChar();
			return e1();
		}
		
		return new Token("idClase", lexema, io.getLineNumber());
	}
	
	private Token e2() {
		if (Character.isUpperCase(lastReadChar)) {
			updateLexema();
			updateLastReadChar();
			return e2();
		}
		if (Character.isLowerCase(lastReadChar)) {
			updateLexema();
			updateLastReadChar();
			return e2();
		}
		if (Character.isDigit(lastReadChar)) {
			updateLexema();
			updateLastReadChar();
			return e2();
		}
		
		return new Token(getIdType(lexema), lexema, io.getLineNumber());
	}	

	private Token e3() throws ExcepcionLexica {
		if (Character.isDigit(lastReadChar)) {
			updateLexema();
			updateLastReadChar();
			return e3();
		}
		
		if (lexema.length() > 9) {
			throw new ExcepcionLexica(lexema, io.getLineNumber());
		}
		
		return new Token("intLiteral", lexema, io.getLineNumber());
	}
	
	private Token e4() throws ExcepcionLexica {
		if (lastReadChar == '\\') {
			updateLexema();
			updateLastReadChar();
			return e5();
		}
		if (lastReadChar == '\'') {
			throw new ExcepcionLexica(lexema, io.getLineNumber());	
		}
		
		updateLexema();
		updateLastReadChar();
		return e6();
	}
	
	private Token e5() throws ExcepcionLexica {
		updateLexema();
		updateLastReadChar();
		return e6();
	}
	
	private Token e6() throws ExcepcionLexica {
		if (lastReadChar == '\'') {
			updateLexema();
			updateLastReadChar();
			return e7();
		}
		
		throw new ExcepcionLexica(lexema, io.getLineNumber());
	}
	
	private Token e7() {
		return new Token("charLiteral", lexema, io.getLineNumber());
	}
	
	private Token e8() throws ExcepcionLexica {
		if (Character.isAlphabetic(lastReadChar)) {
			updateLexema();
			updateLastReadChar();
			return e9();
		}
		
		throw new ExcepcionLexica(lexema, io.getLineNumber());
	}
	
	private Token e9() throws ExcepcionLexica {
		if (Character.isAlphabetic(lastReadChar)) {
			updateLexema();
			updateLastReadChar();
			return e9();
		}
		if (lastReadChar == '"') {
			updateLexema();
			updateLastReadChar();
			return e10();
		}
		if (lastReadChar == '\\') {
			updateLexema();
			updateLastReadChar();
			return e11();
		}
		
		throw new ExcepcionLexica(lexema, io.getLineNumber());
	}
	
	private Token e10() {
		return new Token("stringLiteral", lexema, io.getLineNumber());
	}
	
	private Token e11() throws ExcepcionLexica {
		if (lastReadChar == '"') {
			updateLexema();
			updateLastReadChar();
			return e9();
		}
		
		throw new ExcepcionLexica(lexema, io.getLineNumber());
	}
	
	private Token e12() {
		return new Token("parentesisInicio", lexema, io.getLineNumber());
	}
	
	private Token e13() {
		return new Token("parentesisFin", lexema, io.getLineNumber());
	}
	
	private Token e14() {
		return new Token("llaveInicio", lexema, io.getLineNumber());
	}
	
	private Token e15() {
		return new Token("llaveFin", lexema, io.getLineNumber());
	}
	
	private Token e16() {
		return new Token("puntoComa", lexema, io.getLineNumber());
	}
	
	private Token e17() {
		return new Token("coma", lexema, io.getLineNumber());
	}
	
	private Token e18() {
		return new Token("punto", lexema, io.getLineNumber());
	}
	
	private Token e19() {
		return new Token("dosPuntos", lexema, io.getLineNumber());
	}
	
	private Token e20() {
		if (lastReadChar == '=') {
			updateLexema();
			updateLastReadChar();
			return e21();
		}
		return new Token("opMayor", lexema, io.getLineNumber());
	}
	
	private Token e21() {
		return new Token("opMayorIgual", lexema, io.getLineNumber());
	}
	
	private Token e22() {
		if (lastReadChar == '=') {
			updateLexema();
			updateLastReadChar();
			return e23();
		}
		return new Token("opMenor", lexema, io.getLineNumber());
	}
	
	private Token e23() {
		return new Token("opMenorIgual", lexema, io.getLineNumber());
	}
	
	private Token e24() {
		if (lastReadChar == '=') {
			updateLexema();
			updateLastReadChar();
			return e25();
		}
		return new Token("opNegacion", lexema, io.getLineNumber());
	}
	
	private Token e25() {
		return new Token("opDistinto", lexema, io.getLineNumber());
	}
	
	private Token e26() {
		if (lastReadChar == '=') {
			updateLexema();
			updateLastReadChar();
			return e27();
		}
		return new Token("asignacion", lexema, io.getLineNumber());
	}
	
	private Token e27() {
		return new Token("opIgual", lexema, io.getLineNumber());
	}
	
	private Token e28() {
		if (lastReadChar == '=') {
			updateLexema();
			updateLastReadChar();
			return e29();
		}
		return new Token("opSuma", lexema, io.getLineNumber());
	}
	
	private Token e29() {
		return new Token("asignacionSuma", lexema, io.getLineNumber());
	}
	
	private Token e30() {
		if (lastReadChar == '=') {
			updateLexema();
			updateLastReadChar();
			return e31();
		}
		return new Token("opResta", lexema, io.getLineNumber());
	}
	
	private Token e31() {
		return new Token("asignacionResta", lexema, io.getLineNumber());
	}
	
	private Token e32() {
		return new Token("opMultiplicacion", lexema, io.getLineNumber());
	}
	
	private Token e33() throws ExcepcionLexica {
		if (lastReadChar == '*') {
			lexema = "";
			updateLastReadChar();
			return e40();
		}
		if (lastReadChar == '/') {
			lexema = "";
			updateLastReadChar();
			return e42();
		}
		
		return new Token("opDivision", lexema, io.getLineNumber());
	}
	
	private Token e34() {
		return new Token("opModulo", lexema, io.getLineNumber());
	}
	
	private Token e35() throws ExcepcionLexica {
		if (lastReadChar == '&') {
			updateLexema();
			updateLastReadChar();
			return e36();
		}
		throw new ExcepcionLexica(lexema, io.getLineNumber());
	}
	
	private Token e36() {
		return new Token("opAnd", lexema, io.getLineNumber());
	}
	
	private Token e37() throws ExcepcionLexica {
		if (lastReadChar == '|') {
			updateLexema();
			updateLastReadChar();
			return e38();
		}
		throw new ExcepcionLexica(lexema, io.getLineNumber());
	}
	
	private Token e38() {
		return new Token("opOr", lexema, io.getLineNumber());
	}
	
	private Token e39() {
		return new Token("EOF", lexema, io.getLineNumber());
	}
	
	private Token e40() throws ExcepcionLexica {
		if (lastReadChar == '*') {
			updateLastReadChar();
			return e41();
		}
		
		updateLastReadChar();
		return e40();
	}
	
	private Token e41() throws ExcepcionLexica {
		if (lastReadChar == '/') {
			updateLastReadChar();
			return e0();
		}
		
		updateLastReadChar();
		return e40();
	}
	
	private Token e42() throws ExcepcionLexica {
		if (lastReadChar == '\n') {
			updateLastReadChar();
			return e0();
		}
		
		updateLastReadChar();
		return e42();
	}
	
}
