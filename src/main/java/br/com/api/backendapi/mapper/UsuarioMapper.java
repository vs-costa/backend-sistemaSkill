package br.com.api.backendapi.mapper;

import org.springframework.stereotype.Component;

import br.com.api.backendapi.dto.UsuarioAtualizarDTO;
import br.com.api.backendapi.dto.UsuarioDTO;
import br.com.api.backendapi.entities.Usuario;

@Component

public class UsuarioMapper {

	//Converter DTO
	public UsuarioDTO converterUsuarioDTO(Usuario usuario) {
		UsuarioDTO usuarioConvertido = new UsuarioDTO();
		usuarioConvertido.setId(usuario.getId());
		usuarioConvertido.setEmail(usuario.getEmail());
		usuarioConvertido.setSenha(usuario.getSenha());
		return usuarioConvertido;
	}
	
	//Atualizar DTO
	public UsuarioAtualizarDTO converterUsuarioAtualizarDTO(Usuario usuario) {
		UsuarioAtualizarDTO usuarioConvertido = new UsuarioAtualizarDTO();
		usuarioConvertido.setId(usuario.getId());
		usuarioConvertido.setEmail(usuario.getEmail());
		usuarioConvertido.setSenha(usuario.getSenha());
		return usuarioConvertido;
	}
	
}
