package main;

public class Token {
	
	private String token;
	private String lexema;
	private int linea; 
	
	public Token(String t, String l, int lin) {
		token = t;
		lexema = l;
		linea = lin;
	}

	public String getToken() {
		return token;
	}

	public String getLexema() {
		return lexema;
	}

	public int getLinea() {
		return linea;
	}
	
	public String toString() {
		return "(" + token + "," + lexema + "," + linea + ")";
	}

}
