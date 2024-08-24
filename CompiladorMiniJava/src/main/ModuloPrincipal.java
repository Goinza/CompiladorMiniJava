package main;

import sourcemanager.SourceManagerImpl;

public class ModuloPrincipal {

	public static void main(String[] args) {
		AnalizadorLexico lexico = new AnalizadorLexico(new SourceManagerImpl());
		Token token;
				
		do {
			token = lexico.getNextToken();
		} while (token.getToken() != "EOF"); 

	}

}
