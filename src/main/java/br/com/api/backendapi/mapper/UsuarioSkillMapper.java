package br.com.api.backendapi.mapper;

import org.springframework.stereotype.Component;

import br.com.api.backendapi.dto.UsuarioSkillAtualizarDTO;
import br.com.api.backendapi.dto.UsuarioSkillDTO;
import br.com.api.backendapi.entities.Skill;
import br.com.api.backendapi.entities.UsuarioSkill;

@Component

public class UsuarioSkillMapper {
		
	//Converter DTO
	public UsuarioSkillDTO converterUsuarioSkillDTO(UsuarioSkill usuarioSkill) {
	    UsuarioSkillDTO usuarioSkillConvertido = new UsuarioSkillDTO();
	    usuarioSkillConvertido.setUsuarioId(usuarioSkill.getUsuario().getId());
	    usuarioSkillConvertido.setSkillId(usuarioSkill.getSkill().getId());
	    usuarioSkillConvertido.setLevel(usuarioSkill.getLevel());
	    
	    Skill skill = usuarioSkill.getSkill();
	    if (skill != null) {
	        usuarioSkillConvertido.setSkillNome(skill.getNome());
	        usuarioSkillConvertido.setSkillDescricao(skill.getDescricao());
	        usuarioSkillConvertido.setSkillImagem(skill.getImagem());
	    }
	    
	    return usuarioSkillConvertido;
	}
	
	//Atualizar DTO
	public UsuarioSkillAtualizarDTO converterUsuarioSkillAtualizarDTO(UsuarioSkill usuarioSkill) {
		UsuarioSkillAtualizarDTO usuarioSkillAtualizarConvertido = new UsuarioSkillAtualizarDTO();
		usuarioSkillAtualizarConvertido.setLevel(usuarioSkill.getLevel());
		return usuarioSkillAtualizarConvertido;
	}
}
