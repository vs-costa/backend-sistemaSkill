package br.com.api.backendapi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.api.backendapi.entities.Skill;

@Repository

public interface SkillRepository extends JpaRepository<Skill, Long> {

	@Query("SELECT s FROM Skill s WHERE LOWER(s.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
	List<Skill> findByNome(@Param("nome") String nome);

	@Query("SELECT s FROM Skill s WHERE LOWER(s.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))")
	List<Skill> findByDescricao(@Param("descricao") String descricao);

	List<Skill> findByAtivo(boolean ativo);

	Page<Skill> findAll(Pageable pageable);

	List<Skill> findAll(Sort sort);

}
