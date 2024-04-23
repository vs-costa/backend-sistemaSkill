package br.com.api.backendapi.dto;

public class UsuarioAtualizarDTO {
	
	private String email;
	private String senha;
	
	public UsuarioAtualizarDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsuarioAtualizarDTO(String email, String senha) {
		super();
		this.email = email;
		this.senha = senha;
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
}
