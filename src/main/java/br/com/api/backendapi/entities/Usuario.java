package br.com.api.backendapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tb_usuario")

public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;

	@Column(nullable = false, name = "ativo_usuario")
	private Boolean ativo = true;

	@Email
	@NotNull(message = "Campo e-mail não pode ser nulo ou vazio.")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
	@Column(nullable = false, unique = true, name = "email_usuario")
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull(message = "Campo senha não pode ser nulo ou vazio.")
	@Column(nullable = false, name = "senha_usuario")
	private String senha;

//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "tb_usuario_skill", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
//	private Set<Skill> skills = new HashSet<>();
	
	@OneToMany(mappedBy = "usuario")
    private Set<UsuarioSkill> usuarioSkills = new HashSet<>();

	@OneToOne
	@JoinColumn(name = "system_user_id")
	private SystemUser systemUser;

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuario(Long id, Boolean ativo,
			@Email @NotNull(message = "Campo e-mail não pode ser nulo ou vazio.") @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$") String email,
			@NotNull(message = "Campo senha não pode ser nulo ou vazio.") String senha, Set<UsuarioSkill> usuarioSkills,
			SystemUser systemUser) {
		super();
		this.id = id;
		this.ativo = ativo;
		this.email = email;
		this.senha = senha;
		this.usuarioSkills = usuarioSkills;
		this.systemUser = systemUser;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<UsuarioSkill> getUsuarioSkills() {
		return usuarioSkills;
	}

	public void setUsuarioSkills(Set<UsuarioSkill> usuarioSkills) {
		this.usuarioSkills = usuarioSkills;
	}

	public SystemUser getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", ativo=" + ativo + ", email=" + email + ", senha=" + senha + ", usuarioSkills="
				+ usuarioSkills + ", systemUser=" + systemUser + "]";
	}

	
}
