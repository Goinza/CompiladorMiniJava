package main;

import java.io.FileNotFoundException;

import lexico.AnalizadorLexico;
import lexico.ExcepcionLexica;
import semantico_ts.ExcepcionSemantica;
import semantico_ts.TablaSimbolos;
import sintactico.AnalizadorSintactico;
import sintactico.ExcepcionSintactica;
import sourcemanager.SourceManager;
import sourcemanager.SourceManagerImpl;
import traduccion.GeneradorCodigo;

public class ModuloPrincipal {

	public static void main(String[] args) {
		String pathFile = args[0];
		SourceManager sm = new SourceManagerImpl();
		boolean testFlag = false;
		try {
			TablaSimbolos.inicializarTabla();
			sm.open(pathFile);
			GeneradorCodigo.openFile(pathFile);
			AnalizadorLexico lexico = new AnalizadorLexico(sm);
			new AnalizadorSintactico(lexico);	
			TablaSimbolos.getTabla().verificarDeclaracion();
			TablaSimbolos.getTabla().consolidar();
			TablaSimbolos.getTabla().chequearAST();
			TablaSimbolos.getTabla().generarCodigo();
			GeneradorCodigo.closeFile();
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
		catch (ExcepcionSemantica e3) {
			System.out.println(e3.getMessage());
			if (testFlag) {
				e3.printStackTrace();
				System.err.println(e3.getTokenActual().getTipoToken());				
			}
			System.out.println(e3.getErrorCode());
		}
		
	}

}
