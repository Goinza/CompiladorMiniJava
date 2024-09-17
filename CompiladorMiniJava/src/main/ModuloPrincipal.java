package main;

import java.io.FileNotFoundException;

import lexico.AnalizadorLexico;
import lexico.ExcepcionLexica;
import sintactico.AnalizadorSintactico;
import sintactico.ExcepcionSintactica;
import sourcemanager.SourceManager;
import sourcemanager.SourceManagerImpl;

public class ModuloPrincipal {

	public static void main(String[] args) {
		String pathFile = args[0];
		SourceManager sm = new SourceManagerImpl();
		boolean testFlag = false;
		try {
			sm.open(pathFile);
			AnalizadorLexico lexico = new AnalizadorLexico(sm);
			new AnalizadorSintactico(lexico);			
			System.out.println("[SinErrores]");
		} catch (FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		}		
		catch (ExcepcionLexica e) {
			System.out.println(e.getMessage());
			if (testFlag) {
				e.printStackTrace();				
			}
			System.out.println(e.getErrorCode());
		}
		catch (ExcepcionSintactica e2) {
			System.out.println(e2.getMessage());
			if (testFlag) {
				e2.printStackTrace();
				System.err.println(e2.getTokenActual().getTipoToken());				
			}
			System.out.println(e2.getErrorCode());
		}
		
	}

}
