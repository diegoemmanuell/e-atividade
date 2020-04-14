package br.com.eatividade.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.eatividade.model.Atividade;
import br.com.eatividade.model.Turma;
import br.com.eatividade.service.AtividadeService;
import br.com.eatividade.service.TurmaService;

@Controller
@RequestMapping("/private/atividades")
public class AtividadeController {

	@Autowired private AtividadeService atividadeService;
	@Autowired private TurmaService turmaService;
	
	@GetMapping
	public String getAtividades(Model model, @RequestParam(name = "idTurma", required = false) Long idTurma){
		
		Turma turma = idTurma == null ? null : turmaService.findById(idTurma);
		
		List<Atividade> atividades = atividadeService.findAll(turma);
		
		model.addAttribute("turmas", turmaService.findAllOrderById());
		model.addAttribute("atividades", atividades);
		model.addAttribute("atividade", new Atividade());
		model.addAttribute("labelAtividades", idTurma == null ? 
				"Atividades de todas as turmas" : 
					"Atividades da turma '" + turma.getNome() + "'");
		return "private/Atividade/Atividades";
	}
	
	@PostMapping
	public String novaAtividade(MultipartFile dadosPdf, Atividade atividade, RedirectAttributes redirectAttributes) throws IOException{
		
		if(atividadeService.adicionaAtividade(atividade, dadosPdf)){
			redirectAttributes.addFlashAttribute("mensagem", "Atividade '".concat(atividade.getNome()).concat("' adicionada com sucesso."));
		}else{
			redirectAttributes.addFlashAttribute("errorMessage", "Não foi possível adicionar a atividade '".concat(atividade.getNome()).concat("'."));
		}
		
		return "redirect:/private/atividades";
		
	}
	
	@GetMapping("/valida-download-pdf/{id}")
	public Object validaAtividadeParaDownload(@PathVariable("id") Long idAtividade, RedirectAttributes redirectAttributes){
		
		Atividade atividade = atividadeService.findById(idAtividade);
		
		if(atividade != null && atividade.getId() != null){
			return "redirect:/private/atividades/download-pdf/".concat(idAtividade.toString());
		}
		redirectAttributes.addFlashAttribute("errorMessage", "Não foi possível encontrar a atividade para download.");
		return "redirect:/private/atividades";
	}
	
	@GetMapping("/download-pdf/{id}")
	public HttpEntity<byte[]> getPdf(@PathVariable("id") Long idAtividade){
		return atividadeService.getPdf(idAtividade);
	}
	
	@GetMapping("/deleta/{id}")
	public String deleta(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
		
		Atividade atividade = atividadeService.findById(id);
		if(atividade != null && atividade.getId() != null){
			if(atividadeService.deletaAtividade(atividade)){
				redirectAttributes.addFlashAttribute("mensagem", "Atividade '".concat(atividade.getNome()).concat("' deletada com sucesso"));
			}else{
				redirectAttributes.addFlashAttribute("errorMessage", "Não foi possível deletar a atividade '".concat(atividade.getNome()).concat("'."));
			}
		}else{
			redirectAttributes.addFlashAttribute("errorMessage", "Nenhuma atividade foi encontrada '".concat(atividade.getNome()).concat("'."));
		}
		
		return "redirect:/private/atividades";
		
	}
	
	
}
