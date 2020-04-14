package br.com.eatividade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.eatividade.model.Atividade;
import br.com.eatividade.model.Turma;
import br.com.eatividade.service.AtividadeService;
import br.com.eatividade.service.TurmaService;

@Controller
@RequestMapping("/private/alunos")
public class AlunoController {

	@Autowired private TurmaService turmaService;
	@Autowired private AtividadeService atividadeService;
	
	@GetMapping("/turmas")
	public String getTurmas(Model model){
		
		List<Turma> turmas = turmaService.findAllOrderById();
		
		model.addAttribute("turmas", turmas);
		
		return "private/Aluno/Turmas";
	}
	
	@GetMapping("/atividades/{idTurma}")
	public String findAtividadesByTurma(@PathVariable("idTurma") Long idTurma, Model model){
		
		Turma turma = turmaService.findById(idTurma);
		
		List<Atividade> atividades = atividadeService.findAll(turma);
		
		if(atividades.isEmpty()){
			model.addAttribute("errorMessage", "Nenhuma atividade foi encontrada para a turma ".concat(turma.getNome().concat(".")));
		}
		
		model.addAttribute("atividades", atividades);
		model.addAttribute("turma", turma);
		return "private/Aluno/Atividades";
	}
	
}
