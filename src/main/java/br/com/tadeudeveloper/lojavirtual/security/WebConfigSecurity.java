package br.com.tadeudeveloper.lojavirtual.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.tadeudeveloper.lojavirtual.service.ImplementacaoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(implementacaoUserDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers(HttpMethod.GET, "/salvarAcesso", "/deleteAcesso")
				.antMatchers(HttpMethod.POST, "/salvarAcesso", "/deleteAcesso");
	}

}
