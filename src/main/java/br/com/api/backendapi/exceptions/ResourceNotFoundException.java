package br.com.api.backendapi.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super("Recurso não encontrado: " + message);
	}
}
