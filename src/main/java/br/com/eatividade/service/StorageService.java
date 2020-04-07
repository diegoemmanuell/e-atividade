package br.com.eatividade.service;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.eatividade.model.Atividade;

@Service
public class StorageService {

	@Autowired private ServletContext context;
	
	private Path pathTemporarioPdf = null;
	
	public boolean salvarPdf(Atividade atividade, MultipartFile pdf){
		File arquivo = null;
		
		try{
			pathTemporarioPdf = Paths.get(context.getRealPath("/files/temp/pdf/" + atividade.getId()));
			Files.createDirectories(pathTemporarioPdf);
			arquivo = new File(pathTemporarioPdf.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + pdf.getOriginalFilename());
			pdf.transferTo(arquivo);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

//	public void deletaArquivo(MultipartFile pdf) {
//		try{
//			pathTemporarioPdf = Paths.get(context.getRealPath("/files/temp/pdf/" + pdf.getOriginalFilename()));
//			FileUtils.cleanDirectory(pathTemporarioPdf.getParent().toFile());
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	
}
