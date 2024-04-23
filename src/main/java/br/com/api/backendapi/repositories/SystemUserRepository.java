package br.com.api.backendapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.backendapi.entities.SystemUser;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long>{
	
	Optional<SystemUser> findByEmail(String email);
}
