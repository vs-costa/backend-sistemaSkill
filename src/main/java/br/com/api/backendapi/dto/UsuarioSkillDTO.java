package br.com.api.backendapi.dto;

import br.com.api.backendapi.enums.LevelSkillEnum;

public class UsuarioSkillDTO {
    
    private Long usuarioId;
    private Long skillId;
    private String skillNome;
    private String skillDescricao;
    private String skillImagem;
    private LevelSkillEnum level;

    public UsuarioSkillDTO() {
        super();
    }

	public UsuarioSkillDTO(Long usuarioId, Long skillId, String skillNome, String skillDescricao, String skillImagem,
			LevelSkillEnum level) {
		super();
		this.usuarioId = usuarioId;
		this.skillId = skillId;
		this.skillNome = skillNome;
		this.skillDescricao = skillDescricao;
		this.skillImagem = skillImagem;
		this.level = level;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Long getSkillId() {
		return skillId;
	}

	public void setSkillId(Long skillId) {
		this.skillId = skillId;
	}

	public String getSkillNome() {
		return skillNome;
	}

	public void setSkillNome(String skillNome) {
		this.skillNome = skillNome;
	}

	public String getSkillDescricao() {
		return skillDescricao;
	}

	public void setSkillDescricao(String skillDescricao) {
		this.skillDescricao = skillDescricao;
	}

	public String getSkillImagem() {
		return skillImagem;
	}

	public void setSkillImagem(String skillImagem) {
		this.skillImagem = skillImagem;
	}

	public LevelSkillEnum getLevel() {
		return level;
	}

	public void setLevel(LevelSkillEnum level) {
		this.level = level;
	}
}
