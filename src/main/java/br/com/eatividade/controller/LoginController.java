package br.com.eatividade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping
	public String getLogin() {
		return "public/Login";
	}
	
	@GetMapping("/error")
	public String getLoginError(RedirectAttributes attributes) {
		attributes.addFlashAttribute("errorMessage", "Login e/ou senha inválidos.");
		return "redirect:/login";
	}
	
	@GetMapping("/error/403")
	public String getError403(RedirectAttributes attributes) {
		attributes.addFlashAttribute("errorMessage", "Você não tem permissão de acesso à página solicitada.");
		
		return "redirect:/private/";
	}
	
}
