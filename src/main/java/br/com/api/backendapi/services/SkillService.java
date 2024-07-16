package br.com.api.backendapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.api.backendapi.dto.SkillAtualizarDTO;
import br.com.api.backendapi.dto.SkillDTO;
import br.com.api.backendapi.dto.paginacao.PageResponse;
import br.com.api.backendapi.entities.Skill;
import br.com.api.backendapi.exceptions.ResourceNotFoundException;
import br.com.api.backendapi.mapper.SkillMapper;
import br.com.api.backendapi.repositories.SkillRepository;

@Service

public class SkillService {

	@Autowired
	SkillRepository skillRepository;

	@Autowired
	SkillMapper skillMapper;

	// GET ID
	public SkillDTO buscarPorId(Long id) {
		Skill skill = skillRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Skill com id " + id + " não encontrada."));
		return skillMapper.converterSkillDTO(skill);
	}

	// GET Listar com paginação
	public PageResponse<SkillDTO> listarTodos(int pagina, int tamanhoPagina) {
		Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
		Page<Skill> skillsPage = skillRepository.findAll(pageable);

		List<SkillDTO> skillsDTO = skillsPage.getContent().stream().map(skillMapper::converterSkillDTO)
				.collect(Collectors.toList());

		return new PageResponse<>(skillsDTO, skillsPage.getNumber(), skillsPage.getSize(),
				skillsPage.getTotalElements(), skillsPage.getTotalPages(), skillsPage.isLast(), skillsPage.isFirst());
	}

	// Listar ordenado por nome
	public PageResponse<SkillDTO> listarSkillsOrdenadasPorNome(int pagina, int tamanhoPagina) {
		Pageable pageable = PageRequest.of(pagina, tamanhoPagina, Sort.by(Sort.Direction.ASC, "nome"));
		Page<Skill> skillsPage = skillRepository.findAll(pageable);

		List<SkillDTO> skillsDTO = skillsPage.getContent().stream().map(skillMapper::converterSkillDTO)
				.collect(Collectors.toList());

		return new PageResponse<>(skillsDTO, skillsPage.getNumber(), skillsPage.getSize(),
				skillsPage.getTotalElements(), skillsPage.getTotalPages(), skillsPage.isLast(), skillsPage.isFirst());
	}

	// POST
	public Skill salvar(SkillDTO skillDTO) {
		Skill salvarSkill = new Skill();
		salvarSkill.setNome(skillDTO.getNome());
		salvarSkill.setDescricao(skillDTO.getDescricao());
		salvarSkill.setImagem(skillDTO.getImagem());
		return skillRepository.save(salvarSkill);
	}

	// PUT
	public SkillAtualizarDTO atualizar(Long id, SkillAtualizarDTO skillDTO) {
		Skill registroAntigo = skillRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Skill com id " + id + " não encontrada."));

		if (skillDTO.getNome() != null) {
			registroAntigo.setNome(skillDTO.getNome());
		}
		if (skillDTO.getDescricao() != null) {
			registroAntigo.setDescricao(skillDTO.getNome());
		}
		if (skillDTO.getImagem() != null) {
			registroAntigo.setImagem(skillDTO.getImagem());
		}
		SkillAtualizarDTO skillConvertida = skillMapper.converterSkillAtualizarDTO(registroAntigo);
		registroAntigo.setId(id);
		skillRepository.save(registroAntigo);
		return skillConvertida;
	}

	// Delete lógico
	public void removerLogico(Long id) {
		Skill skill = skillRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Skill com id " + id + " não encontrada."));

		skill.setAtivo(false);
		skillRepository.save(skill);
	}

	// Ativar Lógico
	public void ativarLogico(Long id) {
		Skill skill = skillRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Skill com id " + id + " não encontrada."));

		skill.setAtivo(true);
		skillRepository.save(skill);
	}

	// Filtro buscar por nome
	public List<SkillDTO> buscarPorNome(String nome) {
		List<Skill> skills = skillRepository.findByNome(nome);
		return skills.stream().map(skillMapper::converterSkillDTO).collect(Collectors.toList());
	}

	// Filtro buscar por descrição
	public List<SkillDTO> buscarPorDescricao(String descricao) {
		List<Skill> skills = skillRepository.findByDescricao(descricao);
		return skills.stream().map(skillMapper::converterSkillDTO).collect(Collectors.toList());
	}

	// Filtro buscar por ativo
	public List<SkillDTO> buscarPorAtivo(boolean ativo) {
		List<Skill> skills = skillRepository.findByAtivo(ativo);
		return converterParaDTO(skills);
	}

	private List<SkillDTO> converterParaDTO(List<Skill> skills) {
		List<SkillDTO> dtos = new ArrayList<>();
		for (Skill skill : skills) {
			dtos.add(skillMapper.converterSkillDTO(skill));
		}
		return dtos;
	}
}
