package webScraping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import net.lingala.zip4j.exception.ZipException;

public class Main {
	public static void main(String[] args) throws IOException ,MalformedURLException, ZipException {
		// Instancia da classe
		ZipAnexos z = new ZipAnexos();
		// Fazendo os downloads necessários
		z.downloadAnexos();
		// Zipando os arquivos
		ZipAnexos.ZipArquivos(Paths.get("src/main/resources/anexos/").toFile());
		
		z.readPDFtoCSV();
	}
}
