package br.com.eatividade.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


@Service
public class AuthorityService {

	public boolean isAluno(User user) {
		return possuiPermissao(user, "ALUNO");
	}

	private boolean possuiPermissao(User user, String role) {
		
		user.getAuthorities();
		for(GrantedAuthority auth : user.getAuthorities()){
			if(auth.getAuthority().equals("ROLE_" + role)){
				return true;
			}
		}
		
		return false;
	}

}
