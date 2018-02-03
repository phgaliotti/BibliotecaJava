package br.biblioteca.livros.util;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaver {

	public String write(String baseFolder, MultipartFile file) {
		String realPath = "C:/Desenv/Repositorios/BibliotecaJava/src/main/resources/static/" + baseFolder;
		
		//String realPath = "/Volumes/Arquivos/FIB/POS/workspace/livros/" + baseFolder;

		// se for windows, verificar corretamenteo path da pasta, por exemplo
		//  String realPath = "D:\\Arquivos\\" + baseFolder;
		
		File folder = new File(realPath);
		if(!folder.exists()){
			folder.mkdirs();
		}
		try {
			String path = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(path));
			return baseFolder + "/" + file.getOriginalFilename();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
