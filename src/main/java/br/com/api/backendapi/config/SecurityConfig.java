package br.com.api.backendapi.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.api.backendapi.repositories.SystemUserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	SystemUserRepository userRepo;

	@Autowired
	JWTFilter filter;

	@Autowired
	UserDetailsServiceImpl uds;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception { // Metodo encarregado de configurar a seguranca da
																	// API
		http.cors().and().csrf().disable().httpBasic().disable().authorizeHttpRequests()
				//Permitir para todos
				.antMatchers("/systemUser/registro", "/systemUser/login", "/usuario/ativar/{id}", "/roles").permitAll()
				//Role Usuário
				.antMatchers("usuario/buscar/{id}", "usuario/salvar", "usuario/atualizar{id}",
						"usuario/remover/{id}", "skill/buscar{id}", "skill/listar", "usuarioSkill/buscar{id}",
						"usuarioSkill/listar", "usuarioSkill/salvar", "usuarioSkill/atualizar{id}", "usuarioSkill/excluir/{id}")
				.hasRole("USUARIO")
				//Role Admin
				.antMatchers("usuario/buscar/{id}","usuario/listar", "usuario/salvar", "usuario/atualizar{id}", "usuario/ativar{id}",
						"usuario/remover/{id}", "skill/buscar{id}", "skill/listar", "skill/salvar", "skill/atualizar{id}",
						"skill/ativar/{id}", "skill/remover/{id}", "usuarioSkill/buscar{id}", "usuarioSkill/listar",
						"usuarioSkill/salvar", "usuarioSkill/atualizar{id}", "usuarioSkill/excluir/{id}")
				.hasRole("ADMIN").and().userDetailsService(uds).exceptionHandling()
				.authenticationEntryPoint((request, response, authException) -> response
						.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}
}