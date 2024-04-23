package br.com.api.backendapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.backendapi.entities.UsuarioSkill;
import br.com.api.backendapi.enums.LevelSkillEnum;

@Repository
public interface UsuarioSkillRepository extends JpaRepository<UsuarioSkill, Long> {
    List<UsuarioSkill> findByLevel(LevelSkillEnum level);
}
