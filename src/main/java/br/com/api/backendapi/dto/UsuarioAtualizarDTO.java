package br.com.api.backendapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAtualizarDTO {
	
	private Long id;
	
	@Email(message = "Deve ser um endereço de e-mail válido")
	private String email;
	
	@Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
	private String senha;
	
}
