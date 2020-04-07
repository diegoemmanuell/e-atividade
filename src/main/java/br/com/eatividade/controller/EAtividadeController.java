package br.com.eatividade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.eatividade.service.AuthorityService;

@Controller
@RequestMapping("/private/")
public class EAtividadeController {

	@Autowired private AuthorityService authorityService;
	
	@GetMapping
	public String index(@AuthenticationPrincipal User user) {
		return authorityService.isAluno(user) ? "redirect:/private/alunos/turmas" : "private/Inicio" ;
	}
	
}
