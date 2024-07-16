package br.com.api.backendapi.controllers;

import javax.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.backendapi.dto.UsuarioAtualizarDTO;
import br.com.api.backendapi.dto.UsuarioDTO;
import br.com.api.backendapi.dto.paginacao.PageResponse;
import br.com.api.backendapi.entities.Usuario;
import br.com.api.backendapi.services.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/usuario")

public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@ApiOperation(value = "Retorna um usuário", notes = "Usuário")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna um usuário"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Não há permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 505, message = "Exceção interna da aplicação"), })

	@GetMapping("/buscar/{id}")
	public UsuarioDTO buscarPorID(@PathVariable Long id) {
		return usuarioService.buscarPorId(id);
	}

	@GetMapping("/listar")
	public PageResponse<UsuarioDTO> listarTodos(@RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "5") int tamanhoPagina) {
		return usuarioService.listarTodos(pagina, tamanhoPagina);
	}
	
	// Ordenar por email
	@GetMapping("/listarOrdenadoPorEmail")
	public PageResponse<UsuarioDTO> listarUsuarioOrdenadosPorEmail(@RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "5") int tamanhoPagina) {
		return usuarioService.listarUsuarioOrdenadosPorEmail(pagina, tamanhoPagina);
	}

	@PostMapping("/salvar")
	public Usuario salvar(@Valid @RequestBody Usuario usuario) {
		return usuarioService.salvar(usuario);
	}

	@PutMapping("/atualizar/{id}")
	public UsuarioAtualizarDTO atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioAtualizarDTO usuario) {
		return usuarioService.atualizar(id, usuario);
	}

	@PutMapping("/ativar/{id}")
	public void ativarLogico(@PathVariable Long id) {
		usuarioService.ativarLogico(id);
	}

	@DeleteMapping("/remover/{id}")
	public void removerLogico(@PathVariable Long id) {
		usuarioService.removerLogico(id);
	}

	@GetMapping("/buscar")
	public List<UsuarioDTO> buscarPorCriterios(@RequestParam(required = false) String email,
			@RequestParam(required = false) Boolean ativo) {
		return usuarioService.buscarPorCriterios(email, ativo);
	}


}
