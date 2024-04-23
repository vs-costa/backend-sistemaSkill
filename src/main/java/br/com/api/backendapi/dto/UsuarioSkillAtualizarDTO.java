package br.com.api.backendapi.dto;

import br.com.api.backendapi.enums.LevelSkillEnum;

public class UsuarioSkillAtualizarDTO {
	
	
	private LevelSkillEnum level;
	
	public UsuarioSkillAtualizarDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsuarioSkillAtualizarDTO(LevelSkillEnum level) {
		super();
		this.level = level;
	}

	public LevelSkillEnum getLevel() {
		return level;
	}

	public void setLevel(LevelSkillEnum level) {
		this.level = level;
	}
}
