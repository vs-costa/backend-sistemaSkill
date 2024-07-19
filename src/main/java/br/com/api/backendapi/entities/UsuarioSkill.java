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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_usuario_skill")
@Getter
@Setter
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

}
