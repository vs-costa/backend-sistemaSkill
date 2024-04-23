package br.com.api.backendapi.controllers;

import javax.validation.Valid;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.backendapi.config.JWTUtil;
import br.com.api.backendapi.dto.LoginDTO;
import br.com.api.backendapi.dto.SystemUserDTO;
import br.com.api.backendapi.entities.Role;
import br.com.api.backendapi.entities.SystemUser;
import br.com.api.backendapi.entities.Usuario;
import br.com.api.backendapi.enums.TipoRoleEnum;
import br.com.api.backendapi.repositories.RoleRepository;
import br.com.api.backendapi.services.SystemUserService;
import br.com.api.backendapi.services.UsuarioService;

@RestController
@RequestMapping("/systemUser")

public class SystemUserController {

//	private EmailService emailService;

	@Autowired
	SystemUserService systemUserService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/registro")
	public ResponseEntity<String> cadastro(@RequestParam String email, @Valid @RequestBody SystemUserDTO user) {

	    Set<String> strRoles = user.getRole();

	    if (strRoles == null || strRoles.isEmpty()) {
	        strRoles = Collections.singleton("USUARIO");
	    }

	    for (String role : strRoles) {
	        Set<Role> roles = new HashSet<>();

	        switch (role) {
	            case "ADMIN":
	                Role adminRole = roleRepository.findByName(TipoRoleEnum.ROLE_ADMIN)
	                        .orElseThrow(() -> new RuntimeException("Erro: Role não encontrada."));
	                roles.add(adminRole);
	                break;
	            case "USUARIO":
	                Role userRole = roleRepository.findByName(TipoRoleEnum.ROLE_USUARIO)
	                        .orElseThrow(() -> new RuntimeException("Erro: Role não encontrada."));
	                roles.add(userRole);
	                break;
	            default:
	                throw new RuntimeException("Erro: Role inválida.");
	        }

	        // Criar e salvar o usuário com as roles específicas
	        Usuario usuario = new Usuario();
	        SystemUser usuarioResumido = new SystemUser();
	        String encodedPass = passwordEncoder.encode(user.getSenha());
	        usuarioResumido.setEmail(user.getEmail());
	        usuarioResumido.setRole(roles);
	        usuarioResumido.setSenha(encodedPass);
	        systemUserService.save(usuarioResumido);
	        usuario.setEmail(user.getEmail());
	        usuario.setSenha(encodedPass);
	        usuario.setSystemUser(usuarioResumido);
	        usuarioService.salvar(usuario);
	    }

	    return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro efetuado com sucesso!");
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginDTO body) {
	    try {
	        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
	                body.getEmail(), body.getPassword());
	        
	        authManager.authenticate(authInputToken);
	        SystemUser user = systemUserService.findByEmail(body.getEmail());
	        Map<String, Object> response = new HashMap<>();
	        String token = jwtUtil.generateTokenWithUserData(user);
	      
	        response.put("token", token);
	        response.put("userId", user.getIdSystemUser());

	        // Retornar a resposta com o token e o userId
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    } catch (AuthenticationException authExc) {
	        throw new RuntimeException("Credenciais Inválidas");
	    }
	}

//	@PostMapping("/login")
//	public ResponseEntity<String> login(@Valid @RequestBody LoginDTO body) {
//		try {
//			UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
//					body.getEmail(), body.getPassword());
//
//			authManager.authenticate(authInputToken);
//
//			SystemUser user = systemUserService.findByEmail(body.getEmail());
//			SystemUser usuarioResumido = new SystemUser();
//			usuarioResumido.setEmail(user.getEmail());
//			usuarioResumido.setIdSystemUser(user.getIdSystemUser());
//			usuarioResumido.setRole(user.getRole());
//			String token = jwtUtil.generateTokenWithUserData(usuarioResumido);
//			
//			Map<String, Object> response = new HashMap<>();
//	        response.put("token", token);
//	        response.put("userId", usuarioResumido.getIdSystemUser());
//			
//			return ResponseEntity.status(HttpStatus.OK).body("Login efetuado com sucesso!\n\nToken:"+token);
//		} catch (AuthenticationException authExc) {
//			throw new RuntimeException("Credenciais Invalidas");
//		}
//	}
}
