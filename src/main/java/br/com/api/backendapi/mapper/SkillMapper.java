package br.com.api.backendapi.mapper;

import org.springframework.stereotype.Component;

import br.com.api.backendapi.dto.SkillAtualizarDTO;
import br.com.api.backendapi.dto.SkillDTO;
import br.com.api.backendapi.entities.Skill;

@Component

public class SkillMapper {
	
	
	//Converter DTO
	public SkillDTO converterSkillDTO(Skill skill) {
		SkillDTO skillConvertida = new SkillDTO();
		skillConvertida.setId(skill.getId());
		skillConvertida.setNome(skill.getNome());
		skillConvertida.setDescricao(skill.getDescricao());
		return skillConvertida;
	}
	
	//AtualizarDTO
	public SkillAtualizarDTO converterSkillAtualizarDTO(Skill skill) {
		SkillAtualizarDTO skillConvertida = new SkillAtualizarDTO();
		skillConvertida.setId(skill.getId());
		skillConvertida.setNome(skill.getNome());
		skillConvertida.setDescricao(skill.getDescricao());
		skillConvertida.setImagem(skill.getImagem());
		return skillConvertida;
	}
}
