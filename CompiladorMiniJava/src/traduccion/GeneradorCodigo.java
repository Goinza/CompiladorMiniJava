package traduccion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class GeneradorCodigo {
	
	private static File file;
	private static FileWriter fw;
	private static boolean consoleTesting = false;
	
	public static void openFile(String pathname) {
		String [] parts = pathname.splitWithDelimiters("/", -1);
		String filePathName = "[" + parts[parts.length-1] + "].out";
		file = new File(filePathName);
		try {
			Files.deleteIfExists(file.toPath());
			file.createNewFile();
			fw = new FileWriter(filePathName);
		} catch (IOException e) {
			System.err.println(filePathName);
			e.printStackTrace();
		}
	}
	
	public static void closeFile() {
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//El comentario puede ser nulo
	public static void generarInstruccion(String instruccion, String comentario) {
		String linea = instruccion;
		if (comentario != null) {
			linea += " ; " + comentario;
		}

		if (consoleTesting) {
			System.out.println(linea);
		}
		
		try {
			fw.write(linea + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void generarInstruccionEtiquetada(String etiqueta, String instruccion, String comentario) {
		String linea = etiqueta + ": " + instruccion;
		if (comentario != null) {
			linea += " ; " + comentario;
		}
		
		if (consoleTesting) {
			System.out.println(linea);
		}
		
		try {
			fw.write(linea + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
