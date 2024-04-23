package br.com.api.backendapi.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.api.backendapi.entities.Role;
import br.com.api.backendapi.enums.TipoRoleEnum;

@Repository

public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(TipoRoleEnum roleUser);
	
	@Query(value = "select r.* from tb_system_user u \r\n" + "inner join usuario_role ur on u.id_system_user = ur.usuario_id\r\n"
			+ "inner join tb_roles r on ur.role_id = r.id\r\n" + "where u.email = :email", nativeQuery = true)
	Set<Role> roles(String email);
}
