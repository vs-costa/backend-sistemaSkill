package br.com.api.backendapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.backendapi.dto.UsuarioSkillAtualizarDTO;
import br.com.api.backendapi.dto.UsuarioSkillDTO;
import br.com.api.backendapi.entities.Skill;
import br.com.api.backendapi.entities.Usuario;
import br.com.api.backendapi.entities.UsuarioSkill;
import br.com.api.backendapi.mapper.UsuarioSkillMapper;
import br.com.api.backendapi.repositories.SkillRepository;
import br.com.api.backendapi.repositories.UsuarioRepository;
import br.com.api.backendapi.repositories.UsuarioSkillRepository;

@Service

public class UsuarioSkillService {

	@Autowired
	UsuarioSkillRepository usuarioSkillRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	SkillRepository skillRepository;

	@Autowired
	UsuarioSkillMapper usuarioSkillMapper;

	// GET ID
	public UsuarioSkillDTO buscarPorId(Long id) {
		UsuarioSkillDTO infoUsuarioSkill = new UsuarioSkillDTO();
		Optional<UsuarioSkill> usuarioSkillOptional = usuarioSkillRepository.findById(id);
		if (usuarioSkillOptional.isPresent()) {
			UsuarioSkill usuarioSkill = usuarioSkillOptional.get();
			infoUsuarioSkill = usuarioSkillMapper.converterUsuarioSkillDTO(usuarioSkill);
			Skill skill = usuarioSkill.getSkill();
			if (skill != null) {
				infoUsuarioSkill.setSkillNome(skill.getNome());
				infoUsuarioSkill.setSkillDescricao(skill.getDescricao());
				infoUsuarioSkill.setSkillImagem(skill.getImagem());
			}
		}
		return infoUsuarioSkill;
	}

//	// GET Listar
//	public List<UsuarioSkillDTO> listarTodos() {
//		List<UsuarioSkillDTO> infoUsuarioSkill = new ArrayList<>();
//		List<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findAll();
//		for (UsuarioSkill usuarioSkill : usuarioSkills) {
//			UsuarioSkillDTO usuarioSkillDTO = usuarioSkillMapper.converterUsuarioSkillDTO(usuarioSkill);
//			Skill skill = usuarioSkill.getSkill();
//			if (skill != null) {
//				usuarioSkillDTO.setSkillNome(skill.getNome());
//				usuarioSkillDTO.setSkillDescricao(skill.getDescricao());
//				usuarioSkillDTO.setSkillImagem(skill.getImagem());
//			}
//			usuarioSkillDTO.setUsuarioId(usuarioSkill.getUsuario().getId()); // Adiciona o ID do usuário ao DTO
//			infoUsuarioSkill.add(usuarioSkillDTO);
//		}
//		return infoUsuarioSkill;
//	}

	// GET Listar por usuário
	public List<UsuarioSkillDTO> listarPorUsuario(Long usuarioId) {
		List<UsuarioSkillDTO> infoUsuarioSkill = new ArrayList<>();
		Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioId);
		if (!optionalUsuario.isPresent()) {
			throw new RuntimeException("Usuário não encontrado com o ID: " + usuarioId);
		}
		Usuario usuario = optionalUsuario.get();
		Set<UsuarioSkill> usuarioSkills = usuario.getUsuarioSkills(); // Obtém a coleção de habilidades associadas ao
																		// usuário
		for (UsuarioSkill usuarioSkill : usuarioSkills) {
			UsuarioSkillDTO usuarioSkillDTO = usuarioSkillMapper.converterUsuarioSkillDTO(usuarioSkill);
			Skill skill = usuarioSkill.getSkill();
			if (skill != null) {
				usuarioSkillDTO.setSkillNome(skill.getNome());
				usuarioSkillDTO.setSkillDescricao(skill.getDescricao());
				usuarioSkillDTO.setSkillImagem(skill.getImagem());
			}
			usuarioSkillDTO.setUsuarioId(usuarioSkill.getUsuario().getId()); // Adiciona o ID do usuário ao DTO
			infoUsuarioSkill.add(usuarioSkillDTO);
		}
		return infoUsuarioSkill;
	}
	

	// POST
	public UsuarioSkillDTO salvar(UsuarioSkillDTO usuarioSkillDTO) {
	    UsuarioSkill novoUsuarioSkill = new UsuarioSkill();

	    // Buscar o usuário correspondente ao ID fornecido
	    Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioSkillDTO.getUsuarioId());
	    if (!optionalUsuario.isPresent()) {
	        throw new RuntimeException("Usuário não encontrado com o ID: " + usuarioSkillDTO.getUsuarioId());
	    }
	    Usuario usuario = optionalUsuario.get();
	    novoUsuarioSkill.setUsuario(usuario);

	    // Buscar a skill correspondente ao ID fornecido
	    Optional<Skill> optionalSkill = skillRepository.findById(usuarioSkillDTO.getSkillId());
	    if (!optionalSkill.isPresent()) {
	        throw new RuntimeException("Skill não encontrada com o ID: " + usuarioSkillDTO.getSkillId());
	    }
	    Skill skill = optionalSkill.get();
	    novoUsuarioSkill.setSkill(skill);

	    novoUsuarioSkill.setLevel(usuarioSkillDTO.getLevel());

	    UsuarioSkill usuarioSkillSalvo = usuarioSkillRepository.save(novoUsuarioSkill);

	    return usuarioSkillMapper.converterUsuarioSkillDTO(usuarioSkillSalvo);
	}

	// PUT
	public UsuarioSkillAtualizarDTO atualizar(long id, UsuarioSkillAtualizarDTO usuarioSkillDTO) {
		UsuarioSkill registroAntigo = usuarioSkillRepository.findById(id).get();

		if (usuarioSkillDTO.getLevel() != null) {
			registroAntigo.setLevel(usuarioSkillDTO.getLevel());
		}
		UsuarioSkillAtualizarDTO usuarioSkillConvertido = usuarioSkillMapper
				.converterUsuarioSkillAtualizarDTO(registroAntigo);
		registroAntigo.setId(id);
		usuarioSkillRepository.save(registroAntigo);
		return usuarioSkillConvertido;

	}

	// DELETE
	public void excluir(Long id) {

		UsuarioSkill usuarioSkill = usuarioSkillRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário Skill não encontrado com ID: " + id));
		usuarioSkillRepository.delete(usuarioSkill);
	}
}
