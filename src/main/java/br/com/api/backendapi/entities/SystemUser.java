package br.com.api.backendapi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tb_system_user")

public class SystemUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSystemUser;
	
	@NotNull(message = "Campo e-mail não pode ser nulo")
	private String email;
	
	@ManyToMany
	@JoinTable(name = "usuario_role", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> role = new HashSet<>();
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String senha;

	public SystemUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SystemUser(Long idSystemUser, @NotNull(message = "Campo e-mail não pode ser nulo") String email,
			Set<Role> role, String senha) {
		super();
		this.idSystemUser = idSystemUser;
		this.email = email;
		this.role = role;
		this.senha = senha;
	}

	public Long getIdSystemUser() {
		return idSystemUser;
	}

	public void setIdSystemUser(Long idSystemUser) {
		this.idSystemUser = idSystemUser;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "SystemUser [idSystemUser=" + idSystemUser + ", email=" + email + ", role=" + role + ", senha=" + senha
				+ "]";
	}
}
