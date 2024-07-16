package br.com.api.backendapi.controllers;

import javax.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.api.backendapi.dto.SkillAtualizarDTO;
import br.com.api.backendapi.dto.SkillDTO;
import br.com.api.backendapi.dto.paginacao.PageResponse;
import br.com.api.backendapi.entities.Skill;
import br.com.api.backendapi.services.SkillService;

@RestController
@RequestMapping("/skill")
public class SkillController {

	@Autowired
	SkillService skillService;

	// GET por ID
	@GetMapping("/buscar/{id}")
	public SkillDTO buscarPorId(@PathVariable Long id) {
		return skillService.buscarPorId(id);
	}

	// Listar com paginação
	@GetMapping("/listar")
	public PageResponse<SkillDTO> listarTodos(@RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "10") int tamanhoPagina) {
		return skillService.listarTodos(pagina, tamanhoPagina);
	}

	// Listar ordenado por nome
	@GetMapping("/listarOrdenadasPorNome")
	public PageResponse<SkillDTO> listarSkillsOrdenadasPorNome(@RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "10") int tamanhoPagina) {
		return skillService.listarSkillsOrdenadasPorNome(pagina, tamanhoPagina);
	}

	// POST salvar
	@PostMapping("/salvar")
	public ResponseEntity<Skill> criarSkill(@Valid @RequestBody SkillDTO skillDTO) {
		Skill skill = skillService.salvar(skillDTO);
		return ResponseEntity.ok(skill);
	}

	// PUT atualizar
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<SkillAtualizarDTO> atualizarSkill(@PathVariable Long id,
			@Valid @RequestBody SkillAtualizarDTO skillDTO) {
		SkillAtualizarDTO skillAtualizada = skillService.atualizar(id, skillDTO);
		return ResponseEntity.ok(skillAtualizada);
	}

	// Ativar lógico
	@PutMapping("/ativar/{id}")
	public void ativarLogico(@PathVariable Long id) {
		skillService.ativarLogico(id);
	}

	// Delete lógico
	@DeleteMapping("/remover/{id}")
	public void removerLogico(@PathVariable Long id) {
		skillService.removerLogico(id);
	}

	// Buscar por nome
	@GetMapping("/buscar/nome")
	public List<SkillDTO> buscarPorNome(@RequestParam String nome) {
		return skillService.buscarPorNome(nome);
	}

	// Buscar por descrição
	@GetMapping("/buscar/descricao")
	public List<SkillDTO> buscarPorDescricao(@RequestParam String descricao) {
		return skillService.buscarPorDescricao(descricao);
	}

	// Buscar por ativo
	@GetMapping("/buscar/ativo")
	public List<SkillDTO> buscarPorAtivo(@RequestParam Boolean ativo) {
		return skillService.buscarPorAtivo(ativo);
	}
}
