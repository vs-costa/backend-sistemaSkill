package br.com.api.backendapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_skill")

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

	@NotNull(message = "A descrição da skill não pode ser nulo ou vazia.")
	@Column(nullable = false, name = "descricao_skill")
	private String descricao;

	@NotNull(message = "O caminho do arquivo da imagem não pode ser nulo ou vazio.")
	@Column(nullable = false, name = "imagem_skill")
	private String imagem;

//	@ManyToMany(mappedBy = "skills")
//	private Set<Usuario> usuarios = new HashSet<>();
	
	@OneToMany(mappedBy = "skill")
    private Set<UsuarioSkill> usuarioSkills = new HashSet<>();

	public Skill() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Skill(Long id, Boolean ativo, @NotBlank(message = "O nome da skill não pode ser nulo ou vazia.") String nome,
			@NotNull(message = "A descrição da skill não pode ser nulo ou vazia.") String descricao,
			@NotNull(message = "O caminho do arquivo da imagem não pode ser nulo ou vazio.") String imagem,
			Set<UsuarioSkill> usuarioSkills) {
		super();
		this.id = id;
		this.ativo = ativo;
		this.nome = nome;
		this.descricao = descricao;
		this.imagem = imagem;
		this.usuarioSkills = usuarioSkills;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Set<UsuarioSkill> getUsuarioSkills() {
		return usuarioSkills;
	}

	public void setUsuarioSkills(Set<UsuarioSkill> usuarioSkills) {
		this.usuarioSkills = usuarioSkills;
	}

	@Override
	public String toString() {
		return "Skill [id=" + id + ", ativo=" + ativo + ", nome=" + nome + ", descricao=" + descricao + ", imagem="
				+ imagem + ", usuarioSkills=" + usuarioSkills + "]";
	}
}
