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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;

	@Column(nullable = false, name = "ativo_usuario")
	private Boolean ativo = true;

	@Email
	@NotBlank(message = "Campo e-mail não pode ser nulo ou vazio.")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
	@Column(nullable = false, unique = true, name = "email_usuario")
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "Campo senha não pode ser nulo ou vazio.")
	@Column(nullable = false, name = "senha_usuario")
	private String senha;

	
	@OneToMany(mappedBy = "usuario")
    private Set<UsuarioSkill> usuarioSkills = new HashSet<>();

	@OneToOne
	@JoinColumn(name = "system_user_id")
	private SystemUser systemUser;

	
}
