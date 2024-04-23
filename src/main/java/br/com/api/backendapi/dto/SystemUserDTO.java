package br.com.api.backendapi.dto;

import java.util.Set;

public class SystemUserDTO {
	
	private String email;
	private String senha;
	private Set<String> role;
	
	public SystemUserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SystemUserDTO(String email, String senha, Set<String> role) {
		super();
		this.email = email;
		this.senha = senha;
		this.role = role;
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

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}
}
