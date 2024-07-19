package br.com.api.backendapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.backendapi.entities.SystemUser;
import br.com.api.backendapi.exceptions.ResourceNotFoundException;
import br.com.api.backendapi.repositories.SystemUserRepository;

@Service
public class SystemUserService {
    
    @Autowired
    SystemUserRepository systemUserRepository;
    
    public SystemUser findByEmail(String email) {
        Optional<SystemUser> userOptional = systemUserRepository.findByEmail(email);
        return userOptional.orElseThrow(() -> new ResourceNotFoundException("Usuário com email " + email + " não encontrado."));
    }
    
    public SystemUser save(SystemUser systemUser) {
        return systemUserRepository.save(systemUser);
    }
    
    public List<SystemUser> listarTodos() {
        return systemUserRepository.findAll();
    }
    
    public SystemUser findById(Long id) {
        Optional<SystemUser> userOptional = systemUserRepository.findById(id);
        return userOptional.orElseThrow(() -> new ResourceNotFoundException("Usuário com id " + id + " não encontrado."));
    }
    
    public boolean existsByEmail(String email) {
        return systemUserRepository.findByEmail(email).isPresent();
    }
}
