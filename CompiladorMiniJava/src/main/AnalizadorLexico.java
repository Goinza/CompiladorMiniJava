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
		updateLastReadChar();
	}
	
	public Token getNextToken() {
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
	
	private Token e0() {
		return null;
	}
	
}
