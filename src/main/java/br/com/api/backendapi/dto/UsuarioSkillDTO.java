package br.com.api.backendapi.dto;

import br.com.api.backendapi.enums.LevelSkillEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSkillDTO {
    
	private Long id;
    private Long usuarioId;
    private Long skillId;
    private String skillNome;
    private String skillDescricao;
    private String skillImagem;
    private LevelSkillEnum level;

}
