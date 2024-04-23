package br.com.api.backendapi.enums;

public enum TipoRoleEnum {
	
	ROLE_USUARIO("USUARIO"),
	ROLE_ADMIN("ADMIN");
	
	private String tipo;
	
	TipoRoleEnum(String tipo) {
		this.tipo = tipo;
	}
	
	public String getTipo() {
		return tipo;
	}
}
