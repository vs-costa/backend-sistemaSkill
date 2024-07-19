package br.com.api.backendapi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillAtualizarDTO {

	private Long id;
	
	@NotBlank(message = "O nome é obrigatório")
    @Size(max = 255, message = "O nome não pode ter mais de 255 caracteres")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 255, message = "A descrição não pode ter mais de 255 caracteres")
    private String descricao;

    private String imagem;
	
	
}
