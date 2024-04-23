package br.com.api.backendapi.config;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.api.backendapi.entities.SystemUser;
import br.com.api.backendapi.repositories.RoleRepository;
import br.com.api.backendapi.repositories.SystemUserRepository;
import br.com.api.backendapi.services.SystemUserService;

@Component 
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	SystemUserRepository userRepo;

	@Autowired
	SystemUserService userService;

	@Autowired
	RoleRepository roleRepo;
	
	
	@Override
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		
		Optional<SystemUser> userRes = userRepo.findByEmail(email);
		if (userRes.isEmpty()) {
			throw new UsernameNotFoundException("Não foi possível encontrar usuário com o email = " + email);
		}
		return new org.springframework.security.core.userdetails.User(
				email,
				userRes.get().getSenha(),
				roleRepo.roles(email) 
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList()) 
				);
	}
}