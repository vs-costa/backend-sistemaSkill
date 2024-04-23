package br.com.api.backendapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.api.backendapi.enums.LevelSkillEnum;

@Entity
@Table(name = "tb_usuario_skill")
public class UsuarioSkill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_usuario_skill")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @NotNull(message = "O nível de habilidade não pode ser nulo")
    private LevelSkillEnum level;

	public UsuarioSkill() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsuarioSkill(Long id, Usuario usuario, Skill skill,
			@NotNull(message = "O nível de habilidade não pode ser nulo") LevelSkillEnum level) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.skill = skill;
		this.level = level;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public LevelSkillEnum getLevel() {
		return level;
	}

	public void setLevel(LevelSkillEnum level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "UsuarioSkill [id=" + id + ", usuario=" + usuario + ", skill=" + skill + ", level=" + level + "]";
	}
}
