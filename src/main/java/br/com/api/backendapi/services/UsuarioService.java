package br.com.api.backendapi.services;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.api.backendapi.config.PasswordEncoder;
import br.com.api.backendapi.dto.UsuarioAtualizarDTO;
import br.com.api.backendapi.dto.UsuarioDTO;
import br.com.api.backendapi.dto.paginacao.PageResponse;
import br.com.api.backendapi.entities.SystemUser;
import br.com.api.backendapi.entities.Usuario;
import br.com.api.backendapi.exceptions.ResourceNotFoundException;
import br.com.api.backendapi.mapper.UsuarioMapper;
import br.com.api.backendapi.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioMapper usuarioMapper;

    @Autowired
    SystemUserService systemUserService;

    // Get ID
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
        return usuarioMapper.converterUsuarioDTO(usuario);
    }

    // GET Listar com paginação
    public PageResponse<UsuarioDTO> listarTodos(int pagina, int tamanhoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);

        List<UsuarioDTO> usuariosDTO = usuariosPage.getContent().stream()
                .map(usuarioMapper::converterUsuarioDTO)
                .collect(Collectors.toList());

        return new PageResponse<>(usuariosDTO, usuariosPage.getNumber(), usuariosPage.getSize(),
                usuariosPage.getTotalElements(), usuariosPage.getTotalPages(), usuariosPage.isLast(), usuariosPage.isFirst());
    }

    // Get listar ordenado por email
    public List<UsuarioDTO> listarUsuarioOrdenadosPorEmail() {
        List<Usuario> usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "email"));
        return usuarios.stream()
                .map(usuarioMapper::converterUsuarioDTO)
                .collect(Collectors.toList());
    }
    
    public PageResponse<UsuarioDTO> listarUsuarioOrdenadosPorEmail(int pagina, int tamanhoPagina) {
		Pageable pageable = PageRequest.of(pagina, tamanhoPagina, Sort.by(Sort.Direction.ASC, "email"));
		Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);

		List<UsuarioDTO> usuariosDTO = usuariosPage.getContent().stream().map(usuarioMapper::converterUsuarioDTO)
				.collect(Collectors.toList());

		return new PageResponse<>(usuariosDTO, usuariosPage.getNumber(), usuariosPage.getSize(),
				usuariosPage.getTotalElements(), usuariosPage.getTotalPages(), usuariosPage.isLast(), usuariosPage.isFirst());
	}

    // POST
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // PUT
    @Transactional
    public UsuarioAtualizarDTO atualizar(Long id, @Valid UsuarioAtualizarDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));

        if (usuarioDTO.getEmail() != null) {
            SystemUser systemUser = systemUserService.findByEmail(usuario.getEmail());
            systemUser.setEmail(usuarioDTO.getEmail());
            systemUserService.save(systemUser);
            usuario.setEmail(usuarioDTO.getEmail());
        }

        if (usuarioDTO.getSenha() != null) {
            SystemUser systemUser = systemUserService.findByEmail(usuario.getEmail());
            String senhaCriptografada = PasswordEncoder.encodePassword(usuarioDTO.getSenha());
            systemUser.setSenha(senhaCriptografada);
            systemUserService.save(systemUser);
            usuario.setSenha(senhaCriptografada);
        }

        usuarioRepository.save(usuario);
        return usuarioMapper.converterUsuarioAtualizarDTO(usuario);
    }

    // Delete lógico
    public void removerLogico(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    // Ativar Lógico
    public void ativarLogico(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
    }

    // Filtro
    public List<UsuarioDTO> buscarPorCriterios(String email, Boolean ativo) {
        List<Usuario> usuarios = usuarioRepository.findAll();

        if (email != null && !email.isEmpty()) {
            usuarios = usuarios.stream()
                    .filter(usuario -> usuario.getEmail().toLowerCase().contains(email.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (ativo != null) {
            usuarios = usuarios.stream().filter(usuario -> usuario.getAtivo().equals(ativo))
                    .collect(Collectors.toList());
        }

        return converterParaDTO(usuarios);
    }

    private List<UsuarioDTO> converterParaDTO(List<Usuario> usuarios) {
        List<UsuarioDTO> dtos = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            dtos.add(usuarioMapper.converterUsuarioDTO(usuario));
        }
        return dtos;
    }
}