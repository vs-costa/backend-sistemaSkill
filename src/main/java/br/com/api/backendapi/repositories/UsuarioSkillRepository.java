package br.com.api.backendapi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.backendapi.entities.UsuarioSkill;
import br.com.api.backendapi.enums.LevelSkillEnum;

@Repository
public interface UsuarioSkillRepository extends JpaRepository<UsuarioSkill, Long> {
   
    List<UsuarioSkill> findByLevel(LevelSkillEnum level);
    
    List<UsuarioSkill> findBySkillNome(String skillNome);
    
    List<UsuarioSkill> findByUsuarioIdAndSkillNome(Long usuarioId, String skillNome);
    
    Page<UsuarioSkill> findAll(Pageable pageable);
    
    List<UsuarioSkill> findAll(Sort sort);
}
