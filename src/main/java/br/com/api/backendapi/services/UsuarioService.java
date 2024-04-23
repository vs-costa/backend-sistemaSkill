package br.com.api.backendapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.backendapi.config.PasswordEncoder;
import br.com.api.backendapi.dto.UsuarioAtualizarDTO;
import br.com.api.backendapi.dto.UsuarioDTO;
import br.com.api.backendapi.entities.SystemUser;
import br.com.api.backendapi.entities.Usuario;
import br.com.api.backendapi.mapper.UsuarioMapper;
import br.com.api.backendapi.repositories.UsuarioRepository;

@Service

public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	UsuarioMapper usuarioMapper;

	@Autowired
	SystemUserService systemUserService;

	// Get ID
	public UsuarioDTO buscarPorId(Long id) {
		UsuarioDTO infoUsuario = new UsuarioDTO();
		Usuario usuario = usuarioRepository.findById(id).get();
		infoUsuario = usuarioMapper.converterUsuarioDTO(usuario);
		return infoUsuario;
	}

	// Get Listar
	public List<UsuarioDTO> listarTodos() {
		List<UsuarioDTO> infoUsuario = new ArrayList<>();
		List<Usuario> usuarios = usuarioRepository.findAll();
		for (Usuario usuario : usuarios) {
			infoUsuario.add(usuarioMapper.converterUsuarioDTO(usuario));
		}
		return infoUsuario;
	}

	// POST
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	// PUT
	public UsuarioAtualizarDTO atualizar(Long id, UsuarioAtualizarDTO usuarioDTO) {
	    Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	    
	    if (usuarioDTO.getEmail() != null) {
	        SystemUser systemUser = systemUserService.findByEmail(usuario.getEmail());
	        systemUser.setEmail(usuarioDTO.getEmail());
	        systemUserService.save(systemUser);
	        usuario.setEmail(usuarioDTO.getEmail());
	    }
	    
	    if (usuarioDTO.getSenha() != null) {
	        SystemUser systemUser = systemUserService.findByEmail(usuario.getEmail());
	        String senhaCriptografada = PasswordEncoder.encodePassword(usuarioDTO.getSenha());
	        systemUser.setSenha(senhaCriptografada);
	        systemUserService.save(systemUser);
	        usuario.setSenha(senhaCriptografada);
	    }
	    
	    usuarioRepository.save(usuario);
	    
	    return usuarioMapper.converterUsuarioAtualizarDTO(usuario);
	}

	// Delete lógico
	public void removerLogico(Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();

		if (usuario != null) {
			usuario.setAtivo(false);
			usuarioRepository.save(usuario);
		}
	}

	// Ativar Lógico
	public void ativarLogico(Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();

		if (usuario != null) {
			usuario.setAtivo(true);
			usuarioRepository.save(usuario);
		}
	}
}
