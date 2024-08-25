package main;

import java.io.FileNotFoundException;
import java.io.IOException;

import sourcemanager.SourceManager;

public class AnalizadorLexico {

	private SourceManager io;
	private char lastReadChar;
	private String lexema;
	
	public AnalizadorLexico(SourceManager sm) {
		io = sm;
		try {
			io.open(""); //Add filepath
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
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
		updateLastReadChar();
		if (Character.isWhitespace(lastReadChar)) {
			return e0();
		}
		if (Character.isUpperCase(lastReadChar)) {
			updateLexema();
			return e1();
		}
		if (Character.isLowerCase(lastReadChar)) {
			updateLexema();
			return e2();
		}
		if (Character.isDigit(lastReadChar)) {
			updateLexema();
			return e3();
		}
		if (lastReadChar == '\'') {
			updateLexema();
			return e4();
		}
		if (lastReadChar == '"') {
			updateLexema();
			return e8();
		}
		
		//Faltan puntuacion, operadores, asignacion
		//Cada signo de puntuacion es un tipo de token distinto?
		
		throw new ExcepcionLexica(lexema, io.getLineNumber());		
	}
	
	private Token e1() {
		updateLastReadChar();
		if (Character.isUpperCase(lastReadChar)) {
			updateLexema();
			return e1();
		}
		if (Character.isLowerCase(lastReadChar)) {
			updateLexema();
			return e1();
		}
		if (Character.isDigit(lastReadChar)) {
			updateLexema();
			return e1();
		}
		
		return new Token("idClase", lexema, io.getLineNumber());
	}
	
	private Token e2() {
		updateLastReadChar();
		if (Character.isUpperCase(lastReadChar)) {
			updateLexema();
			return e2();
		}
		if (Character.isLowerCase(lastReadChar)) {
			updateLexema();
			return e2();
		}
		if (Character.isDigit(lastReadChar)) {
			updateLexema();
			return e2();
		}
		
		return new Token(getIdType(lexema), lexema, io.getLineNumber());
	}	

	private Token e3() throws ExcepcionLexica {
		updateLastReadChar();
		if (Character.isDigit(lastReadChar)) {
			updateLexema();
			return e3();
		}
		
		if (lexema.length() > 9) {
			throw new ExcepcionLexica(lexema, io.getLineNumber());
		}
		
		return new Token("intLiteral", lexema, io.getLineNumber());
	}
	
	private Token e4() throws ExcepcionLexica {
		updateLastReadChar();
		if (lastReadChar == '\\') {
			updateLexema();
			return e5();
		}
		
		throw new ExcepcionLexica(lexema, io.getLineNumber());		
	}
	
	private Token e5() throws ExcepcionLexica {
		updateLastReadChar();
		if (Character.isAlphabetic(lastReadChar)) {
			updateLexema();
			return e6();
		}
		
		throw new ExcepcionLexica(lexema, io.getLineNumber());
	}
	
	private Token e6() throws ExcepcionLexica {
		updateLastReadChar();
		if (lastReadChar == '\'') {
			updateLexema();
			return e7();
		}
		
		throw new ExcepcionLexica(lexema, io.getLineNumber());
	}
	
	private Token e7() {
		return new Token("charLiteral", lexema, io.getLineNumber());
	}
	
	private Token e8() throws ExcepcionLexica {
		updateLastReadChar();
		if (Character.isAlphabetic(lastReadChar)) {
			updateLexema();
			return e9();
		}
		
		throw new ExcepcionLexica(lexema, io.getLineNumber());
	}
	
	private Token e9() throws ExcepcionLexica {
		updateLastReadChar();
		if (Character.isAlphabetic(lastReadChar)) {
			updateLexema();
			return e9();
		}
		if (lastReadChar == '"') {
			updateLexema();
			return e10();
		}
		if (lastReadChar == '\\') {
			updateLexema();
			return e11();
		}
		
		throw new ExcepcionLexica(lexema, io.getLineNumber());
	}
	
	private Token e10() {
		return new Token("stringLiteral", lexema, io.getLineNumber());
	}
	
	private Token e11() throws ExcepcionLexica {
		updateLastReadChar();
		if (lastReadChar == '"') {
			updateLexema();
			return e9();
		}
		
		throw new ExcepcionLexica(lexema, io.getLineNumber());
	}
	
}
