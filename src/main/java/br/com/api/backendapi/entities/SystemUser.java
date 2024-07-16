package br.com.api.backendapi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_system_user")
@Getter
@Setter
public class SystemUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSystemUser;
	
	@NotBlank(message = "Campo e-mail não pode ser nulo")
    @Email(message = "Email deve ser válido")
	private String email;
	
	@ManyToMany
	@JoinTable(name = "usuario_role", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> role = new HashSet<>();
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Campo senha não pode ser nulo")
	private String senha;

	
}
