package br.com.api.backendapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.backendapi.entities.Role;
import br.com.api.backendapi.repositories.RoleRepository;

@Service

public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	public Role save(Role role) {
		return roleRepository.save(role);
	}

}
