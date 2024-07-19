package br.com.api.backendapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
	
	private Long id;
	
	@NotBlank(message = "Campo e-mail não pode ser nulo")
	@Email(message = "Email deve ser válido")
	private String email;
	
	@NotBlank(message = "Campo senha não pode ser nulo")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
	private String senha;
	
	
}
