package br.com.api.backendapi.controllers;

import javax.transaction.Transactional;
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

import br.com.api.backendapi.dto.UsuarioSkillAtualizarDTO;
import br.com.api.backendapi.dto.UsuarioSkillDTO;
import br.com.api.backendapi.entities.UsuarioSkill;
import br.com.api.backendapi.mapper.UsuarioSkillMapper;
import br.com.api.backendapi.repositories.UsuarioSkillRepository;
import br.com.api.backendapi.services.UsuarioSkillService;

@RestController
@RequestMapping("usuarioSkill")

public class UsuarioSkillController {

	@Autowired
	UsuarioSkillService usuarioSkillService;

	@Autowired
	UsuarioSkillRepository usuarioSkillRepository;

	@Autowired
	UsuarioSkillMapper usuarioSkillMapper;

	@GetMapping("/buscar/{id}")
	public UsuarioSkillDTO buscarPorID(@PathVariable Long id) {
		return usuarioSkillService.buscarPorId(id);
	}

	@GetMapping("/listar/{usuarioId}")
	public List<UsuarioSkillDTO> listarPorUsuario(@PathVariable Long usuarioId) {
		return usuarioSkillService.listarPorUsuario(usuarioId);
	}

	@PostMapping("/salvar")
	public UsuarioSkillDTO salvar(@Valid @RequestBody UsuarioSkillDTO usuarioSkill) {
		UsuarioSkillDTO savedUsuarioSkillDTO = usuarioSkillService.salvar(usuarioSkill);
		return savedUsuarioSkillDTO;
	}
	
	@PutMapping("/atualizar/{id}")
	public UsuarioSkillAtualizarDTO atualizar(@PathVariable Long id,
			@Valid @RequestBody UsuarioSkillAtualizarDTO usuarioSkillAtualizarDTO) {
		UsuarioSkill usuarioSkill = usuarioSkillRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário Skill não encontrado com ID: " + id));

		if (usuarioSkillAtualizarDTO.getLevel() != null) {
			usuarioSkill.setLevel(usuarioSkillAtualizarDTO.getLevel());
		}

		UsuarioSkill usuarioSkillAtualizado = usuarioSkillRepository.save(usuarioSkill);
		return usuarioSkillMapper.converterUsuarioSkillAtualizarDTO(usuarioSkillAtualizado);
	}
	

	@DeleteMapping("/excluir/{id}")
	public void excluir(@PathVariable Long id) {
		usuarioSkillService.excluir(id);
	}
	
}
