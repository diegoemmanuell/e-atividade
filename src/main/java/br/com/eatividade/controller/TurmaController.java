package br.com.eatividade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.eatividade.model.Turma;
import br.com.eatividade.service.TurmaService;

@Controller
@RequestMapping("/private/turmas")
public class TurmaController {

	@Autowired private TurmaService turmaService;
	
	@GetMapping
	public String findTurmas(Model model){
		
		List<Turma> turmas = turmaService.findAllOrderById();
		model.addAttribute("turmas", turmas);
		model.addAttribute("turma", new Turma());
		
		return "private/Turma/Turmas";
	}
	
	@PostMapping
	public String novaTurma(Turma turma, RedirectAttributes redirectAttributes){
		
		if(turmaService.salva(turma) != null){
			redirectAttributes.addFlashAttribute("mensagem", "Turma '".concat(turma.getNome()).concat("' adicionada com sucesso."));
		}else{
			redirectAttributes.addFlashAttribute("errorMessage", "Não foi possível adicionar a turma '".concat(turma.getNome()).concat("'."));
		}
		
		return "redirect:/private/turmas";
	}
	
}
