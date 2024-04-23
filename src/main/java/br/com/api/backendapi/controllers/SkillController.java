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
import org.springframework.web.bind.annotation.RestController;

import br.com.api.backendapi.dto.SkillAtualizarDTO;
import br.com.api.backendapi.dto.SkillDTO;
import br.com.api.backendapi.entities.Skill;
import br.com.api.backendapi.services.SkillService;

@RestController
@RequestMapping("/skill")

public class SkillController {
	
	@Autowired
	SkillService skillService;
	
	@GetMapping("/buscar/{id}")
	public SkillDTO buscarPorId(@PathVariable Long id) {
		return skillService.buscarPorId(id);
	}
	
	@GetMapping("/listar")
	public List<SkillDTO> listarTodos() {
		return skillService.listarTodos();
	}
	
	@PostMapping("/salvar")
		public Skill salvar(@Valid @RequestBody SkillDTO skillDTO) {
			return skillService.salvar(skillDTO);
		}
	
	@PutMapping("/atualizar/{id}")
	public SkillAtualizarDTO atualizar(@PathVariable Long id, @Valid @RequestBody SkillAtualizarDTO skill) {
		return skillService.atualizar(id, skill);
	}
	
	@PutMapping("/ativar/{id}")
	public void ativarLogico(@PathVariable Long id) {
		skillService.ativarLogico(id);
	}
	
	@DeleteMapping("/remover/{id}")
	public void removerLogico(@PathVariable Long id) {
		skillService.removerLogico(id);
	}
}
