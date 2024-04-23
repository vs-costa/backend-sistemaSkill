package br.com.api.backendapi.dto;

public class SkillAtualizarDTO {

	private String nome;
	private String descricao;
	private String imagem;
	
	public SkillAtualizarDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SkillAtualizarDTO(String nome, String descricao, String imagem) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.imagem = imagem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
}
