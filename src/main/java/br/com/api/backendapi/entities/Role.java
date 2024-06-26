package br.com.api.backendapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.api.backendapi.enums.TipoRoleEnum;

@Entity
@Table(name = "tb_roles")

public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "É necessário definir uma role de usuário.")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "name")
	private TipoRoleEnum name;

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(Long id, @NotNull(message = "É necessário definir uma role de usuário.") TipoRoleEnum name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoRoleEnum getName() {
		return name;
	}

	public void setName(TipoRoleEnum name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}
}
