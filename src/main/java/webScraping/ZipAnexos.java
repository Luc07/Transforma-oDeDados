package webScraping;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class ZipAnexos {
	
	
	private static ZipFile zipFile;
	
	private ArrayList<URL> urls = new ArrayList<URL>();
	
	// Metodo para fazer a zipagem dos arquivos
	public static void ZipArquivos(File f) throws IOException, ZipException {
		// Nomeando e setando o local onde irá ser salvo o zip
		zipFile = new ZipFile("src/main/resources/anexos.zip");
		// Adicionando a pasta no zip
		zipFile.addFolder(f);
	}
	
	// Metodo para baixar os arquivos, irá salvar na pasta src/main/resources/anexos 
	// caso não tenha vai criar
	public void downloadAnexos() throws MalformedURLException {
		// Varíaveis para controlar quantidade dos pdfs, xlsx nos links
		int pdf = 1;
		int xlsx = 1;
		
		// Adiciona ao array de URLs todas as urls dos arquivos
		urls.add(new URL("https://www.gov.br/ans/pt-br/arquivos/assuntos/consumidor/o-que-seu-plano-deve-cobrir/Anexo_I_Rol_2021RN_465.2021_RN473_RN478_RN480_RN513_RN536.pdf"));
		urls.add(new URL("https://www.gov.br/ans/pt-br/arquivos/assuntos/consumidor/o-que-seu-plano-deve-cobrir/Anexo_I_Rol_2021RN_465.2021_RN473_RN478_RN480_RN513_RN536.xlsx"));
		urls.add(new URL("https://www.gov.br/ans/pt-br/arquivos/assuntos/consumidor/o-que-seu-plano-deve-cobrir/Anexo_II_DUT_2021_RN_465.2021_tea.br_RN473_RN477_RN478_RN480_RN513_RN536.pdf"));	
		urls.add(new URL("https://www.gov.br/ans/pt-br/arquivos/assuntos/consumidor/o-que-seu-plano-deve-cobrir/Anexo_III_DC_2021_RN_465.2021.v2.pdf"));	
		urls.add(new URL("https://www.gov.br/ans/pt-br/arquivos/assuntos/consumidor/o-que-seu-plano-deve-cobrir/Anexo_IV_PROUT_2021_RN_465.2021.v2.pdf"));	
			
		// Para cada url dentro do array urls irá fazer o procedimento de salvar na pasta
		for (URL url : urls) {
			// Captura o que tem na url, download
			try (InputStream in = url.openStream()) {
				// Criando o diretorio
				Path p = Paths.get("src/main/resources/anexos");
				Files.createDirectories(p);
				
				// Aqui é mais para controle de saber se é pdf ou xlsx para poder nomear direito
				char ultimaLetra = url.getFile().charAt(url.getFile().length() - 1);
				// Caso for f na ultima letra é um pdf
				if(ultimaLetra == 'f') {
					// Copia as coisas que pegou na url, o arquivo, e faz uma copia dele para outro aquivo dentro do diretorio criado
					Files.copy(in, Paths.get("src/main/resources/anexos/Anexo_"+intParaRomano(pdf)+".pdf"), StandardCopyOption.REPLACE_EXISTING);
					pdf++;
			    // Caso for x é um xlsx
				}else if (ultimaLetra == 'x') {
					Files.copy(in, Paths.get("src/main/resources/anexos/Anexo_"+intParaRomano(xlsx)+".xlsx"), StandardCopyOption.REPLACE_EXISTING);
					xlsx++;
				}
				
			} catch (IOException e) {
				// handle exception
			}
		}
	}
	
	// Convertendo o numero para romano so pra torna mais bonito o nome do arquivo
	// (I,II,III,IV ...) em vez de (1,2,3,4 ... )
	public static String intParaRomano(int num) {
		int[] valores = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
		String[] letrasRomanas = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < valores.length; i++) {
			while (num >= valores[i]) {
				num = num - valores[i];
				r.append(letrasRomanas[i]);
			}
		}
		return r.toString();
	}
}
