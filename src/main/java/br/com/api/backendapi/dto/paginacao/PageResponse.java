package br.com.api.backendapi.dto.paginacao;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse<T> {
	
	    private List<T> conteudo;
	    private int pagina;
	    private int tamanhoPagina;
	    private long totalElementos;
	    private int totalPaginas;
	    private boolean ultima;
	    private boolean primeira;

	    public PageResponse(List<T> conteudo, int pagina, int tamanhoPagina, long totalElementos, int totalPaginas, boolean ultima, boolean primeira) {
	        this.conteudo = conteudo;
	        this.pagina = pagina;
	        this.tamanhoPagina = tamanhoPagina;
	        this.totalElementos = totalElementos;
	        this.totalPaginas = totalPaginas;
	        this.ultima = ultima;
	        this.primeira = primeira;
	    }
}
