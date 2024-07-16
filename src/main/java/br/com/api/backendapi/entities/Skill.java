package br.com.api.backendapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_skill")
@Getter
@Setter
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_skill")
	private Long id;

	@Column(name = "ativa_skill")
	private Boolean ativo = true;

	@NotBlank(message = "O nome da skill não pode ser nulo ou vazia.")
	@Column(nullable = false, name = "nome_skill")
	private String nome;

	@NotBlank(message = "A descrição da skill não pode ser nulo ou vazia.")
	@Column(nullable = false, name = "descricao_skill")
	private String descricao;

	@NotBlank(message = "O caminho do arquivo da imagem não pode ser nulo ou vazio.")
	@Column(nullable = false, name = "imagem_skill")
	private String imagem;

	@OneToMany(mappedBy = "skill")
    private Set<UsuarioSkill> usuarioSkills = new HashSet<>();

}
