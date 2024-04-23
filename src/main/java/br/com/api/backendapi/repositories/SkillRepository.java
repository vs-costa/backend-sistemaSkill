package br.com.api.backendapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.backendapi.entities.Skill;

@Repository

public interface SkillRepository extends JpaRepository<Skill, Long>{

}
