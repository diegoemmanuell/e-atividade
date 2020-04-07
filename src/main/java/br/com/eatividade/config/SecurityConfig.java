package br.com.eatividade.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired private PasswordEncoder pass;
	
	@Bean public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
          .withUser("diretor").password(pass.encode("diretor")).roles("DIRETOR")
          .and()
          .withUser("professores").password(pass.encode("professores")).roles("PROFESSOR")
          .and()
          .withUser("aluno.censg").password(pass.encode("aluno.censg")).roles("ALUNO");
    }
	
	@Override
    protected void configure(final HttpSecurity http) throws Exception {
		http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/css/**, /js/**, /login/**").permitAll()
        .antMatchers("/private/turmas/**").hasAnyRole("DIRETOR")
        .antMatchers("/private/atividades/deleta/**").hasAnyRole("DIRETOR")
		.antMatchers("/private/**").authenticated()
		.and()
		.exceptionHandling().accessDeniedPage("/login/error/403")
		.and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/login/check")
		.defaultSuccessUrl("/")
		.failureUrl("/login/error")
		.and()
		.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/login")
		.deleteCookies("JSESSIONID")
		.invalidateHttpSession(true);
	}

}
