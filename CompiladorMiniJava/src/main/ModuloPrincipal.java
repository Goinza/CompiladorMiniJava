package main;

import sourcemanager.SourceManagerImpl;

public class ModuloPrincipal {

	public static void main(String[] args) {
		AnalizadorLexico lexico = new AnalizadorLexico(new SourceManagerImpl());
		Token token;
		try {		
			do {				
				token = lexico.getNextToken();
				System.out.println(token.getToken());
			} while (token.getToken() != "EOF"); 
		}
		catch (ExcepcionLexica e) {
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
		}
	}

}
