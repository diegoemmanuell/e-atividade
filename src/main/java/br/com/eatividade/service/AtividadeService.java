package br.com.eatividade.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.eatividade.model.Atividade;
import br.com.eatividade.model.Turma;
import br.com.eatividade.repository.AtividadeRepository;

@Service
public class AtividadeService {

	@Autowired private AtividadeRepository atividadeRepository;
	
	public List<Atividade> findAll(Turma turma) {
		return atividadeRepository.findAllOrderByDataDevolucaoAndId(turma);
	}

	public boolean adicionaAtividade(Atividade atividade, MultipartFile pdf) throws IOException {
		
		if(atividade.getId() == null){
			atividade.setDataCadastro(new Date());
			
			if(!pdf.getOriginalFilename().isEmpty()){
				atividade.setPdf(pdf.getBytes());
			}
		}else{
			Atividade atvOld = atividadeRepository.findById(atividade.getId()).get();
			
			atividade.setDataCadastro(atvOld.getDataCadastro());
			atividade.setPdf(atvOld.getPdf());
		}
		
		Atividade retorno = salva(atividade);
		
//		if(retorno != null){
//			Turma turma = turmaService.findById(retorno.getTurma());
//			retorno.setTurma(turma);
//			boolean pdfSalvo = storageService.salvarPdf(retorno, pdf);
//			if(!pdfSalvo){
//				atividadeRepository.delete(retorno);
//			}
////			storageService.deletaArquivo(pdf);
//			return pdfSalvo;
//		}
//		return false;
		
		return retorno != null;
		
	}


	private Atividade salva(Atividade atividade) {
		return atividadeRepository.save(atividade);
	}

	public HttpEntity<byte[]> getPdf(Long idAtividade) {
		
		Atividade atividade = findById(idAtividade);
		
		HttpEntity<byte[]> entity = null;
		
		String nomeArquivo = atividade.getNome()
				.replaceAll(" ", "_")
				.replaceAll(",", "_")
				.concat("_")
				.concat(atividade.getTurma().getNome().replaceAll(" ", "_"))
				.concat(".pdf");
		
		HttpHeaders  headers = new HttpHeaders();
		headers.add("Content-Type", "application/pdf");
		headers.add("Content-Disposition", "attachment; filename=\"" + nomeArquivo + "\"");
		entity = new HttpEntity<byte[]>(atividade.getPdf(), headers);
		return entity;
	}

	public Atividade findById(Long idAtividade) {
		
		Optional<Atividade> atividadeOpt = atividadeRepository.findById(idAtividade);
		
		return atividadeOpt.isPresent() ? atividadeOpt.get() : new Atividade();
	}

	public boolean deletaAtividade(Atividade atividade) {
		atividadeRepository.delete(atividade);
		
		return true;
	}
}
