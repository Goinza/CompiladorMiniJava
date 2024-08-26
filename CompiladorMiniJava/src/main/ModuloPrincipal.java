package main;

import java.io.FileNotFoundException;

import sourcemanager.SourceManager;
import sourcemanager.SourceManagerImpl;

public class ModuloPrincipal {

	public static void main(String[] args) {
		String pathFile = args[0];
		SourceManager sm = new SourceManagerImpl();
		try {
			sm.open(pathFile);
			AnalizadorLexico lexico = new AnalizadorLexico(sm);
			Token token;	
			
			do {				
				token = lexico.getNextToken();
				System.out.println(token.toString());
			} while (token.getToken() != "EOF");
			
			System.out.println("[SinErrores]");
		} catch (FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		}		
		catch (ExcepcionLexica e) {
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
		}
		
	}

}
