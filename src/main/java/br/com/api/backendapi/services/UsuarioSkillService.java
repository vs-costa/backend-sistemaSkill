package br.com.api.backendapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.api.backendapi.dto.UsuarioSkillAtualizarDTO;
import br.com.api.backendapi.dto.UsuarioSkillDTO;
import br.com.api.backendapi.dto.paginacao.PageResponse;
import br.com.api.backendapi.entities.Skill;
import br.com.api.backendapi.entities.Usuario;
import br.com.api.backendapi.entities.UsuarioSkill;
import br.com.api.backendapi.enums.LevelSkillEnum;
import br.com.api.backendapi.exceptions.ResourceNotFoundException;
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
		UsuarioSkill usuarioSkill = usuarioSkillRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário Skill não encontrado com ID: " + id));
		UsuarioSkillDTO infoUsuarioSkill = usuarioSkillMapper.converterUsuarioSkillDTO(usuarioSkill);
		Skill skill = usuarioSkill.getSkill();
		if (skill != null) {
			infoUsuarioSkill.setSkillNome(skill.getNome());
			infoUsuarioSkill.setSkillDescricao(skill.getDescricao());
			infoUsuarioSkill.setSkillImagem(skill.getImagem());
		}
		return infoUsuarioSkill;
	}

	// GET Listar por usuário
	public List<UsuarioSkillDTO> listarPorUsuario(Long usuarioId) {
		List<UsuarioSkillDTO> infoUsuarioSkill = new ArrayList<>();
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId));
		Set<UsuarioSkill> usuarioSkills = usuario.getUsuarioSkills();
		for (UsuarioSkill usuarioSkill : usuarioSkills) {
			UsuarioSkillDTO usuarioSkillDTO = usuarioSkillMapper.converterUsuarioSkillDTO(usuarioSkill);
			Skill skill = usuarioSkill.getSkill();
			if (skill != null) {
				usuarioSkillDTO.setSkillNome(skill.getNome());
				usuarioSkillDTO.setSkillDescricao(skill.getDescricao());
				usuarioSkillDTO.setSkillImagem(skill.getImagem());
			}
			usuarioSkillDTO.setUsuarioId(usuarioSkill.getUsuario().getId());
			infoUsuarioSkill.add(usuarioSkillDTO);
		}
		return infoUsuarioSkill;
	}

	// GET Listar todos
	public PageResponse<UsuarioSkillDTO> listarTodos(int pagina, int tamanhoPagina) {
		Page<UsuarioSkill> page = usuarioSkillRepository.findAll(PageRequest.of(pagina, tamanhoPagina));

		List<UsuarioSkillDTO> conteudo = page.getContent().stream().map(usuarioSkillMapper::converterUsuarioSkillDTO)
				.collect(Collectors.toList());

		return new PageResponse<>(conteudo, pagina, tamanhoPagina, page.getTotalElements(), page.getTotalPages(),
				page.isLast(), page.isFirst());
	}

	// POST
	public UsuarioSkillDTO salvar(UsuarioSkillDTO usuarioSkillDTO) {
		UsuarioSkill novoUsuarioSkill = new UsuarioSkill();

		// Buscar o usuário correspondente ao ID fornecido
		Usuario usuario = usuarioRepository.findById(usuarioSkillDTO.getUsuarioId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Usuário não encontrado com ID: " + usuarioSkillDTO.getUsuarioId()));
		novoUsuarioSkill.setUsuario(usuario);

		// Buscar a skill correspondente ao ID fornecido
		Skill skill = skillRepository.findById(usuarioSkillDTO.getSkillId()).orElseThrow(
				() -> new ResourceNotFoundException("Skill não encontrada com ID: " + usuarioSkillDTO.getSkillId()));
		novoUsuarioSkill.setSkill(skill);

		novoUsuarioSkill.setLevel(usuarioSkillDTO.getLevel());

		UsuarioSkill usuarioSkillSalvo = usuarioSkillRepository.save(novoUsuarioSkill);

		return usuarioSkillMapper.converterUsuarioSkillDTO(usuarioSkillSalvo);
	}

	// PUT
	public UsuarioSkillAtualizarDTO atualizar(long id, UsuarioSkillAtualizarDTO usuarioSkillDTO) {
		UsuarioSkill registroAntigo = usuarioSkillRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário Skill não encontrado com ID: " + id));

		if (usuarioSkillDTO.getLevel() != null) {
			registroAntigo.setLevel(usuarioSkillDTO.getLevel());
		}
		UsuarioSkillAtualizarDTO usuarioSkillConvertido = usuarioSkillMapper
				.converterUsuarioSkillAtualizarDTO(registroAntigo);
		usuarioSkillRepository.save(registroAntigo);
		return usuarioSkillConvertido;
	}

	// DELETE
	public void excluir(Long id) {

		UsuarioSkill usuarioSkill = usuarioSkillRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário Skill não encontrado com ID: " + id));
		usuarioSkillRepository.delete(usuarioSkill);
	}

	// Filtros
	public List<UsuarioSkillDTO> buscarPorLevel(LevelSkillEnum level) {
		List<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findByLevel(level);
		return converterParaDTO(usuarioSkills);
	}

	public List<UsuarioSkillDTO> buscarPorSkillNome(String skillNome) {
		List<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findBySkillNome(skillNome);
		return converterParaDTO(usuarioSkills);
	}

	public List<UsuarioSkillDTO> buscarPorUsuarioIdESkillNome(Long usuarioId, String skillNome) {
		List<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findByUsuarioIdAndSkillNome(usuarioId, skillNome);
		return converterParaDTO(usuarioSkills);
	}

	private List<UsuarioSkillDTO> converterParaDTO(List<UsuarioSkill> usuarioSkills) {
		List<UsuarioSkillDTO> dtos = new ArrayList<>();
		for (UsuarioSkill usuarioSkill : usuarioSkills) {
			UsuarioSkillDTO dto = usuarioSkillMapper.converterUsuarioSkillDTO(usuarioSkill);
			Skill skill = usuarioSkill.getSkill();
			if (skill != null) {
				dto.setSkillNome(skill.getNome());
				dto.setSkillDescricao(skill.getDescricao());
				dto.setSkillImagem(skill.getImagem());
			}
			dto.setUsuarioId(usuarioSkill.getUsuario().getId());
			dtos.add(dto);
		}
		return dtos;
	}

	// Ordenar por Level
	public List<UsuarioSkillDTO> listarTodasOrdenadasPorLevel() {
		List<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findAll(Sort.by(Sort.Direction.ASC, "level"));
		return usuarioSkills.stream().map(usuarioSkillMapper::converterUsuarioSkillDTO).collect(Collectors.toList());
	}
}
