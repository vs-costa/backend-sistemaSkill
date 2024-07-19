package br.com.api.backendapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.api.backendapi.enums.TipoRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_roles")
@Getter
@Setter
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "É necessário definir uma role de usuário.")
	@Column(nullable = false, name = "name")
	private TipoRoleEnum name;

}
