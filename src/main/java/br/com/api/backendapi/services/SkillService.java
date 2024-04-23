package br.com.api.backendapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.backendapi.dto.SkillAtualizarDTO;
import br.com.api.backendapi.dto.SkillDTO;
import br.com.api.backendapi.entities.Skill;
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
		SkillDTO infoSkill = new SkillDTO();
		Skill skill = skillRepository.findById(id).get();
		infoSkill = skillMapper.converterSkillDTO(skill);
		return infoSkill;
	}

	// GET Listar
	public List<SkillDTO> listarTodos() {
		List<SkillDTO> infoSkill = new ArrayList<>();
		List<Skill> skills = skillRepository.findAll();
		for (Skill skill : skills) {
			infoSkill.add(skillMapper.converterSkillDTO(skill));
		}
		return infoSkill;
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
		Skill registroAntigo = skillRepository.findById(id).get();

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
		Skill skill = skillRepository.findById(id).get();

		if (skill != null) {
			skill.setAtivo(false);
			skillRepository.save(skill);
		}
	}

	// Ativar Lógico
	public void ativarLogico(Long id) {
		Skill skill = skillRepository.findById(id).get();

		if (skill != null) {
			skill.setAtivo(true);
			skillRepository.save(skill);
		}
	}
}
